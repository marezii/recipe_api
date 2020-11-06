package guru.springframework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

//    @Value("${security.oauth2.client.clientId}")
//    private String clientId;
//
//    @Value("${security.oauth2.client.clientSecret}")
//    private String clientSecret;

//    @Value("${security.oauth2.resource.token-info-uri}")
//    private String checkTokenURI;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setClientId(clientId);
//        tokenServices.setClientSecret(clientSecret);
//        tokenServices.setCheckTokenEndpointUrl(checkTokenURI);
//        return tokenServices;
//    }
}