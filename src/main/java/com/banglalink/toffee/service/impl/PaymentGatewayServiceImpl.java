package com.banglalink.toffee.service.impl;

import com.banglalink.toffee.exception.BadRequestException;
import com.banglalink.toffee.exception.RecordNotFoundException;
import com.banglalink.toffee.models.dto.*;
import com.banglalink.toffee.models.request.PaymentGatewayRequest;
import com.banglalink.toffee.models.schema.*;
import com.banglalink.toffee.repository.ImagesRepository;
import com.banglalink.toffee.repository.PaymentGatewayBindingRepository;
import com.banglalink.toffee.repository.PaymentGatewayRepository;
import com.banglalink.toffee.repository.specification.GatewayImageSpecification;
import com.banglalink.toffee.service.CacheService;
import com.banglalink.toffee.service.GatewayBlacklistedCountryService;
import com.banglalink.toffee.service.GatewaySubChannelService;
import com.banglalink.toffee.service.PaymentGatewayService;
import com.banglalink.toffee.service.mapper.PaymentGatewayMapper;
import com.banglalink.toffee.utils.AuthUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.banglalink.toffee.models.schema.ConstantUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private final PaymentGatewayRepository pgwRepository;

    private final ImagesRepository imagesRepository;

    private final PaymentGatewayMapper mapper;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    private final PaymentGatewayMapper paymentGatewayMapper;

    private final PaymentGatewayRepository paymentGatewayRepository;

    private final GatewayBlacklistedCountryService blacklistedCountryService;

    private final GatewaySubChannelService subChannelService;

    private final PaymentGatewayBindingRepository gatewayBindingRepository;

    private final CacheService cacheService;
    private final AuthUtil authUtil;

    @Override
    public PagedResult<PaymentGatewayDto> getAllPageablePaymentGateways(String decodedPayload, Integer pageSize, Integer page, Map<String, String> queryParams, Boolean blacklistingEnabled) {
        int size = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int pageNumber = page != null ? page : PAGE;
        if (pageNumber > 0) pageNumber--;

        Pageable pageable = Pageable.ofSize(Math.min(size, MAXIMUM_PAGE_SIZE)).withPage(pageNumber);

        List<PaymentGatewayDto> gatewayDtos = getAllPaymentGateways(decodedPayload, queryParams, blacklistingEnabled);

        Page<PaymentGatewayDto> result = convertPaymentGatewayListToPage(gatewayDtos, pageable);

        return new PagedResult<PaymentGatewayDto>()
                .setResult(result.stream().map(data -> modelMapper.map(data, PaymentGatewayDto.class)).toList())
                .setTotalCount(result.getTotalElements());
    }

    @Override
    public PaymentGatewayDto createPaymentGateway(PaymentGatewayRequest request) {
        if (paymentGatewayRepository.findByName(request.getName()).isPresent())
            throw new BadRequestException("Gateway name should be unique");

        PaymentGatewayEntity entity = paymentGatewayMapper.mapPaymentGatewayRequestToEntity(request);
        paymentGatewayRepository.save(entity);

        subChannelService.addGatewaySubChannels(request.getGatewaySubChannels(), entity);
        blacklistedCountryService.addBlacklistedCountries(request.getBlacklistedCountries(), entity);

        PaymentGatewayDto gatewayDto = paymentGatewayMapper.mapPaymentGatewayEntityToDto(entity, true);
        cacheService.setToCache(PGW_CACHE_STORE, entity.getId().toString(), gatewayDto);

        return gatewayDto;
    }

    @Override
    @CachePut(value = PGW_CACHE_STORE, key = "#paymentGatewayId")
    public PaymentGatewayDto updatePaymentGateway(PaymentGatewayRequest request, UUID paymentGatewayId) {
        PaymentGatewayEntity paymentGatewayEntity = fetchPaymentGateway(paymentGatewayId);
        paymentGatewayEntity = paymentGatewayMapper.mapGatewayUpdateRequestToEntity(paymentGatewayEntity, request);

        subChannelService.updateGatewaySubChannelsInBulk(request.getGatewaySubChannels(), paymentGatewayEntity);
        blacklistedCountryService.updateBlacklistedCountriesInBulk(request.getBlacklistedCountries(), paymentGatewayEntity);

        PaymentGatewayEntity updatedGateway = paymentGatewayRepository.save(paymentGatewayEntity);

        return paymentGatewayMapper.mapPaymentGatewayEntityToDto(updatedGateway, true);
    }

    @Override
    @Cacheable(value = PGW_CACHE_STORE, key = "#paymentGatewayId")
    public PaymentGatewayDto getPaymentGatewayById(UUID paymentGatewayId) {
        PaymentGatewayEntity paymentGatewayEntity = fetchPaymentGateway(paymentGatewayId);

        List<ImageEntity> imageEntities = imagesRepository.findAll();

        PaymentGatewayDto gatewayDto = paymentGatewayMapper.mapPaymentGatewayEntityToDto(paymentGatewayEntity, true);

        setPaymentGatewayImages(gatewayDto, imageEntities);

        return gatewayDto;
    }

    @Override
    @CacheEvict(value = PGW_CACHE_STORE, key = "#paymentGatewayId")
    public void deletePaymentGatewayById(UUID paymentGatewayId) {
        PaymentGatewayEntity paymentGatewayEntity = fetchPaymentGateway(paymentGatewayId);

        paymentGatewayRepository.delete(paymentGatewayEntity);
    }

    @Override
    public List<ImageDto> fetchGatewayImages(Map<String, String> queryParams) {
        Specification<ImageEntity> filters = new GatewayImageSpecification<>(queryParams);

        List<ImageEntity> imageEntities = imagesRepository.findAll(filters);

        return new ArrayList<>(imageEntities.stream()
                .map(mapper::mapImageEntityToDto)
                .toList());
    }

    @Override
    public DirectPayDto getDirectPayEnableInfo(String decodedPayload) {
        try {
            JtiData jtiData = authUtil.extractJtiClaims(decodedPayload);

            PaymentGatewayBindingEntity gatewayBindingEntity = gatewayBindingRepository.findByRequesterId(jtiData.getRequester_id());

            if (gatewayBindingEntity == null) {
                log.error("Gateway binding doesn't exist");
                throw new RecordNotFoundException("Gateway binding doesn't exist");
            }

            GatewayBindingDto gatewayBindingDto = mapper.mapGatewayBindingEntityToDto(gatewayBindingEntity);

            if (gatewayBindingDto.getStatus().equalsIgnoreCase("deleted")) {
                log.error("Gateway binding doesn't exist");
                throw new RecordNotFoundException("Gateway binding doesn't exist");
            }

            return mapBindingInfoIntoDirectPayDto(gatewayBindingDto);

        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Don't find any binding info");
        }
    }

    private DirectPayDto mapBindingInfoIntoDirectPayDto(GatewayBindingDto gatewayBindingDto) {
        return DirectPayDto.builder()
                .gatewayId(gatewayBindingDto.getGatewayId())
                .paywallGatewayId(gatewayBindingDto.getPaywallGatewayId())
                .initiatorMsisdn(gatewayBindingDto.getInitiatorMsisdn())
                .requesterId(gatewayBindingDto.getRequesterId())
                .mfsAccount(formatPhoneNumber(gatewayBindingDto.getMfsAccount()))
                .build();
    }

    private String formatPhoneNumber(String number) {
        if (number == null || number.length() != 11) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        return number.substring(0, 3) + "XXXXX" + number.substring(8);
    }

    private PaymentGatewayEntity fetchPaymentGateway(UUID paymentGatewayId) {
        Optional<PaymentGatewayEntity> gatewayEntity = paymentGatewayRepository.findById(paymentGatewayId);

        if (gatewayEntity.isEmpty()) throw new RecordNotFoundException("Payment gateway doesn't exist");

        return gatewayEntity.get();
    }

    private List<PaymentGatewayDto> getAllPaymentGateways(String decodedPayload, Map<String, String> queryParams, Boolean blacklistingEnabled) {
        try {
            PayloadData payloadData = objectMapper.readValue(decodedPayload, PayloadData.class);

            List<PaymentGatewayEntity> allGateways = findAllOrFilterByQueryParams(queryParams);

            List<ImageEntity> imageEntities = imagesRepository.findAll();

            return new ArrayList<>(allGateways.stream()
                    .filter(gateway -> !blacklistingEnabled || !isBlacklistedInRegion(gateway, payloadData.getCountry()))
                    .filter(gateway -> blacklistingEnabled ? gateway.getStatus() : true)
                    .map(gatewayEntity -> paymentGatewayMapper.mapPaymentGatewayEntityToDto(gatewayEntity, !blacklistingEnabled))
                    .peek(paymentGatewayDto -> setPaymentGatewayImages(paymentGatewayDto, imageEntities))
                    .toList());

        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    private List<PaymentGatewayEntity> findAllOrFilterByQueryParams(Map<String, String> queryParams) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");

        return pgwRepository.findAllWithAssociatedNodes(queryParams, sort);
    }

    private void setPaymentGatewayImages(PaymentGatewayDto paymentGatewayDto, List<ImageEntity> imageEntities) {
        List<ImageDto> imageDtos = imageEntities.stream()
                .filter(imageEntity -> Objects.equals(imageEntity.getSlug(), paymentGatewayDto.getImageLabel()))
                .map(mapper::mapImageEntityToDto)
                .toList();

        paymentGatewayDto.setImageDtos(imageDtos);
    }

    private boolean isBlacklistedInRegion(PaymentGatewayEntity gateway, String countryRegion) {
        return gateway.getGatewayBlacklistedCountry().stream()
                .anyMatch(blacklistedCountry -> blacklistedCountry.getCountryCode()
                        .equalsIgnoreCase(countryRegion));
    }

    private Page<PaymentGatewayDto> convertPaymentGatewayListToPage(List<PaymentGatewayDto> paymentGatewayDtos, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), paymentGatewayDtos.size());
        return new PageImpl<>(start > end ? new ArrayList<>() : paymentGatewayDtos.subList(start, end), pageable, paymentGatewayDtos.size());
    }
}
