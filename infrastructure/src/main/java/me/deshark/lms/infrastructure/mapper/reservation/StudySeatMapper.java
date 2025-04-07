package me.deshark.lms.infrastructure.mapper.reservation;

import me.deshark.lms.infrastructure.entity.StudySeatDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudySeatMapper {
    @Insert("INSERT INTO study_seat (seat_id, area_id, seat_number, seat_type, has_power, is_active) " +
            "VALUES (#{seatId}, #{areaId}, #{seatNumber}, #{seatType}, #{hasPower}, #{isActive})")
    void insert(StudySeatDO studySeat);
}