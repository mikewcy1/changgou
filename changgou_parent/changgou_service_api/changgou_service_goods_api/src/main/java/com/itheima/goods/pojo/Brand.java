package com.itheima.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author 王昌耀
 * @date 2020/10/26 18:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_brand")
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//品牌id
    private String name;//品牌名称
    private String image;//品牌图片地址
    private String letter;//品牌的首字母
    private Integer seq;//排序
}
