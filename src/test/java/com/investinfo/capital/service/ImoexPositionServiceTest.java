package com.investinfo.capital.service;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.dto.mapper.PositionMapper;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.repository.ImoexPositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.tinkoff.piapi.core.models.Money;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ImoexPositionServiceTest {

    @Mock
    private ImoexPositionRepository imoexPositionRepository;

    @Mock
    private PositionMapper positionMapper;

    @InjectMocks
    private ImoexPositionService imoexPositionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetImoexPositionDTO() {
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

        // Настраиваем мок ImoexPositionRepository, чтобы он возвращал объект ImoexPosition
        when(imoexPositionRepository.getImoexPositionByFigi("FIGI123")).thenReturn(position);

        // Настраиваем мок PositionMapper, чтобы он возвращал ожидаемый DTO
        when(positionMapper.toDto(position)).thenReturn(expectedDto);

        // Вызываем метод getImoexPositionDTO
        ImoexPositionDTO actualDto = imoexPositionService.getImoexPositionDTO("FIGI123");

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

    @Test
    public void testToDto() {
        // Создаем мок объекта Position
        Position position = Mockito.mock(Position.class);
        when(position.getFigi()).thenReturn("FIGI123");

        Money price = Mockito.mock(Money.class);
        when(price.getValue()).thenReturn(new BigDecimal("150.00"));
        when(position.getCurrentPrice()).thenReturn(price);

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

        // Настраиваем мок ImoexPositionRepository, чтобы он возвращал объект ImoexPosition
        ImoexPosition imoexPosition = new ImoexPosition();
        imoexPosition.setFigi("FIGI123");
        imoexPosition.setTicker("TICKER123");
        imoexPosition.setClassCode("CLASSCODE123");
        imoexPosition.setIsin("ISIN123");
        imoexPosition.setName("Test Position");
        imoexPosition.setSector("Technology");
        imoexPosition.setShortName("TestPos");
        when(imoexPositionRepository.getImoexPositionByFigi("FIGI123")).thenReturn(imoexPosition);

        // Настраиваем мок PositionMapper, чтобы он возвращал ожидаемый DTO
        when(positionMapper.toDto(imoexPosition)).thenReturn(expectedDto);

        // Вызываем метод toDto
        ImoexPositionDTO actualDto = imoexPositionService.toDto(position);

        // Проверяем, что метод возвращает ожидаемый DTO с обновленной ценой
        assertEquals(expectedDto.getFigi(), actualDto.getFigi());
        assertEquals(expectedDto.getTicker(), actualDto.getTicker());
        assertEquals(new BigDecimal("150.00"), actualDto.getCurrentPrice());
        assertEquals(expectedDto.getClassCode(), actualDto.getClassCode());
        assertEquals(expectedDto.getIsin(), actualDto.getIsin());
        assertEquals(expectedDto.getName(), actualDto.getName());
        assertEquals(expectedDto.getSector(), actualDto.getSector());
        assertEquals(expectedDto.getShortName(), actualDto.getShortName());
    }
}