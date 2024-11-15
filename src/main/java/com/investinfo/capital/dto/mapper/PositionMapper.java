package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.model.ImoexPosition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PositionMapper {
    private final ModelMapper modelMapper;

    public PositionDTO toDto(ImoexPosition position) {
        return modelMapper.map(position, PositionDTO.class);
    }
}
