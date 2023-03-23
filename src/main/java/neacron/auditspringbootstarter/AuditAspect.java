package neacron.auditspringbootstarter;

//import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
//import org.json.JSONObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
//import java.util.Arrays;

import java.util.Optional;

import static java.util.Arrays.stream;

@Slf4j
@Component
@Aspect
public class AuditAspect {

    @Autowired
    private AuditProperties auditProperties;

    @Pointcut("@annotation(neacron.auditspringbootstarter.Audit)")
    public void hasAudit() {
    }

        @After("hasAudit()")
        public void logAudit(JoinPoint joinPoint) {

            stream(joinPoint.getArgs())
                    .forEach(o -> System.out.println("arg value: " + o.toString()));

            if (Optional.ofNullable(joinPoint.getArgs()).isEmpty()) {
                System.out.println("There's no args for method");
            }

            var url = auditProperties.getUrl();

            System.out.println("audit server url: " + url);
            System.out.println("Signature: " + joinPoint.getSignature());
            System.out.println("Kind: " + joinPoint.getKind());
            System.out.println("StaticPart: " + joinPoint.getStaticPart());
            System.out.println("Target: " + joinPoint.getTarget());

            var restTemplate = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            var personJsonObject = new JSONObject();
            personJsonObject.put("message", stream(joinPoint.getArgs()).findFirst().toString());
            personJsonObject.put("url", url);

            HttpEntity<String> requestEntity =
                    new HttpEntity<>(personJsonObject.toString(), headers);

            restTemplate.postForEntity(url, requestEntity, String.class);
        }
}
