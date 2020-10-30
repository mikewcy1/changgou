package com.itheima.goods.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*
 * @author 王昌耀
 * @date 2020/10/30 16:39
 */
@Table(name = "tb_category_brand")
public class CategoryBrand implements Serializable {
    @Id
    private Integer categoryId;
    @Id
    private Integer brandId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
