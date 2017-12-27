package com.gexf.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gexf.dataobject.OrderDetail;
import com.gexf.dto.OrderDTO;
import com.gexf.enums.ResultEnum;
import com.gexf.exception.SellException;
import com.gexf.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gexf on 2017/7/23.
 */
@Slf4j
public class OrderFormToOrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception ex) {
            log.error("【格式转换错误】restl={}", orderForm.getItems());
            log.error(ex.getMessage());
            throw new SellException(ResultEnum.PARAM_ERROR);

        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
