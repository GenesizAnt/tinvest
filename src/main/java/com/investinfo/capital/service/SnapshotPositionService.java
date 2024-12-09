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
    private static final Integer ONE_DAY = 1;

    public String getEveryDayEndReport(Portfolio portfolio) {
        List<SnapshotPosition> byDateSnapshot = snapshotPositionRepository.findByDateSnapshot(LocalDate.now().minusDays(ONE_DAY));
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream()
                .filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency")).toList();

        Map<String, String[]> resultMap = new HashMap<>();

        List<Map<String, BigDecimal>> tickerAndAmountMap = snapshotPositionRepository.findTickerAndAmountMap(LocalDate.now().minusDays(ONE_DAY));


        List<ImoexPositionDTO> imoexPositionDTOList = new ArrayList<>();
        positionWithoutBonds.forEach(position -> imoexPositionDTOList.add(toImoexDTO(position)));

        imoexPositionDTOList.forEach(positionDTO -> {
            for (int i = 0; i < byDateSnapshot.size(); i++) {
                if (byDateSnapshot.get(i).getTicker().equals(positionDTO.getTicker())) {
                    resultMap.put(positionDTO.getName(),
                            new String[]{
                                    formatUtils.formantNumber(positionDTO.getCurrentPrice()),
                                    getPercentageString(byDateSnapshot.get(i).getAmount(), positionDTO.getCurrentPrice().subtract(byDateSnapshot.get(i).getAmount()))});
                }
            }
        });


        Map<String, String[]> growthPercentage = sortByGrowthPercentage(resultMap);

        String msg = msgData.getMsgEveryDayEndReport(growthPercentage);

        return msg;
    }

    private ImoexPositionDTO toImoexDTO(Position position) {
        return imoexPositionService.toDtoImoex(position);
    }

    public static Map<String, String[]> sortByGrowthPercentage(Map<String, String[]> map) {
        List<Map.Entry<String, String[]>> list = new ArrayList<>(map.entrySet());

        list.sort((entry1, entry2) -> {
            double growth1 = parseGrowthPercentage(entry1.getValue()[1]);
            double growth2 = parseGrowthPercentage(entry2.getValue()[1]);
            return Double.compare(growth2, growth1);
        });

        Map<String, String[]> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private static double parseGrowthPercentage(String percentage) {
        // Удаляем символ процента и заменяем запятую на точку
        String cleanedPercentage = percentage.replace("%", "").replace(',', '.');
        return Double.parseDouble(cleanedPercentage);
    }

//
//    private PositionDTO toDto(Position position) {
//        return new PositionDTO(position.getFigi(), position.getCurrentPrice());
//    }

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
