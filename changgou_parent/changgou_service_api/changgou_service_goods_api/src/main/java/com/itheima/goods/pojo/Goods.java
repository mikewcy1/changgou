package com.itheima.goods.pojo;

import java.io.Serializable;
import java.util.List;

/*
 * @author 王昌耀
 * @date 2020/10/30 14:05
 */
public class Goods implements Serializable {

    private Spu spu;
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
