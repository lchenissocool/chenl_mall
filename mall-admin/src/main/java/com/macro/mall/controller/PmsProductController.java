package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.service.PmsProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class PmsProductController {

    @Resource
    PmsProductService pmsProductService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> findList(PmsProductQueryParam param,
                                             @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {
        List<PmsProduct> pmsProductList = pmsProductService.list(param,pageSize,pageNum);
        return CommonResult.success(CommonPage.restPage(pmsProductList));
    }


    /**
     * 批量设置上新
     * @param ids
     * @param publishStatus
     * @return
     */
    @RequestMapping(value = "/update/publishStatus",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        return pmsProductService.updatePublishStatus(ids,publishStatus);
    }

    /**
     * 批量上线新品
     * @param ids
     * @param newStatus
     * @return
     */
    @RequestMapping(value = "/update/newStatus",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("newStatus") Integer newStatus) {
        int count = pmsProductService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 批量推荐
     * @param ids
     * @param recommendStatus
     * @return
     */
    @RequestMapping(value = "/update/recommendStatus",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = pmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
