package me.deshark.lms.infrastructure.mybatis;

import me.deshark.lms.domain.model.auth.vo.UserRole;
import me.deshark.lms.domain.model.auth.vo.UserStatus;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DE_SHARK
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 注册 UUID 处理器
            configuration.getTypeHandlerRegistry().register(UuidTypeHandler.class);

            // 批量注册通用枚举处理器
            configuration.getTypeHandlerRegistry().register(UserRole.class, GenericEnumTypeHandler.class);
            configuration.getTypeHandlerRegistry().register(UserStatus.class, GenericEnumTypeHandler.class);
        };
    }
}