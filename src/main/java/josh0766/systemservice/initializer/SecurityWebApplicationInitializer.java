package josh0766.systemservice.initializer;

import josh0766.systemservice.configuration.SecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer () {
        super(SecurityConfiguration.class);
    }
}
