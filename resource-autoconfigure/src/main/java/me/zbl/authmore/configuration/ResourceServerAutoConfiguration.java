package me.zbl.authmore.configuration;

import me.zbl.authmore.main.ResourceServerConfigurationProperties;
import me.zbl.authmore.main.ResourceServerFilter;
import me.zbl.authmore.main.ResourceServerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-28
 */
@Configuration
@ConditionalOnClass({ResourceServerFilter.class})
@EnableConfigurationProperties({ResourceServerConfigurationProperties.class})
public class ResourceServerAutoConfiguration implements WebMvcConfigurer, SmartInitializingSingleton {

    private final ResourceServerConfigurationProperties resourceProperties;

    public ResourceServerAutoConfiguration(ResourceServerConfigurationProperties resourceProperties) {
        this.resourceProperties = resourceProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceServerInterceptor(resourceProperties));
    }

    @Bean
    public ResourceServerFilter oAuthResourceServerFilter() {
        return new ResourceServerFilter(resourceProperties);
    }

    @Override
    public void afterSingletonsInstantiated() {
        String tokenInfoUrl = resourceProperties.getTokenInfoUrl();
        String clientId = resourceProperties.getClientId();
        String clientSecret = resourceProperties.getClientSecret();
        if (!isEmpty(tokenInfoUrl)) {
            if (isEmpty(clientId) || isEmpty(clientSecret))
                throw new IllegalStateException("If token-info-url value is specified, a client-id " +
                        "and a client-secret is also required.");
        }
    }
}
