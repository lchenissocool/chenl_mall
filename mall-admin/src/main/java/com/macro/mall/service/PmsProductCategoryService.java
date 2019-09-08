package com.macro.mall.service;

import com.macro.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

public interface PmsProductCategoryService {
    /**
     * 查询所有一级分类及子分类
     * @return
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
