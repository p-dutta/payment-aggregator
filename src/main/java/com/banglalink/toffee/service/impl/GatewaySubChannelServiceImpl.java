package com.banglalink.toffee.service.impl;

import com.banglalink.toffee.models.dto.GatewaySubChannelDto;
import com.banglalink.toffee.models.schema.GatewaySubChannelEntity;
import com.banglalink.toffee.models.schema.PaymentGatewayEntity;
import com.banglalink.toffee.repository.PaymentGatewayRepository;
import com.banglalink.toffee.repository.PaymentGatewaySubChannelRepository;
import com.banglalink.toffee.service.GatewaySubChannelService;
import com.banglalink.toffee.service.mapper.PaymentGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatewaySubChannelServiceImpl implements GatewaySubChannelService {
    private final PaymentGatewayMapper mapper;

    private final PaymentGatewaySubChannelRepository subChannelRepository;
    private final PaymentGatewayRepository paymentGatewayRepository;

    @Override
    public void updateGatewaySubChannelsInBulk(List<GatewaySubChannelDto> subChannelDtos, PaymentGatewayEntity paymentGateway) {
        List<GatewaySubChannelEntity> existingChannels = paymentGateway.getGatewaySubChannels().stream().toList();
        List<GatewaySubChannelEntity> subChannelList = new ArrayList<>(subChannelDtos.stream()
                .map(channelDto -> mapper.mapGatewaySubChannelDtoToEntity(channelDto, paymentGateway)).toList());

        subChannelRepository.deleteAllByIdInBatch(existingChannels.stream().map(GatewaySubChannelEntity::getId).toList());
        paymentGateway.getGatewaySubChannels().clear();

        subChannelRepository.saveAll(subChannelList);

        paymentGateway.setGatewaySubChannels(subChannelList);
        paymentGatewayRepository.save(paymentGateway);
    }

    @Override
    public void addGatewaySubChannels(List<GatewaySubChannelDto> subChannelDto, PaymentGatewayEntity paymentGateway) {
        List<GatewaySubChannelEntity> subChannelList = new ArrayList<>(subChannelDto.stream()
                .map(channelDto -> mapper.mapGatewaySubChannelDtoToEntity(channelDto, paymentGateway)).toList());

        paymentGateway.setGatewaySubChannels(subChannelList);
        paymentGatewayRepository.save(paymentGateway);
    }
}
