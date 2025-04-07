package me.deshark.lms.infrastructure.config;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.infrastructure.entity.*;
import me.deshark.lms.infrastructure.mapper.reservation.SeatReservationMapper;
import me.deshark.lms.infrastructure.mapper.reservation.SeatUsageRuleMapper;
import me.deshark.lms.infrastructure.mapper.reservation.StudyAreaMapper;
import me.deshark.lms.infrastructure.mapper.reservation.StudySeatMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class StudyAreaInitializer implements ApplicationRunner {

    private final StudyAreaMapper studyAreaMapper;
    private final StudySeatMapper studySeatMapper;
    private final SeatReservationMapper seatReservationMapper;
    private final SeatUsageRuleMapper seatUsageRuleMapper;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // 检查是否已有数据
        if (studyAreaMapper.count() == 0) {
            initializeStudyAreas();
        }
    }

    private void initializeStudyAreas() {
        // 1. 初始化自习区域
        UUID mainAreaId = GUID.v7();
        StudyAreaDO mainArea = StudyAreaDO.builder()
                .areaId(mainAreaId)
                .name("主图书馆自习区")
                .location("图书馆3楼")
                .description("安静学习区域，提供电源插座")
                .openingHours("08:00-22:00")
                .maxCapacity(200)
                .build();
        studyAreaMapper.insert(mainArea);

        // 2. 初始化座位使用规则
        SeatUsageRuleDO defaultRule = SeatUsageRuleDO.builder()
                .ruleId(GUID.v7())
                .name("默认座位使用规则")
                .maxReservationHours(4)
                .maxDailyReservations(2)
                .minCancelHours(1)
                .noShowPenaltyHours(24)
                .build();
        seatUsageRuleMapper.insert(defaultRule);

        // 3. 初始化自习座位
        Arrays.asList(
                StudySeatDO.builder()
                        .seatId(GUID.v7())
                        .areaId(mainAreaId)
                        .seatNumber("A-101")
                        .seatType("标准座位")
                        .hasPower(false)
                        .isActive(true)
                        .build(),
                StudySeatDO.builder()
                        .seatId(GUID.v7())
                        .areaId(mainAreaId)
                        .seatNumber("A-102")
                        .seatType("电源座位")
                        .hasPower(true)
                        .isActive(true)
                        .build(),
                StudySeatDO.builder()
                        .seatId(GUID.v7())
                        .areaId(mainAreaId)
                        .seatNumber("A-103")
                        .seatType("安静区座位")
                        .hasPower(true)
                        .isActive(true)
                        .build()
        ).forEach(studySeatMapper::insert);

        // 4. 初始化一个示例预约记录
        SeatReservationDO sampleReservation = SeatReservationDO.builder()
                .reservationId(GUID.v7())
                .seatId(GUID.v7())
                .userId(GUID.v7()) // 假设存在的用户ID
                .startTime(Instant.now().plus(1, ChronoUnit.HOURS))
                .endTime(Instant.now().plus(5, ChronoUnit.HOURS))
                .status("RESERVED")
                .createdAt(Instant.now())
                .build();
        seatReservationMapper.insert(sampleReservation);
    }
}