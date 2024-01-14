package hskl.cnse.chat.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(auth -> {
                                        auth.requestMatchers("/").permitAll();
                                        auth.requestMatchers("/register").permitAll();
                                        auth.requestMatchers("/favicon.ico").permitAll();
                                        auth.requestMatchers("/swagger-ui/**").permitAll();
                                        auth.requestMatchers("/actuator/**").hasRole("ADMIN");
                                        auth.requestMatchers("/secured/**").hasRole("USER");
                                        auth.requestMatchers("/index.html").hasRole("USER");
                                        auth.requestMatchers("/script.js").permitAll();
                                        auth.requestMatchers("/styles.js").permitAll();
                                        auth.anyRequest().authenticated();
                                })
                                .oauth2Login(oauth2 -> oauth2
                                                .defaultSuccessUrl("/index.html", true))
                                .formLogin(withDefaults()) /* TODO: Joshua Login Page anfertigen */
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login.html")
                                                )
                                .build();
        }

       


        @Bean
        public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                        PasswordEncoder passwordEncoder) {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        /* 
        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
                UserDetails user1 = User.withUsername("user1")
                                .password(passwordEncoder.encode("password"))
                                .roles("USER")
                                .build();

                UserDetails user2 = User.withUsername("user2")
                                .password(passwordEncoder.encode("password"))
                                .roles("USER")
                                .build();

                UserDetails user3 = User.withUsername("user3")
                                .password(passwordEncoder.encode("password"))
                                .roles("USER")
                                .build();

                UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder.encode("adminpassword"))
                                .roles("ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(user1, user2, user3, admin);
        } */

}
