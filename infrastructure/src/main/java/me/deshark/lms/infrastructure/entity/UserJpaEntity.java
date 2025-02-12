package me.deshark.lms.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.deshark.lms.infrastructure.persistence.UuidV7Generator;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaEntity {

    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(
        name = "uuid-v7",
        type = UuidV7Generator.class
    )
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    // 已经在数据库中设置默认值
    private Timestamp createdAt;

    // 已经在数据库中设置默认值
    private String role;

    // 已经在数据库中设置默认值
    private String status;

}
