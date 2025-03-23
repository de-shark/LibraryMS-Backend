package me.deshark.lms.common.exception.domain;

/**
 * 参数校验异常
 * @author DE_SHARK
 */
public class DomainValidationException extends DomainException {

    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(String errorCode, String message) {
        super(errorCode, message);
    }
}
