package com.investinfo.capital.service;

import com.investinfo.capital.controller.FormatUtils;
import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.dto.mapper.PositionMapper;
import com.investinfo.capital.model.ImoexPosition;
import com.investinfo.capital.model.SnapshotPosition;
import com.investinfo.capital.repository.ImoexPositionRepository;
import com.investinfo.capital.repository.SnapshotPositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.investinfo.capital.controller.MathUtils.getPercentageString;
import static com.investinfo.capital.controller.MathUtils.getSumPositions;

@Service
@RequiredArgsConstructor
public class SnapshotPositionService {

    private final SnapshotPositionRepository snapshotPositionRepository;
    private final FormatUtils msgData;
    private final ModelMapper modelMapper;
    private final PositionMapper positionMapper;
    private final ImoexPositionService imoexPositionService;
    private final FormatUtils formatUtils;

    public String getEveryDayEndReport(Portfolio portfolio) {
        List<SnapshotPosition> byDateSnapshot = snapshotPositionRepository.findByDateSnapshot(LocalDate.now().minusDays(2));
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream()
                .filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency")).toList();
        Map<String, String[]> resultMap = new HashMap<>();

        List<Map<String, BigDecimal>> tickerAndAmountMap = snapshotPositionRepository.findTickerAndAmountMap();


        List<ImoexPositionDTO> imoexPositionDTOList = new ArrayList<>();
        positionWithoutBonds.forEach(position -> imoexPositionDTOList.add(toImoexDTO(position)));



//        List<PositionDTO> positionDTOList = new ArrayList<>();
//        List<ImoexPositionDTO> imoexPositionDTOList = new ArrayList<>();
//        allPositions.forEach(position -> {
//            positionDTOList.add(toDto(position));
//        });
//
//        positionDTOList.forEach(position -> {
//            imoexPositionDTOList.add(positionMapper.toDtoImoex(position));
//        });

        imoexPositionDTOList.forEach(positionDTO -> {
            System.out.println();
            for (int i = 0; i < byDateSnapshot.size(); i++) {
                if (byDateSnapshot.get(i).getTicker().equals(positionDTO.getTicker())) {
                    resultMap.put(positionDTO.getName(),
                            new String[] {
                                    formatUtils.formantNumber(positionDTO.getCurrentPrice()),
//                            getPercentageString(byDateSnapshot.get(i).getAmount(), byDateSnapshot.get(i).getAmount().subtract(positionDTO.getCurrentPrice()))});
                            getPercentageString(byDateSnapshot.get(i).getAmount(), positionDTO.getCurrentPrice().subtract(byDateSnapshot.get(i).getAmount()))});
                }

//
//                if (tickerAndAmountMap.get(i).containsKey(positionDTO.getTicker())) {
//                    resultMap.put(positionDTO.getName(),
//                            (tickerAndAmountMap.get(i).get(positionDTO.getTicker()).subtract(positionDTO.getCurrentPrice())).divide(positionDTO.getCurrentPrice()));
//                }
            }

        });

        System.out.println();

//        Map<String, BigDecimal> sectorData = getDataFromPosition(allPositions);
        BigDecimal currSumPosition = getSumPositions(allPositions);



        return "result.toString()";
    }

    private ImoexPositionDTO toImoexDTO(Position position) {
        return imoexPositionService.toDtoImoex(position);
    }

    private PositionDTO toDto(Position position) {
        return new PositionDTO(position.getFigi(), position.getCurrentPrice());
    }

//    private Map<String, BigDecimal> getDataFromPosition(List<Position> position) {
//        Map<String, BigDecimal> sectorData = new HashMap<>();
//        for (Position currPosition : position) {
//            PositionDTO share = positionService.getShareDTO(currPosition.getFigi());
//            if (sectorData.containsKey(share.getSector())) {
//                sectorData.computeIfPresent(share.getSector(), (price, sumSector) -> sumSector.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity())));
//            } else {
//                sectorData.put(share.getSector(), currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
//            }
//        }
//        return sectorData;
//    }
}
