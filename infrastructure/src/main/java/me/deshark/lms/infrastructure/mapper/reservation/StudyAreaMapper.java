package me.deshark.lms.infrastructure.mapper.reservation;

import me.deshark.lms.infrastructure.entity.StudyAreaDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudyAreaMapper {
    @Insert("INSERT INTO study_area (area_id, name, location, description, opening_hours, max_capacity) " +
            "VALUES (#{areaId}, #{name}, #{location}, #{description}, #{openingHours}, #{maxCapacity})")
    void insert(StudyAreaDO studyArea);

    @Select("SELECT COUNT(*) FROM study_area")
    int count();
}