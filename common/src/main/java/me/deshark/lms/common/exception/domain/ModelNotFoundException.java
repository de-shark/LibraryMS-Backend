package me.deshark.lms.common.exception.domain;

/**
 * 实体不存在异常
 * @author DE_SHARK
 */
public class ModelNotFoundException extends DomainException {

    public ModelNotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ModelNotFoundException(String message) {
        super(message);
    }
}
