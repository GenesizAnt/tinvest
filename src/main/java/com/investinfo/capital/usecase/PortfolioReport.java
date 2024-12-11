package com.investinfo.capital.usecase;

import com.investinfo.capital.dto.ImoexPositionDTO;
import com.investinfo.capital.model.SnapshotPosition;
import com.investinfo.capital.repository.SnapshotPositionRepository;
import com.investinfo.capital.service.ImoexPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.investinfo.capital.usecase.utils.FormatNumberUtils.formantNumber;
import static com.investinfo.capital.usecase.utils.FormatNumberUtils.parseGrowthPercentage;
import static com.investinfo.capital.usecase.utils.MathUtils.getPercentage;
import static com.investinfo.capital.usecase.utils.MathUtils.getPercentageString;

@Component
@RequiredArgsConstructor
public class PortfolioReport {

    private final PortfolioMessageReport messageReport;
    private final SnapshotPositionRepository snapshotPositionRepository;
    private final ImoexPositionService imoexPositionService;
    private static final Integer ONE_DAY = 1;

    public String getPortfolioOverview(Portfolio portfolio) {
        return messageReport.getPortfolioOverview(portfolio);
    }

    public String getPositionsWithoutBonds(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream()
                .filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency"))
                .sorted((p1, p2) -> {
                    BigDecimal yield1 = getPercentage(
                            p1.getAveragePositionPrice().getValue(),
                            p1.getCurrentPrice().getValue().subtract(p1.getAveragePositionPrice().getValue())
                    );
                    BigDecimal yield2 = getPercentage(
                            p2.getAveragePositionPrice().getValue(),
                            p2.getCurrentPrice().getValue().subtract(p2.getAveragePositionPrice().getValue())
                    );
                    return yield2.compareTo(yield1);
                })
                .toList();
        return messageReport.getInfoPosition(positionWithoutBonds);
    }

    public String getDiagramSecuritiesSector(Portfolio portfolio) {
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream().filter(position -> position.getInstrumentType().equals("share")).toList();
        return messageReport.getSectorData(positionWithoutBonds);
    }

    public String getEveryDayEndReport(Portfolio portfolio) {
        List<SnapshotPosition> byDateSnapshot = snapshotPositionRepository.findByDateSnapshot(LocalDate.now().minusDays(ONE_DAY));
        List<Position> allPositions = portfolio.getPositions();
        List<Position> positionWithoutBonds = allPositions.stream()
                .filter(position -> !position.getInstrumentType().equals("bond") && !position.getInstrumentType().equals("currency")).toList();
        Map<String, String[]> resultMap = new HashMap<>();
        List<ImoexPositionDTO> imoexPositionDTOList = new ArrayList<>();
        positionWithoutBonds.forEach(position -> imoexPositionDTOList.add(toDTO(position)));
        imoexPositionDTOList.forEach(positionDTO -> {
            for (SnapshotPosition snapshotPosition : byDateSnapshot) {
                if (snapshotPosition.getTicker().equals(positionDTO.getTicker())) {
                    resultMap.put(positionDTO.getName(),
                            new String[]{
                                    formantNumber(positionDTO.getCurrentPrice()),
                                    getPercentageString(snapshotPosition.getAmount(), positionDTO.getCurrentPrice().subtract(snapshotPosition.getAmount()))});
                }
            }
        });
        Map<String, String[]> growthPercentage = sortByGrowthPercentage(resultMap);
        return messageReport.getMsgEveryDayEndReport(growthPercentage);
    }

    private ImoexPositionDTO toDTO(Position position) {
        return imoexPositionService.toDto(position);
    }

    public Map<String, String[]> sortByGrowthPercentage(Map<String, String[]> map) {
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
}
