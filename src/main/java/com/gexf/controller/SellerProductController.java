package com.gexf.controller;

import com.gexf.config.ProjectUrlConfig;
import com.gexf.constant.CookieConstant;
import com.gexf.constant.RedisConstant;
import com.gexf.dataobject.ProductCategory;
import com.gexf.dataobject.ProductInfo;
import com.gexf.exception.SellerAuthorizeException;
import com.gexf.form.ProductForm;
import com.gexf.service.CategoryService;
import com.gexf.service.ProductService;
import com.gexf.utils.CookieUtil;
import com.gexf.utils.KeyUtil;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Gexf on 2017/8/4.
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
    	String token = CookieUtil.getToken();

        //去redis里查询
        String sellerid = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(sellerid)) {
            log.warn("Redis中查不到sellerid");
        }
        
        PageRequest request = new PageRequest(page - 1, size);
        //Page<ProductInfo> productInfoPage = productService.findAll(request);
        Page<ProductInfo> productInfoPage = productService.findBySellerid(sellerid, request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView Index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {

        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("product/index", map);
    }

    @PostMapping("/save")
    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map,@RequestParam("uploadingFile") MultipartFile uploadingFile) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }


        try {
            ProductInfo productInfo = new ProductInfo();
            //更新
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            }
            //新增
            else {
                form.setProductId(KeyUtil.genUniqueKey());

                String token = CookieUtil.getToken();

                //去redis里查询
                String sellerid = redisTemplate.opsForValue().get(token);
                if (StringUtils.isEmpty(sellerid)) {
                    log.warn("Redis中查不到sellerid");
                }
                form.setSellerid(sellerid);
            
            }
            BeanUtils.copyProperties(form, productInfo);
            if(!"".equals(uploadingFile.getOriginalFilename())){
            	String uploadingdir =  this.getClass().getResource("/static").getPath() + "/images/";
                if(!new File(uploadingdir).exists()){
                	new File(uploadingdir).mkdirs();
                }
            	File file = new File(uploadingdir + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
                productInfo.setProductIcon(projectUrlConfig.getSell() + "/images/" + uploadingFile.getOriginalFilename());
            }else if("".equals(uploadingFile.getOriginalFilename()) && "".equals(productInfo.getProductIcon())){
            	productInfo.setProductIcon(projectUrlConfig.getSell() + "/images/default.jpeg");
            }
            
            productService.save(productInfo);

        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);

    }
}
