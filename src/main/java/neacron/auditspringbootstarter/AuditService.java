package neacron.auditspringbootstarter;

public class AuditService {

    private final AuditProperties auditProperties;

    public AuditService(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    public String getMyUrlServer() {
        return auditProperties.getUrl();
    }
}
