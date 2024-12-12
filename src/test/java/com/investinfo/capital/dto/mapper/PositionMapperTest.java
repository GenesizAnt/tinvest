package com.investinfo.capital.dto.mapper;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.model.ImoexPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PositionMapperTest {

    private ModelMapper modelMapper;
    private PositionMapper positionMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        positionMapper = new PositionMapper(modelMapper);
    }

    @Test
    public void testToDto() {
        // Создаем объект ImoexPosition
        ImoexPosition position = new ImoexPosition();
        position.setFigi("FIGI123");
        position.setTicker("TICKER123");
        position.setClassCode("CLASSCODE123");
        position.setIsin("ISIN123");
        position.setName("Test Position");
        position.setSector("Technology");
        position.setShortName("TestPos");

        // Создаем ожидаемый объект ImoexPositionDTO
        ImoexPositionDTO expectedDto = new ImoexPositionDTO();
        expectedDto.setFigi("FIGI123");
        expectedDto.setTicker("TICKER123");
        expectedDto.setCurrentPrice(new BigDecimal("100.00"));
        expectedDto.setClassCode("CLASSCODE123");
        expectedDto.setIsin("ISIN123");
        expectedDto.setName("Test Position");
        expectedDto.setSector("Technology");
        expectedDto.setShortName("TestPos");

        // Настраиваем мок ModelMapper, чтобы он возвращал ожидаемый DTO
        when(modelMapper.map(position, ImoexPositionDTO.class)).thenReturn(expectedDto);

        // Вызываем метод toDto
        ImoexPositionDTO actualDto = positionMapper.toDto(position);

        // Проверяем, что метод возвращает ожидаемый DTO
        assertEquals(expectedDto.getFigi(), actualDto.getFigi());
        assertEquals(expectedDto.getTicker(), actualDto.getTicker());
        assertEquals(expectedDto.getCurrentPrice(), actualDto.getCurrentPrice());
        assertEquals(expectedDto.getClassCode(), actualDto.getClassCode());
        assertEquals(expectedDto.getIsin(), actualDto.getIsin());
        assertEquals(expectedDto.getName(), actualDto.getName());
        assertEquals(expectedDto.getSector(), actualDto.getSector());
        assertEquals(expectedDto.getShortName(), actualDto.getShortName());
    }
}

