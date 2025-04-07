package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudySeatDO {
    private UUID seatId;
    private UUID areaId;
    private String seatNumber;
    private String seatType;
    private Boolean hasPower;
    private Boolean isActive;
}