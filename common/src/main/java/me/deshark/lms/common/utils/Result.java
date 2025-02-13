package me.deshark.lms.common.utils;

/**
 * @author DE_SHARK
 * @date 2025/2/13 22:53
 */
public class Result<T, E> {

    private final T ok;
    private final E err;
    private final boolean isOk;

    private Result(T ok, E err, boolean isOk) {
        this.ok = ok;
        this.err = err;
        this.isOk = isOk;
    }

    public static <T, E> Result<T, E> ok(T value) {
        return new Result<>(value, null, true);
    }

    public static <T, E> Result<T, E> err(E err) {
        return new Result<>(null, err, false);
    }

    public boolean isOk() {
        return isOk;
    }

    public T getOk() {
        return ok;
    }

    public E getErr() {
        return err;
    }
}
