package com.example.usermanagement.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * Unified paginated API response data.
 *
 * @param <T> record data type
 * @author liulang
 */
@Data
public class PageResponse<T> {

    private List<T> records;

    private long total;

    private long current;

    private long size;

    private long pages;

    /**
     * Builds a paginated response from a MyBatis-Plus page result.
     *
     * @param page MyBatis-Plus page result
     * @param <T> record data type
     * @return paginated response
     */
    public static <T> PageResponse<T> from(IPage<T> page) {
        PageResponse<T> response = new PageResponse<T>();
        response.setRecords(page.getRecords());
        response.setTotal(page.getTotal());
        response.setCurrent(page.getCurrent());
        response.setSize(page.getSize());
        response.setPages(page.getPages());
        return response;
    }
}
