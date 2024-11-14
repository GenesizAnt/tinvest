package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.ShareDTO;
import com.investinfo.capital.model.ImoexPosition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShareMapper {
    private final ModelMapper modelMapper;

    public ShareDTO toDto(ImoexPosition position) {
        return modelMapper.map(position, ShareDTO.class);
    }
}
