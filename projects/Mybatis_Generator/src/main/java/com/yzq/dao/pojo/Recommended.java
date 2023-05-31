package com.yzq.dao.pojo;

public class Recommended {
    private Integer id;

    private Double com;

    private Integer cnt;

    private Integer shopid;

    public Recommended(Integer id, Double com, Integer cnt, Integer shopid) {
        this.id = id;
        this.com = com;
        this.cnt = cnt;
        this.shopid = shopid;
    }

    public Recommended() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCom() {
        return com;
    }

    public void setCom(Double com) {
        this.com = com;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }
}