package com.macro.mall.dao;

import com.macro.mall.model.PmsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductDao {
    /**
     * 批量修改状态
     * @param productList
     * @return
     */
    int updatePublishStatusBatch(@Param("productList") List<PmsProduct> productList);
}
