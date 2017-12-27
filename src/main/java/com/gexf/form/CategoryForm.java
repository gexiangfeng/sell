package com.gexf.form;

import lombok.Data;

/**
 * Created by Gexf on 2017/8/10.
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;
}
