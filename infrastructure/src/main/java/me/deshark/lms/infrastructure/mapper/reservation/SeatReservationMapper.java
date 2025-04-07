package me.deshark.lms.infrastructure.mapper.reservation;

import me.deshark.lms.infrastructure.entity.SeatReservationDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeatReservationMapper {
    @Insert("INSERT INTO seat_reservation (reservation_id, seat_id, user_id, start_time, end_time, status, check_in_time, created_at) " +
            "VALUES (#{reservationId}, #{seatId}, #{userId}, #{startTime}, #{endTime}, #{status}, #{checkInTime}, #{createdAt})")
    void insert(SeatReservationDO reservation);
}