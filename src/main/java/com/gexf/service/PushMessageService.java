package com.gexf.service;

import com.gexf.dto.OrderDTO;

/**
 * 微信模板消息推送
 * Created by Gexf on 2017/8/13.
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息推送
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
