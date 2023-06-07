package com.biology.commons.interact.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 13:40
 * @desc: 分页对象
 */
@ApiModel("分页对象")
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    protected Long total = 0L;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数，默认 10")
    protected Long size;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    protected Long current;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    protected Long pages = 1L;

    /**
     * 结果列表
     */
    @ApiModelProperty(value = "结果列表")
    private List<T> rows;

    public PageVo() {
        this.current = 1L;
        this.size = 10L;
    }

    public PageVo(Long currentPage, Long pageSize) {
        this.current = currentPage <= 0 ? 1 : currentPage;
        this.size = pageSize <= 0 ? 1 : pageSize;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long pageSize) {
        this.size = pageSize;
    }

    public Long getCurrent() {
        return this.current;
    }

    public PageVo<T> setCurrent(Long current) {
        this.current = current;
        return this;
    }

    public Long getTotal() {
        return this.total;
    }

    public PageVo<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Long getPages() {
        return this.pages;
    }

    /**
     * @author: lichong
     * @param:
     * @return:
     * @description: 设置结果 及总页数
     * @date: 2022/4/11
     */
    public void build(List<T> rows) {
        this.setRows(rows);
        Long count = this.getTotal();
        Long divisor = count / this.getSize();
        Long remainder = count % this.getSize();
        this.setPages(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
