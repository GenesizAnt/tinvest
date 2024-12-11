package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.model.ImoexPosition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PositionMapper {

    private final ModelMapper modelMapper;

    public ImoexPositionDTO toDto(ImoexPosition position) {
        return modelMapper.map(position, ImoexPositionDTO.class);
    }
}
