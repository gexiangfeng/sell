package com.gexf.dataobject;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Gexf on 2017/8/10.
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
        
    private String name;
    
    private String description;
    
    private Integer deliveryTime;
    
    private BigDecimal score;
    
    private BigDecimal serviceScore;
    
    private BigDecimal foodScore;
    
    private BigDecimal rankRate;
    
    private BigDecimal minPrice;
    
    private BigDecimal deliveryPrice;
    
    private Integer ratingCount;
    
    private Integer sellCount;
    
    private String bulletin;
    
    private String supports;
    
    private String avatar;
    
    private String pics;
    
    private String infos;
}
