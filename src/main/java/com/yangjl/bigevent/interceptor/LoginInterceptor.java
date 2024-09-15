package com.yangjl.bigevent.interceptor;

import com.yangjl.bigevent.utils.JwtUtil;
import com.yangjl.bigevent.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌验证
        String token = request.getHeader("Authorization");
        if(!StringUtils.hasLength(token)) {
            log.info("token为空");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        try {
            // 解析 token
            ThreadLocalUtil.set(JwtUtil.parseToken(token));

            // redis 验证 token
            ValueOperations<String, String> operation = stringRedisTemplate.opsForValue();
            String originToken = operation.get("token_"+ThreadLocalUtil.getId());
            if(!StringUtils.hasLength(originToken)) {
                log.info("token已经失效");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            if(!originToken.equals(token)) {
                log.info("token不正确");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            return true;
        }catch (Exception e){
            log.info("token无效");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
