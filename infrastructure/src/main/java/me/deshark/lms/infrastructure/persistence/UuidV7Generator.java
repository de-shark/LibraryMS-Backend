package me.deshark.lms.infrastructure.persistence;

import com.github.f4b6a3.uuid.alt.GUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author DE_SHARK
 */
public class UuidV7Generator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return GUID.v7().toUUID();
    }
}
