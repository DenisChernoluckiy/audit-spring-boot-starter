package neacron.auditspringbootstarter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
public class AuditAutoConfiguration {

    @Bean
    public AuditService service(AuditProperties auditProperties) {
        return new AuditService(auditProperties);
    }
}
