package me.deshark.lms.application.cqrs.core;

/**
 * @author DE_SHARK
 */
public interface QueryHandler<Q extends Query<R>, R>{
    /**
     * 处理查询并返回结果
     * @param query 查询对象
     * @return 查询结果
     */
    R handle(Q query);
}
