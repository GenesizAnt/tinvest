package com.investinfo.capital.service;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.dto.mapper.PositionMapper;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.repository.ImoexPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.models.Position;

@Service
@RequiredArgsConstructor
public class ImoexPositionService {

    private final ImoexPositionRepository imoexPositionRepository;
    private final PositionMapper positionMapper;

    public ImoexPositionDTO getShareDTO(String figi) {
        ImoexPosition position = imoexPositionRepository.getImoexPositionByFigi(figi);
        return positionMapper.toDto(position);
    }

    public ImoexPositionDTO toDtoImoex(Position position) {
        ImoexPositionDTO shareDTO = getShareDTO(position.getFigi());
        shareDTO.setCurrentPrice(position.getCurrentPrice().getValue());
        return shareDTO;
    }
}
