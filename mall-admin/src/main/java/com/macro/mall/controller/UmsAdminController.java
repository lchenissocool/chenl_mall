package com.macro.mall.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.UmsAdminLoginParam;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class UmsAdminController {
    private static Logger logger = LoggerFactory.getLogger(UmsAdminController.class);

    @Resource
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录
     * @param param
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam param) {
        String token = umsAdminService.login(param);
        if(StringUtils.isEmpty(token)) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }

    /**
     * 获取用户基本信息
     * @param principal
     * @return
     */
    @RequestMapping(value = "info",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getInfo(Principal principal) {
        logger.info("principal is {}" ,principal);
        String username = principal.getName();
        UmsAdmin admin = umsAdminService.getAdminByUsername(username);
        Map<String,Object> map = new HashMap<>();
        map.put("username",admin.getUsername());
        map.put("roles",new String[]{"TEST"});
        map.put("icon",admin.getIcon());
        return CommonResult.success(map);
    }

    /**
     * 刷新token
     * @param request
     * @return
     */
    @RequestMapping(value = "/token/refresh",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsAdminService.refreshToken(token);
        if(StringUtils.isEmpty(refreshToken)) {
            return CommonResult.failed();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("token",refreshToken);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }

    /**
     * 登出
     */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }
}
