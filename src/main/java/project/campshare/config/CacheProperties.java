package project.campshare.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component@ConfigurationProperties(prefix = "cache.redis")
public class CacheProperties {

   private final Map<String,Long>ttl = new HashMap<>();
}
