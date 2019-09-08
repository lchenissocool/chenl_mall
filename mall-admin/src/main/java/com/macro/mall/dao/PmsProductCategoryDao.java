package com.macro.mall.dao;

import com.macro.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

public interface PmsProductCategoryDao {

    /**
     * 查询所有一级分类及子分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
