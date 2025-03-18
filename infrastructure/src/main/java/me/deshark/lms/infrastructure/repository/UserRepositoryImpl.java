package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.common.utils.GUID;
import me.deshark.lms.domain.model.auth.entity.AuthUser;
import me.deshark.lms.domain.model.auth.vo.UserRole;
import me.deshark.lms.domain.repository.auth.UserRepository;
import me.deshark.lms.infrastructure.entity.BorrowerInfoDO;
import me.deshark.lms.infrastructure.entity.UserDO;
import me.deshark.lms.infrastructure.entity.UserRoleDO;
import me.deshark.lms.infrastructure.enums.UserRoleType;
import me.deshark.lms.infrastructure.mapper.BorrowerInfoMapper;
import me.deshark.lms.infrastructure.mapper.UserMapper;
import me.deshark.lms.infrastructure.mapper.UserPersistenceMapper;
import me.deshark.lms.infrastructure.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserPersistenceMapper persistenceMapper;
    private final BorrowerInfoMapper borrowerInfoMapper;

    @Override
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    public void save(AuthUser authUser) {
        UserDO userDO = persistenceMapper.toDataObject(authUser);
        if (userMapper.insert(userDO) != 1) {
            throw new IllegalArgumentException("User not saved");
        }
        // 插入用户角色
        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userRoleId(GUID.v7())
                .userId(userDO.getUuid())
                .role(UserRoleType.USER)
                .build();
        userRoleMapper.insert(userRoleDO);
        // 插入借阅者信息
        BorrowerInfoDO borrowerInfoDO = BorrowerInfoDO.builder()
                .id(userDO.getUuid())
                .maxBorrowLimit(5)
                .currentLoans(0)
                .build();
        borrowerInfoMapper.insert(borrowerInfoDO);
    }

    @Override
    public AuthUser findByUsername(String username) {
        UserDO userDO = userMapper.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        UserRoleDO userRoleDO = userRoleMapper.findByUserId(userDO.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Null Role Type"));

        UserRole userRole = UserRole.valueOf(userRoleDO.getRole().toString());
        AuthUser authUser = persistenceMapper.toDomainEntity(userDO);
        authUser.setUserRole(userRole);
        return authUser;
    }
}
