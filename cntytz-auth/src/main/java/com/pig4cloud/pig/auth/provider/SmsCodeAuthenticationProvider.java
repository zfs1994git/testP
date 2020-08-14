package com.pig4cloud.pig.auth.provider;

import com.pig4cloud.pig.auth.granter.SmsCodeAuthenticationToken;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : lixueliang
 * @version : v1.0
 * @description : 短信登录的身份验证Provide
 * @date: 2020-08-06 09:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //这个authentication就是SmsCodeAuthenticationToken
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String userName = (String) authenticationToken.getPrincipal();
        String openId = (String) authenticationToken.getCredentials();
        if (StringUtils.isBlank(openId)) {
            throw new BadCredentialsException("openId不可以为空");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(openId);
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new SmsCodeAuthenticationToken(userDetails, openId, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
