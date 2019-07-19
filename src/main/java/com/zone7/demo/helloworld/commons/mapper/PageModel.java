package com.zone7.demo.helloworld.commons.mapper;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * PageModel
 * 分页查询返回实体类
 *
 * @author: zone7
 * @time: 2018.08.29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> implements Serializable {

    public PageModel(List<T> list, PageInfo pageInfo) {
        this.list = list;
        this.totalNum = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }



    /**
     * 当页数据
     */
    private List<T> list;

    private Integer pageSize;
    private Integer pageNum;
    /**
     * 数据总条数
     */
    private Long totalNum;

}
