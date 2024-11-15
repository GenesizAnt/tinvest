package com.investinfo.capital.service;

import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.dto.mapper.ShareMapper;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.repository.ImoexPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImoexPositionService {

    private final ImoexPositionRepository imoexPositionRepository;
    private final ShareMapper shareMapper;

    public PositionDTO getShareDTO(String figi) {
        ImoexPosition position = imoexPositionRepository.getImoexPositionByFigi(figi);
        return shareMapper.toDto(position);
    }
}
