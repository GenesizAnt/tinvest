package com.investinfo.capital.service;

import com.investinfo.capital.controller.FormatUtils;
import com.investinfo.capital.dto.PositionDTO;
import com.investinfo.capital.model.SnapshotPosition;
import com.investinfo.capital.repository.SnapshotPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.investinfo.capital.controller.MathUtils.getPercentage;
import static com.investinfo.capital.controller.MathUtils.getSumPositions;

@Service
@RequiredArgsConstructor
public class SnapshotPositionService {

    private final SnapshotPositionRepository snapshotPositionRepository;
    private final FormatUtils msgData;

    public String getEveryDayEndReport(Portfolio portfolio) {
        List<SnapshotPosition> byDateSnapshot = snapshotPositionRepository.findByDateSnapshot(LocalDate.now());
        List<Position> allPositions = portfolio.getPositions();
        Map<String, BigDecimal> sectorData = getDataFromPosition(allPositions);
        BigDecimal currSumPosition = getSumPositions(allPositions);



        return "result.toString()";
    }

    private Map<String, BigDecimal> getDataFromPosition(List<Position> position) {
        Map<String, BigDecimal> sectorData = new HashMap<>();
        for (Position currPosition : position) {
            PositionDTO share = positionService.getShareDTO(currPosition.getFigi());
            if (sectorData.containsKey(share.getSector())) {
                sectorData.computeIfPresent(share.getSector(), (price, sumSector) -> sumSector.add(currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity())));
            } else {
                sectorData.put(share.getSector(), currPosition.getCurrentPrice().getValue().multiply(currPosition.getQuantity()));
            }
        }
        return sectorData;
    }
}
