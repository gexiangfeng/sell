package com.gexf.repository;

import com.gexf.dataobject.ProductInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Gexf on 2017/7/20.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 通过产品状态查找产品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatusAndSellerid(Integer productStatus,String sellerid);
    
    /**
     * 通过商家查找产品
     * @param sellerid
     * @return
     */
    Page<ProductInfo> findBySellerid(String sellerid,Pageable pageable);
}
