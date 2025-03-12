package me.deshark.lms.interfaces.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 分页响应DTO
 *
 * <p>用于向客户端返回分页查询结果</p>
 *
 * @param <T> 数据项类型
 */
@Data
@Builder
public class PageResponse<T> {
    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总条目数
     */
    private long totalItems;

    /**
     * 创建分页响应
     *
     * @param data        数据列表
     * @param currentPage 当前页码
     * @param totalPages  总页数
     * @param totalItems  总条目数
     * @param <T>         数据项类型
     * @return 分页响应
     */
    public static <T> PageResponse<T> of(List<T> data, int currentPage, int totalPages, long totalItems) {
        return PageResponse.<T>builder()
                .data(data)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalItems(totalItems)
                .build();
    }
}