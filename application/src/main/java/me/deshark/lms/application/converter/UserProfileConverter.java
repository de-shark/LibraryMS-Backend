package me.deshark.lms.application.converter;

import me.deshark.lms.application.info.userprofile.UserProfileInfo;
import me.deshark.lms.domain.model.userprofile.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserProfileConverter {
    UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

    UserProfileInfo domainEntityToAppEntity(UserProfile profile);
}
