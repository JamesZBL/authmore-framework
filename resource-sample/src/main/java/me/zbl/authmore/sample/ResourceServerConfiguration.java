package me.zbl.authmore.sample;

import me.zbl.authmore.main.ResourceServerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
@Configuration
public class ResourceServerConfiguration {

    private final ResourceServerFilter resourceServerFilter;

    public ResourceServerConfiguration(ResourceServerFilter resourceServerFilter) {
        this.resourceServerFilter = resourceServerFilter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/");
        bean.setFilter(resourceServerFilter);
        return bean;
    }
}
