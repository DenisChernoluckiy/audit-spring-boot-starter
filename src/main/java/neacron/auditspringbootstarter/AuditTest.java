package neacron.auditspringbootstarter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditTest {


    private final AuditService auditService;

    public AuditTest(AuditService auditService) {
        this.auditService = auditService;
    }

    @Audit
    @PostMapping("/{id}")
    public void lala(@PathVariable Integer id){
        System.err.println(auditService.getMyUrlServer());
    }
}
