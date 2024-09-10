package com.banglalink.toffee.service.impl;

import com.banglalink.toffee.exception.BadRequestException;
import com.banglalink.toffee.exception.RecordNotFoundException;
import com.banglalink.toffee.repository.BlacklistedCountryRepository;
import com.banglalink.toffee.models.dto.BlacklistedCountriesDto;
import com.banglalink.toffee.models.request.BlacklistCoutriesRequest;
import com.banglalink.toffee.models.schema.BlacklistedCountryEntity;
import com.banglalink.toffee.models.schema.PaymentGatewayEntity;
import com.banglalink.toffee.repository.PaymentGatewayRepository;
import com.banglalink.toffee.service.GatewayBlacklistedCountryService;
import com.banglalink.toffee.service.mapper.GatewayBlacklistedCountriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.banglalink.toffee.repository.specification.GatewayBlacklistedCountriesSpecification.buildGenericBlacklistedCountriesFetchingFilter;

@Service
@RequiredArgsConstructor
public class GatewayBlacklistedCountryServiceImpl implements GatewayBlacklistedCountryService {
    private final PaymentGatewayRepository gatewayRepository;

    private final BlacklistedCountryRepository blacklistedRepository;

    private final GatewayBlacklistedCountriesMapper mapper;

    private final BlacklistedCountryRepository blacklistedCountryRepository;

    private final PaymentGatewayRepository paymentGatewayRepository;

    @Override
    public void addBlacklistedCountries(List<String> countryCodes, PaymentGatewayEntity paymentGateway) {
        List<BlacklistedCountryEntity> blacklistedCountries = new ArrayList<>(countryCodes.stream()
                .map(countryCode -> new BlacklistedCountryEntity().setCountryCode(countryCode).setPaymentGateways(paymentGateway)).toList());

        paymentGateway.setGatewayBlacklistedCountry(blacklistedCountries);
        gatewayRepository.save(paymentGateway);
    }

    @Override
    public List<BlacklistedCountriesDto> fetchBlacklistedCountries(UUID gatewayId, String countryCode) {
        Specification<BlacklistedCountryEntity> filters = buildGenericBlacklistedCountriesFetchingFilter(gatewayId, countryCode);

        List<BlacklistedCountryEntity> blacklistedCountryEntityList = blacklistedRepository.findAll(filters);

        return blacklistedCountryEntityList.stream()
                .map(mapper::mapBlacklistedCountryEntityToDto)
                .toList();
    }


    @Override
    public void updateBlacklistedCountry(BlacklistCoutriesRequest request, Long blacklistedCountryId) {
        PaymentGatewayEntity paymentGateway = fetchPaymentGateway(request.getGatewayId());
        BlacklistedCountryEntity currentEntity = fetchBlacklistedCountryById(blacklistedCountryId);

        String countryCode = request.getCountryCode().get(0);

        boolean blacklistedCountryExists = paymentGateway.getGatewayBlacklistedCountry().stream().anyMatch(bc -> bc.getCountryCode().equals(countryCode));

        if (blacklistedCountryExists) {
            throw new BadRequestException("Composite gateway & country code entry already exists");
        }

        currentEntity.setCountryCode(countryCode);
        blacklistedCountryRepository.save(currentEntity);
    }

    @Override
    public void updateBlacklistedCountriesInBulk(List<String> countryCodes, PaymentGatewayEntity paymentGateway) {
        List<BlacklistedCountryEntity> blacklistedCountryEntityList = paymentGateway.getGatewayBlacklistedCountry().stream().toList();
        blacklistedCountryEntityList.stream()
                .filter(e -> countryCodes
                        .stream()
                        .noneMatch(countryCode -> e.getCountryCode().equals(countryCode)))
                .forEach(b -> {
                    paymentGateway.getGatewayBlacklistedCountry().remove(b);
                    blacklistedCountryRepository.delete(b);
                });

        countryCodes.stream().filter(
                        e -> blacklistedCountryEntityList.stream().noneMatch(be -> be.getCountryCode().equals(e)))
                .toList()
                .forEach(country -> paymentGateway.getGatewayBlacklistedCountry().add(new BlacklistedCountryEntity().setCountryCode(country).setPaymentGateways(paymentGateway)));

        gatewayRepository.save(paymentGateway);
    }

    @Override
    public void deleteBlacklistedCountry(Long blacklistedCountryId) {
        BlacklistedCountryEntity currentEntity = fetchBlacklistedCountryById(blacklistedCountryId);

        blacklistedCountryRepository.delete(currentEntity);
    }

    private BlacklistedCountryEntity fetchBlacklistedCountryById(Long blacklistedCountryId) {
        Optional<BlacklistedCountryEntity> optional = blacklistedRepository.findById(blacklistedCountryId);
        if (optional.isEmpty()) throw new RecordNotFoundException("Invalid Request");

        return optional.get();
    }

    private PaymentGatewayEntity fetchPaymentGateway(UUID paymentGatewayId) {
        Optional<PaymentGatewayEntity> gatewayEntity = paymentGatewayRepository.findById(paymentGatewayId);

        if (gatewayEntity.isEmpty()) throw new RecordNotFoundException("Payment gateway doesn't exist");

        return gatewayEntity.get();
    }
}
