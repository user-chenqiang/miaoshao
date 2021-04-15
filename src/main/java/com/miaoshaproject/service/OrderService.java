package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

public interface OrderService {
    //1通过前端url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始
    //2直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下档
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount,String stockLogIdS) throws BusinessException;
}
