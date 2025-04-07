package me.deshark.lms.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SeatReservationDO {
    private UUID reservationId;
    private UUID seatId;
    private UUID userId;
    private Instant startTime;
    private Instant endTime;
    private String status; // RESERVED/IN_USE/CANCELLED/COMPLETED
    private Instant checkInTime;
    private Instant createdAt;
}