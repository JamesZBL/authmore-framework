package me.zbl.authmore.main;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-03
 */
@Configuration
public class TokenManagerConfiguration {

    private final AccessTokenRepository tokens;
    private final RefreshTokenRepository refreshTokens;
    private final RedisTemplate<String, String> redisTemplate;
    private final ClientDetailsRepository clients;
    private final CodeRepository codes;

    public TokenManagerConfiguration(
            AccessTokenRepository tokens,
            RefreshTokenRepository refreshTokens,
            RedisTemplate<String, String> redisTemplate,
            ClientDetailsRepository clients,
            CodeRepository codes) {
        this.tokens = tokens;
        this.refreshTokens = refreshTokens;
        this.redisTemplate = redisTemplate;
        this.clients = clients;
        this.codes = codes;
    }

    @Bean
    @ConditionalOnMissingBean({TokenManager.class})
    public TokenManager tokenManager() {
        return new RedisTokenManager(tokens, refreshTokens, redisTemplate, clients);
    }

    @Bean
    @ConditionalOnMissingBean({CodeManager.class})
    public CodeManager codeManager() {
        return new RedisCodeManager(codes);
    }
}
