package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.service.PmsBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/brand")
public class PmsBrandController {

    @Resource
    private PmsBrandService pmsBrandService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> findList(@RequestParam(value = "keyword",required = false) String keyword,
                                                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {
        List<PmsBrand> pmsBrandList = pmsBrandService.listBrand(keyword,pageSize,pageNum);
        return CommonResult.success(CommonPage.restPage(pmsBrandList));
    }
}
