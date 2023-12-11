package hskl.cnse.chat.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2LoginConfig {
 /* 
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

   private ClientRegistration googleClientRegistration() {
    return ClientRegistration.withRegistrationId("google")
        .clientId("Ihr-Google-Client-ID")
        .clientSecret("Ihr-Google-Client-Geheimnis")
        .scope("openid", "profile", "email")
        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
        .tokenUri("https://oauth2.googleapis.com/token")
        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
        .userNameAttributeName(IdTokenClaimNames.SUB)
        .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
        .clientName("Google")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Setzen Sie den authorizationGrantType
        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}") // Setzen Sie die redirectUri
        .build();
}*/
}
