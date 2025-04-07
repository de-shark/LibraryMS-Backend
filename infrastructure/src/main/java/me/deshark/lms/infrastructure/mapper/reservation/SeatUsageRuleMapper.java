package me.deshark.lms.infrastructure.mapper.reservation;

import me.deshark.lms.infrastructure.entity.SeatUsageRuleDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeatUsageRuleMapper {
    @Insert("INSERT INTO seat_usage_rule (rule_id, name, max_reservation_hours, max_daily_reservations, min_cancel_hours, no_show_penalty_hours) " +
            "VALUES (#{ruleId}, #{name}, #{maxReservationHours}, #{maxDailyReservations}, #{minCancelHours}, #{noShowPenaltyHours})")
    void insert(SeatUsageRuleDO rule);
}