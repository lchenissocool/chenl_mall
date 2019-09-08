package com.macro.mall.service;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {
    /**
     * 获取商品列表（分页）
     * @param param
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<PmsProduct> list(PmsProductQueryParam param, Integer pageSize, Integer pageNum);

    /**
     * 批量上下架商品
     * @param ids
     * @param publishStatus
     * @return
     */
    CommonResult updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量设置新品
     * @param ids
     * @param newStatus
     * @return
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量推荐新品
     * @param ids
     * @param recommendStatus
     * @return
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
}
