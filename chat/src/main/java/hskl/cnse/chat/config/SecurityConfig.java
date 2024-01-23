package hskl.cnse.chat.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(
                                                authorizeHttp -> {
                                                        authorizeHttp.requestMatchers("/","/favicon.svg","/error","/register","/login","static/css/**","static/js/**").permitAll();
                                                        authorizeHttp.requestMatchers("/chat").hasAnyAuthority("ADMIN","OIDC_USER","USER");
                                                        authorizeHttp.anyRequest().authenticated();
                                                })
                                .formLogin(
                                                form -> form
                                                                .loginPage("/")
                                                                .loginProcessingUrl("/")
                                                                .defaultSuccessUrl("/chat")
                                                                .permitAll())
                                .oauth2Login(
                                                oauth2 -> oauth2
                                                                .loginPage("/")
                                                                .defaultSuccessUrl("/chat"))
                                .httpBasic(withDefaults())
                                .build();
        }


}