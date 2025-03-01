package me.deshark.lms.common.utils;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
public class GUID {
    public static UUID v7() {
        return com.github.f4b6a3.uuid.alt.GUID.v7().toUUID();
    }
}
