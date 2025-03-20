package me.deshark.lms.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import me.deshark.lms.domain.model.borrow.entity.Patron;
import me.deshark.lms.domain.repository.borrow.PatronRepository;
import me.deshark.lms.infrastructure.entity.UserDO;
import me.deshark.lms.infrastructure.mapper.BorrowerInfoMapper;
import me.deshark.lms.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Repository
@RequiredArgsConstructor
public class PatronRepositoryImpl implements PatronRepository {

    private final UserMapper userMapper;
    private final BorrowerInfoMapper borrowerInfoMapper;

    @Override
    public Patron findById(UUID id) {
        return null;
    }

    @Override
    public Patron findByUsername(String username) {
        // 1. 获取用户id
        UserDO userDO = userMapper.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        // 2. 通过用户id查借阅信息
        return borrowerInfoMapper.findByUserId(userDO.getUuid())
                .map(BorrowerInfoMapper::convertToPatron)
                .orElseThrow(() -> new IllegalArgumentException("找不到个人借阅信息"));
    }
}
