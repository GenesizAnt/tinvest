package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.ShareDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Share;

@RequiredArgsConstructor
@Component
public class ShareMapper {
    private final ModelMapper modelMapper;

    public ShareDTO toDto(Share share) {
        return modelMapper.map(share, ShareDTO.class);
    }
}
