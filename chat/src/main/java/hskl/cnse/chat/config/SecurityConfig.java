package hskl.cnse.chat.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import hskl.cnse.chat.services.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

         @Autowired
         private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                authorizeHttp -> {
                                                        authorizeHttp.requestMatchers("/").permitAll();
                                                        authorizeHttp.requestMatchers("/favicon.svg").permitAll();
                                                        authorizeHttp.requestMatchers("/error").permitAll();
                                                        authorizeHttp.requestMatchers("/register").permitAll();
                                                        authorizeHttp.requestMatchers("/login").permitAll();
                                                        authorizeHttp.requestMatchers("/chat").hasAnyAuthority("ADMIN","OIDC_USER");
                                                        authorizeHttp.requestMatchers("static/css/**").permitAll();
                                                        authorizeHttp.requestMatchers("static/js/**").permitAll();
                                                        authorizeHttp.anyRequest().authenticated();
                                                })
                                .formLogin(
                                                form -> form
                                                                .loginPage("/login")
                                                                .loginProcessingUrl("/login")
                                                                .defaultSuccessUrl("/chat")
                                                                .permitAll())
                                .oauth2Login(
                                                oauth2 -> oauth2
                                                                .loginPage("/login")
                                                                .defaultSuccessUrl("/chat")
                                                                .successHandler(oAuth2AuthenticationSuccessHandler))
                                .httpBasic(withDefaults())
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}