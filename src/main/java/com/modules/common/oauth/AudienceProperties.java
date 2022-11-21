package com.modules.common.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author v_vllchen
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "audience")
public class AudienceProperties {

	private String clientId;

    private String base64Secret;

    private String name;

    private int expiresSecond = 120;
}
