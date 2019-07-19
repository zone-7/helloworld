package com.zone7.demo.helloworld.config.filter;

import com.google.common.collect.Sets;
import com.zone7.demo.helloworld.sys.common.RequestHolder;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * RequestFilter
 * 请求过滤器
 * 安全认证
 * @author: zone7
 * @time: 2019.02.19
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {

    private static Set<String> URL_WHITE_LIST = Sets.newHashSet();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //忽略不过旅的路径
        URL_WHITE_LIST.add("/unauth");
        URL_WHITE_LIST.add("/login");
        URL_WHITE_LIST.add("/logout");

        URL_WHITE_LIST.add("/sys/unauth");
        URL_WHITE_LIST.add("/sys/login");
        URL_WHITE_LIST.add("/sys/logout");

        URL_WHITE_LIST.add("/static");
        URL_WHITE_LIST.add("/actuator");
        URL_WHITE_LIST.add("/oauth");//oauth2.0默认接口
        URL_WHITE_LIST.add("/api"); // 外部接口采用oauth进行权限验证
    }

    private boolean isWhiteUrl(String requestUrl){
        if (URL_WHITE_LIST.contains(requestUrl)){
            return true;
        }
        for(String str:URL_WHITE_LIST){

            if(requestUrl.startsWith(str)){
                return true;
            }

        }

        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        if (isWhiteUrl(requestUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        SysUserVo userVo = (SysUserVo) request.getSession().getAttribute("user");
        if (userVo == null) {
            request.getRequestDispatcher("/unauth").forward(request, response);
            return;
        }

        RequestHolder.add(userVo);
        RequestHolder.add(request);


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
