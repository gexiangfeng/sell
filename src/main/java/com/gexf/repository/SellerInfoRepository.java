package com.gexf.repository;

import com.gexf.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Created by Gexf on 2017/8/10.
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}

