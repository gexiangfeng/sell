package com.gexf.controller;

import com.gexf.config.ProjectUrlConfig;
import com.gexf.constant.CookieConstant;
import com.gexf.constant.RedisConstant;
import com.gexf.enums.ResultEnum;
import com.gexf.exception.SellException;
import com.gexf.utils.CookieUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

/**
 * Created by Gexf on 2017/7/23.
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1 配置
        String url = projectUrlConfig.getWechatMpAuthorize() + "/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权获取 code],resule={}", redirectUrl);

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {


        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        String openId = null;
        try {
        	log.info("微信授权code: " + code);
        	openId =  stringRedisTemplate.opsForValue().get(code);
        	log.info("openId: " + openId);
        	if(openId == null || "".equals(openId)){
        		wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        		openId = wxMpOAuth2AccessToken.getOpenId();
                Integer expire = RedisConstant.EXPIRE;
                stringRedisTemplate.opsForValue().set(code, openId, expire, TimeUnit.SECONDS);
                
        	}
        } catch (WxErrorException ex) {
            log.error("【微信网页授权】{}", ex);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), ex.getError().getErrorMsg());
        }

        
        log.info("opiedId2: " + openId);
        log.info("state: " + returnUrl);
        String redirectUrl = "redirect:" + returnUrl + "?openid=" + openId;
        if(returnUrl.indexOf("?") > -1){
        	redirectUrl = "redirect:" + returnUrl + "&openid=" + openId;
        }
        return  redirectUrl;
    }


    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        //1 配置
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/wechat/qruserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        log.info("[微信网页登陆获取 code],resule={}", redirectUrl);

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qruserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException ex) {
            log.error("【微信网页登陆】{}", ex);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), ex.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("opiedId: " + openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
