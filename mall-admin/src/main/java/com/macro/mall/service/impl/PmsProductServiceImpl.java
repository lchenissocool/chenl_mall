package com.macro.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.service.PmsProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductServiceImpl implements PmsProductService{

    @Resource
    PmsProductMapper pmsProductMapper;
    @Resource
    PmsProductDao pmsProductDao;

    @Override
    public List<PmsProduct> list(PmsProductQueryParam param, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(!StringUtils.isEmpty(param.getPublishStatus())) {
            criteria.andPublishStatusEqualTo(param.getPublishStatus());
        }
        if(!StringUtils.isEmpty(param.getVerifyStatus())) {
            criteria.andVerifyStatusEqualTo(param.getVerifyStatus());
        }
        if(!StringUtils.isEmpty(param.getKeyword())) {
            criteria.andKeywordsEqualTo(param.getKeyword());
        }
        if(!StringUtils.isEmpty(param.getProductSn())) {
            criteria.andProductSnEqualTo(param.getProductSn());
        }
        if(!StringUtils.isEmpty(param.getBrandId())) {
            criteria.andBrandIdEqualTo(param.getBrandId());
        }
        if(!StringUtils.isEmpty(param.getProductCategoryId())) {
            criteria.andProductCategoryIdEqualTo(param.getProductCategoryId());
        }
        List<PmsProduct> pmsProductList = pmsProductMapper.selectByExample(productExample);
        return pmsProductList;
    }

    @Override
    public CommonResult updatePublishStatus(List<Long> ids, Integer publishStatus) {
        if(CollectionUtils.isEmpty(ids)) {
            return null;
        }
        List<PmsProduct> productList = new ArrayList<>();
        ids.stream().forEach(t -> {
            PmsProduct product = new PmsProduct();
            product.setId(t);
            product.setPublishStatus(publishStatus);
            productList.add(product);
        });
        int count = pmsProductDao.updatePublishStatusBatch(productList);
        if(count != productList.size()) {
            return CommonResult.failed("更新失败");
        }
        return CommonResult.success("更新成功");
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(record, example);
    }
}
