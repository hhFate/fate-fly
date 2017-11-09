package cn.reinforce.web.fly.config;

import cn.reinforce.web.fly.interceptor.LoginInterceptor;
import cn.reinforce.web.fly.interceptor.RequestLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 幻幻Fate
 * @create 2017/11/9
 * @since
 */
@Configuration
public class FateWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private RequestLogInterceptor requestLogInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 配置拦截器
     *
     * @param registry
     * @author lance
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/profile/**")
                .addPathPatterns("/lay/**")
                .addPathPatterns("/requestLog/**")
                .addPathPatterns("/contacts/**")
                .addPathPatterns("/moment/**")
                .addPathPatterns("/m/**")
                .addPathPatterns("/index")
        ;
    }
}
