package me.deshark.lms.common.exception.domain;

import lombok.Getter;

/**
 * @author DE_SHARK
 */
@Getter
public class DomainException extends RuntimeException {

    // 领域错误码（如BORROW_LIMIT_EXCEEDED）
    private final String errorCode;

    public DomainException(String message) {
        super(message);
        this.errorCode = null;
    }

    public DomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
