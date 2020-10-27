package com.itheima.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*
 * @author 王昌耀
 * @date 2020/10/27 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_spec")
public class Spec implements Serializable {

    @Id
    private Integer id;//ID
    private String name;//名称
    private String options;//规格选项
    private Integer seq;//排序
    private Integer templateId;//模板ID
}
