package com.pentasecurity.isignplus.systemservice.initializer;

import com.pentasecurity.isignplus.systemservice.configuration.SecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer () {
        super(SecurityConfiguration.class);
    }
}
