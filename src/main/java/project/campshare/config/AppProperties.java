package project.campshare.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("external-certification")
public class AppProperties {
    private final String emailFromAddress;
    private final String coolSmsKey;
    private final String coolSmsSecret;
    private final String coolSmsFromPhoneNumber;

}
