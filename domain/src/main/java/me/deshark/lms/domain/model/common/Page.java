package me.deshark.lms.domain.model.common;

import java.util.List;

/**
 * * 领域层分页模型
 * @author DE_SHARK
 */
public class Page<T> {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;

    public Page(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return pageSize == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) pageSize);
    }

    public boolean hasNext() {
        return (long) (pageNumber + 1) * pageSize < totalElements;
    }

    public boolean hasPrevious() {
        return pageNumber > 0;
    }
}
