package project.campshare.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter@Setter@Component@ConfigurationProperties(prefix = "redis-cache-constants")
public class CacheProperties {

    private Map<String,Long>ttl;

    @Getter@Setter
    public static class CacheNameTimeout{
        private String name;
        private String timeout;
    }
}
