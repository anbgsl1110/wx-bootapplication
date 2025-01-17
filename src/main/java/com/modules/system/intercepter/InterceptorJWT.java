package com.modules.system.intercepter;

import com.modules.common.annotation.IgnoreSecurity;
import com.modules.common.oauth.AudienceProperties;
import com.modules.common.oauth.JwtHelper;
import com.modules.common.utils.RedisUtils;
import com.modules.system.exception.TokenErrorException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求api服务器时，对accessToken进行拦截判断，有效则可以返回接口，否则返回错误
 *
 * @author Win7
 */
@Component
@Slf4j
public class InterceptorJWT extends HandlerInterceptorAdapter {

    @Resource
    private AudienceProperties audiencePropertiesEntity;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        // 若目标方法忽略了安全性检查，则直接调用目标方法
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //如果类或者方法上有@IgnoreSecurity注解，则不需要进行token验证
            IgnoreSecurity annotation = ((HandlerMethod) handler).getMethod().getDeclaringClass().getAnnotation(IgnoreSecurity.class);
            if (annotation != null) {
                return true;
            }
            IgnoreSecurity ignoreSecurity = ((HandlerMethod) handler).getMethodAnnotation(IgnoreSecurity.class);
            if (ignoreSecurity != null) {
                return true;
            }
        }
        String accessToken = StringUtils.isNotBlank(request.getParameter("token")) ?
                request.getParameter("token") : request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            String headStr = accessToken.substring(0, 6).toLowerCase();
            if (headStr.equals("bearer")) {
                accessToken = accessToken.substring(6);
                Claims claims = JwtHelper.parseJWT(accessToken, audiencePropertiesEntity.getBase64Secret());
                //判断密钥是否相等，如果不等则认为时无效的token
                if (claims != null) {
                    Long userId = (Long) claims.get("userId");
                    //token未失效，token需要和redis服务器中的储存的token值一样才有效
                    String serviceToken = redisUtils.getToken(userId);
                    System.out.println("service redis token : " + serviceToken);
                    System.out.println("request accessToken : " + accessToken);
                    System.out.println("token" + (accessToken.equals(serviceToken) ? "一致" : "不一致"));
                    if (claims.getAudience().equals(audiencePropertiesEntity.getClientId()) && accessToken.equals(serviceToken)) {
                        request.setAttribute("userId", userId);
                        return true;
                    }
                } else {
                    log.error("token解码失败!");
                }
            }
        } else {
            log.error("请传递token!");
        }
        throw new TokenErrorException();
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        super.afterCompletion(request, response, handler, ex);
    }


    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request,
                                               HttpServletResponse response, Object handler) throws Exception {

        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
