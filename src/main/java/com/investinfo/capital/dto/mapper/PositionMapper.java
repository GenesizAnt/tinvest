package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Position;

@RequiredArgsConstructor
@Component
public class PositionMapper {
    private final ModelMapper modelMapper;

    public ImoexPositionDTO toDto(ImoexPosition position) {
        return modelMapper.map(position, ImoexPositionDTO.class);
    }
}
