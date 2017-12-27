package com.gexf.service;

import com.gexf.dataobject.ProductInfo;
import com.gexf.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Created by Gexf on 2017/7/20.
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     *
     * @return
     */

    Page<ProductInfo> findAll(Pageable pageable);
    
    Page<ProductInfo> findBySellerid(String sellerid,Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

	List<ProductInfo> findUpAll(String sellerid);

}
