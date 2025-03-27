package me.deshark.lms.application.cqrs.userprofile.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.converter.UserProfileConverter;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.info.userprofile.UserProfileInfo;
import me.deshark.lms.domain.repository.userprofile.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author DE_SHARK
 */
@RequiredArgsConstructor
@Service
public class GetUserProfileHandler
        implements QueryHandler<GetUserProfileQuery, UserProfileInfo> {

    private final UserProfileRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileInfo> handle(GetUserProfileQuery query) {
        // 1. 参数校验
        Objects.requireNonNull(query.getUserId(), "用户ID不能为空");

        // 2. 查询领域模型
        return repository.findByUserId(query.getUserId())
                .map(UserProfileConverter.INSTANCE::domainEntityToAppEntity);

        // 3. 可添加缓存逻辑等...
    }

}
