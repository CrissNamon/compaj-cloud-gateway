package tech.hiddenproject.compajcloud.gatewayservice.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Autowired
  private ReactiveClientRegistrationRepository clientRegistrationRepository;

  @Value("${spring.security.oauth2.client.logout-uri}")
  private String logoutUrl;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http) {
    return http
        .csrf()
        .disable()
        .authorizeExchange()
        .pathMatchers("/ui-service/*")
        .authenticated()
        .pathMatchers("/hello")
        .authenticated()
        .pathMatchers("/**")
        .permitAll()
        .and()
        .oauth2Login()
        .authorizedClientRepository(authorizedClientRepository())
        .and()
        .logout()
        .logoutHandler(logoutHandler())
        .logoutSuccessHandler(oidcLogoutSuccessHandler())
        .and().build();
  }

  public ServerLogoutHandler logoutHandler() {
    return new DelegatingServerLogoutHandler(new SecurityContextServerLogoutHandler(), new WebSessionServerLogoutHandler());
  }

  public ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
    logoutSuccessHandler.setLogoutSuccessUrl(URI.create(logoutUrl));
    return logoutSuccessHandler;
  }

  @Bean
  public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
    return new WebSessionServerOAuth2AuthorizedClientRepository();
  }

}
