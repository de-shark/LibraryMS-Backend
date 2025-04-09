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
import java.util.List;
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

    private void initializeSeatsForFloor(UUID areaId, String floorPrefix,
                                         List<String> seatTypes, List<Boolean> hasPowerOptions,
                                         int seatsPerType) {
        int seatCounter = 1;
        for (int i = 0; i < seatTypes.size(); i++) {
            String seatType = seatTypes.get(i);
            boolean hasPower = hasPowerOptions.get(i);
            
            for (int j = 1; j <= seatsPerType; j++) {
                StudySeatDO seat = StudySeatDO.builder()
                        .seatId(GUID.v7())
                        .areaId(areaId)
                        .seatNumber(floorPrefix + "-" + seatType.charAt(0) + "-" + j)
                        .seatType(seatType)
                        .hasPower(hasPower)
                        .isActive(true)
                        .build();
                studySeatMapper.insert(seat);
                seatCounter++;
            }
        }
    }

    private void initializeStudyAreas() {
        // 初始化四个楼层的自习区域
        UUID areaIdFloor1 = GUID.v7();
        UUID areaIdFloor2 = GUID.v7();
        UUID areaIdFloor3 = GUID.v7();
        UUID areaIdFloor4 = GUID.v7();
        Arrays.asList(
            StudyAreaDO.builder()
                .areaId(areaIdFloor1)
                .name("图书馆一楼自习区")
                .location("图书馆1楼东侧")
                .description("开放式自习区，适合小组讨论")
                .openingHours("07:00-23:00")
                .maxCapacity(150)
                .build(),
            StudyAreaDO.builder()
                .areaId(areaIdFloor2)
                .name("图书馆二楼电子阅览室")
                .location("图书馆2楼西侧")
                .description("安静学习区，提供独立座位")
                .openingHours("08:00-22:30")
                .maxCapacity(120)
                .build(),
            StudyAreaDO.builder()
                .areaId(areaIdFloor3)
                .name("图书馆三楼自习区")
                .location("图书馆3楼南侧")
                .description("安静学习区")
                .openingHours("08:00-22:00")
                .maxCapacity(100)
                .build(),
            StudyAreaDO.builder()
                .areaId(areaIdFloor4)
                .name("图书馆四楼研修室")
                .location("图书馆4楼北侧")
                .description("为考研学生提供安静的学习空间")
                .openingHours("08:00-22:00")
                .maxCapacity(80)
                .build()
        ).forEach(studyAreaMapper::insert);

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

        // 为每个楼层初始化不同的座位
        initializeSeatsForFloor(
                areaIdFloor1,
                "1F",
                Arrays.asList("标准座位", "小组讨论桌"),
                Arrays.asList(true, false),
                50
        );
        
        initializeSeatsForFloor(
                areaIdFloor2,
                "2F",
                List.of("电脑座位"),
                List.of(true),
                40
        );
        
        initializeSeatsForFloor(
                areaIdFloor3,
                "3F",
                Arrays.asList("静音区座位", "单人自习间"),
                Arrays.asList(true, true),
                30
        );
        
        initializeSeatsForFloor(
                areaIdFloor4,
                "4F",
                Arrays.asList("考研座位", "带电脑座位"),
                Arrays.asList(true, true),
                20
        );
    }
}
