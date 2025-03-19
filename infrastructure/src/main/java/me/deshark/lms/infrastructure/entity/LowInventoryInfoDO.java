package me.deshark.lms.infrastructure.entity;

import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
public class LowInventoryInfoDO {
    private String isbn;
    private int currentCopyCount;
}
