package com.seckillmall.controller;

import com.seckillmall.controller.viewobject.ItemVO;
import com.seckillmall.error.BusinessException;
import com.seckillmall.response.CommonReturnType;
import com.seckillmall.service.ItemService;
import com.seckillmall.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
//对跨域请求参数进行设置保证session中的信息跨域得到读取
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    ItemService itemService;

    @Autowired
    RedisTemplate redisTemplate;

    //创建商品
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl,
                                       @RequestParam(name = "description") String description) throws BusinessException {
        // 封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        itemModel.setDescription(description);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = this.convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    //展示单件商品
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id) {

        //根据商品id到redis查询缓存
        ItemModel itemModel = (ItemModel) redisTemplate.opsForValue().get("item_"+id);
        //不存在则读service
        if(itemModel == null) {
            itemModel = itemService.getItemById(id);
            //设置进去
            redisTemplate.opsForValue().set("item_"+id, itemModel);
            redisTemplate.expire("item_"+id, 10, TimeUnit.MINUTES);
        }

        ItemVO itemVo = this.convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVo);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if(itemModel == null) {
            return null;
        }
        ItemVO itemVo = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVo);
        if(itemModel.getPromoModel() != null){
            //有秒杀活动
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setStartDate(itemModel.getPromoModel().getStartDate().toString(
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoId(itemModel.getPromoModel().getId());
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            itemVo.setPromoStatus(0);
        }

        return itemVo;
    }

    //商品列表页浏览
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){

        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVoList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVo = convertVOFromModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
    }
}
