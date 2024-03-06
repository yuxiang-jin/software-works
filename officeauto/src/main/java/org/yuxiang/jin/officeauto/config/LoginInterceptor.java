package org.yuxiang.jin.officeauto.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yuxiang.jin.officeauto.commonutil.OaContants;
import org.yuxiang.jin.officeauto.commonutil.UserHolder;
import org.yuxiang.jin.officeauto.domain.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截到用户的请求了
        String requestUrl = request.getRequestURL().toString();
        System.out.println("requestUrl:" + requestUrl);
        //判断session是否存在用户,如果存在说明用户已经登录了,应该放行
        User user = (User) request.getSession().getAttribute(OaContants.USER_SESSION);
        if (user != null) {
            System.out.println("requestUrl:" + requestUrl + "->被放行！");
            //当前请求：每个请求是否都是一个线程
            UserHolder.addCurrentUser(user);
            return true;
        } else {
            //重定向
            response.sendRedirect(request.getContextPath() + "/oa/login");
            System.out.println("requestUrl:" + requestUrl + "->被拦截！");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeCurrentUser();
    }
}
