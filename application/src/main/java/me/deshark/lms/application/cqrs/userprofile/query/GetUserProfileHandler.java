package me.deshark.lms.application.cqrs.userprofile.query;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.application.cqrs.core.QueryHandler;
import me.deshark.lms.application.info.userprofile.UserProfileInfo;
import me.deshark.lms.common.exception.application.UserProfileNotFoundException;
import me.deshark.lms.domain.repository.userprofile.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author DE_SHARK
 */
@RequiredArgsConstructor
@Service
public class GetUserProfileHandler
        implements QueryHandler<GetUserProfileQuery, UserProfileInfo> {

    private final UserProfileRepository repository;

    @Override
    @Transactional(readOnly = true) // 明确标记为只读事务
    public UserProfileInfo handle(GetUserProfileQuery query) {
        // 1. 参数校验
        Objects.requireNonNull(query.getUserId(), "用户ID不能为空");

        // 2. 查询领域模型
        return repository.findByUserId(query.getUserId())
                .map(user -> UserProfileInfo
                        .builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build()
                )
                .orElseThrow(() -> new UserProfileNotFoundException(
                        "用户档案不存在: " + query.getUserId()
                ));

        // 3. 可添加缓存逻辑等...
    }

}
