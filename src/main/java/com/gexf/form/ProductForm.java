package com.gexf.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Gexf on 2017/8/10.
 */
@Data
public class ProductForm {

    private String productId;

    /**
     * 商家.
     */
    private String sellerid;
    
    /**
     * 名字.
     */
    private String productName;

    /**
     * 单价.
     */
    private BigDecimal productPrice;

    /**
     * 库存.
     */
    private Integer productStock;

    /**
     * 描述.
     */
    private String productDescription;

    /**
     * 小图.
     */
    private String productIcon;


    /**
     * 类目编号.
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
