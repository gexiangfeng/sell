package com.gexf.service.impl;

import com.gexf.dataobject.SellerInfo;
import com.gexf.repository.SellerInfoRepository;
import com.gexf.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Gexf on 2017/8/10.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}

