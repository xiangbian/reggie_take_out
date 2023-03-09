package com.xiangbian.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.xiangbian.reggie.common.BaseContext;
import com.xiangbian.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录的过滤器
 *
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
          "/employee/login",
          "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2.判断此次请求是否需要处理
        boolean check = check(urls,requestURI);

        //3.不需要处理，直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4-1.判断登录状态，如果已登录，则直接放行
//        Object employee = request.getSession().getAttribute("employee");
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登录,用户id为：{}",request.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        //4-2.判断登录状态，如果已登录，则直接放行
//        Object employee = request.getSession().getAttribute("employee");
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已经登录,用户id为：{}",request.getSession().getAttribute("user"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }

        //5.如果未登录则返回未登录的结果，通过输出流方式向客户端页面写响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
