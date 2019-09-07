package com.macro.mall.service.impl;

import com.macro.mall.dao.UmsAdminRoleRelationDao;
import com.macro.mall.dto.UmsAdminLoginParam;
import com.macro.mall.mapper.UmsAdminLoginLogMapper;
import com.macro.mall.mapper.UmsAdminMapper;
import com.macro.mall.model.*;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UmsAdminServiceImpl implements UmsAdminService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UmsAdminMapper adminMapper;
    @Resource
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Resource
    private UmsAdminLoginLogMapper adminLoginLogMapper;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public String login(UmsAdminLoginParam param) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(param.getUsername());
            if(!passwordEncoder.matches(param.getPassword(),userDetails.getPassword())) {
                throw new BadCredentialsException("用户账号或密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            // 添加登陆记录
            this.insetLoginLog(param.getUsername());
        } catch (AuthenticationException e) {
            LOGGER.warn("登陆异常: {}",e.getMessage());
        }
        return token;
    }

    private void insetLoginLog(String username) {
        if(StringUtils.isEmpty(username)) {
            return;
        }
        UmsAdmin admin = this.getAdminByUsername(username);
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        loginLog.setIp(request.getRequestURI());
        adminLoginLogMapper.insert(loginLog);
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if(adminList.size() != 1) {
            return null;
        }
        UmsAdmin admin = adminList.get(0);
        return admin;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long id) {
        return adminRoleRelationDao.getPermission(id);
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if(jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
