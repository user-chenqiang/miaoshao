package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//创建商品的controller
@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController{
    @Autowired
    private ItemService itemService;
    //创建商品信息
    @RequestMapping(value="/create",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam (name = "title")String title,
                                       @RequestParam (name = "description")String description,
                                       @RequestParam (name = "price") BigDecimal price,
                                       @RequestParam (name = "stock")Integer stock,
                                       @RequestParam (name = "imgUrl")String imgUrl
                                       ) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO=convertVOFromModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);

    }
    //商品详情页浏览我的的浏览功能一般，采用get，就是对服务端不发生任何变化的幂等操作
    @RequestMapping(value="/get",method = {RequestMethod.GET})
    @ResponseBody
    private CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel){
        if (itemModel==null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!=null){
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemVO.setPromoStatus(0);
        }
        return itemVO;
        //这里的VO和Model差不多，在我们企业中VO层和model层定义是完全不一样的，而且许多用到了聚合操作，
        // 比如说我们model层到dataobject层已经用到了一些库存字段，
        // 是通过dataobject层聚合出来的，对应voobject我们为了一些前端交互思想的方便很多的时候我们吧vo层定义的比model定义的更大
        //比如说为了前端性能考虑，我们会聚合上一些活动的价格信息等等因此我们的vo在很多时候会变得很复杂因此分层是必须的
    }
    //商品列表页面浏览
    @RequestMapping(value="/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();
        //使用spring api将list内的itemMode转化为ITEMVO；
        List<ItemVO> itemVoList=itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
                    return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
    }
}
