package com.gexf.service.impl;

import com.gexf.dto.CartDTO;
import com.gexf.enums.ProductStatusEnum;
import com.gexf.dataobject.ProductInfo;
import com.gexf.enums.ResultEnum;
import com.gexf.exception.SellException;
import com.gexf.repository.ProductInfoRepository;
import com.gexf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by Gexf on 2017/7/20.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;


    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll(String sellerid) {

        return productInfoRepository.findByProductStatusAndSellerid(ProductStatusEnum.UP.getCode(),sellerid);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }
    @Override
	public Page<ProductInfo> findBySellerid(String sellerid,Pageable pageable) {
		// TODO Auto-generated method stub
    	return productInfoRepository.findBySellerid(sellerid, pageable);
	}
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
            }
            Integer resultNum = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(resultNum);
            productInfoRepository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
            }
            Integer num = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (num < 0) {
                throw new SellException(ResultEnum.PRODUC_STOCK_ERROR);
            }

            productInfo.setProductStock(num);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXI);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }


	
}
