package me.deshark.lms.interfaces.converter;

import me.deshark.lms.application.info.userprofile.UserProfileInfo;
import me.deshark.lms.interfaces.dto.user.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserProfileConverter {
    UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

    UserInfoResponse entityToDto(UserProfileInfo profile);
}
