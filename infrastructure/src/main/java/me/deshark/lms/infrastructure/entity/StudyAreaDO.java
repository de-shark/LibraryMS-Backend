package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudyAreaDO {
    private UUID areaId;
    private String name;
    private String location;
    private String description;
    private String openingHours;
    private Integer maxCapacity;
}
