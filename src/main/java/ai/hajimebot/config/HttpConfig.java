package ai.hajimebot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "http.api")
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpConfig {

    private String url;
}
