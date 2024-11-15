package com.investinfo.capital.service;

import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.dto.mapper.PositionMapper;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.repository.ImoexPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImoexPositionService {

    private final ImoexPositionRepository imoexPositionRepository;
    private final PositionMapper positionMapper;

    public PositionDTO getShareDTO(String figi) {
        ImoexPosition position = imoexPositionRepository.getImoexPositionByFigi(figi);
        return positionMapper.toDto(position);
    }
}
