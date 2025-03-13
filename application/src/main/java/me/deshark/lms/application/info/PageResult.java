package me.deshark.lms.application.info;

import java.util.List;

/**
 * 分页结果DTO
 *
 * <p>用于在应用层内部传递分页查询结果</p>
 *
 * @param <T> 数据项类型
 *
 * @author DE_SHARK
 */
public record PageResult<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems
) {
    /**
     * 创建分页结果
     *
     * @param data        数据列表
     * @param currentPage 当前页码
     * @param totalPages  总页数
     * @param totalItems  总条目数
     * @param <T>         数据项类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> data, int currentPage, int totalPages, long totalItems) {
        return new PageResult<>(data, currentPage, totalPages, totalItems);
    }
}
