package com.xwz.mangayomu.entity;

public class PageInfo {
    private Integer pageNo;
    private String path;

    public PageInfo(Integer pageNo, String path) {
        this.pageNo = pageNo;
        this.path = path;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
