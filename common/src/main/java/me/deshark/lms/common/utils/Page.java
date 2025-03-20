package me.deshark.lms.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 自定义分页类，适用于 MyBatis
 *
 * @param <T> 分页数据类型
 * @author DE_SHARK
 */
@Data
public class Page<T> implements Serializable {

    // 当前页码（从 1 开始）
    private long current;

    // 每页记录数
    private long size;

    // 总记录数
    private long total;

    // 总页数（自动计算）
    private long pages;

    // 当前页的数据列表
    private List<T> records;

    // 默认构造函数
    public Page() {
        this.current = 1;
        this.size = 10;
        this.total = 0;
        this.pages = 0;
        this.records = Collections.emptyList();
    }

    // 带分页参数的构造函数
    public Page(long current, long size) {
        this.current = current <= 0 ? 1 : current;
        this.size = size <= 0 ? 10 : size;
        this.total = 0;
        this.pages = 0;
        this.records = Collections.emptyList();
    }

    // 计算总页数
    public void calculatePages() {
        if (this.size <= 0) {
            this.pages = 0;
        } else {
            this.pages = (this.total + this.size - 1) / this.size;
        }
    }

    // 获取偏移量（用于 SQL 的 LIMIT）
    public long getOffset() {
        return (this.current - 1) * this.size;
    }

    public void setTotal(long total) {
        this.total = total;
        calculatePages();
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}
