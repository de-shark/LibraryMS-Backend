package me.deshark.lms.interfaces.dto.borrow;

import lombok.Getter;

import java.util.UUID;

/**
 * @author DE_SHARK
 */
@Getter
public class ReturnRequest {
    private UUID recordId;
}
