package com.macro.mall.service;

import com.macro.mall.dto.PmsBrandParam;
import com.macro.mall.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
    /**
     * 获取品牌列表
     * @param param
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<PmsBrand> listBrand(String keyword, Integer pageSize, Integer pageNum);
}
