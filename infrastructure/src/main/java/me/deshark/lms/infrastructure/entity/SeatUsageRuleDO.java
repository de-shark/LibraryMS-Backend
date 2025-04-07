package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SeatUsageRuleDO {
    private UUID ruleId;
    private String name;
    private Integer maxReservationHours;
    private Integer maxDailyReservations;
    private Integer minCancelHours;
    private Integer noShowPenaltyHours;
}
