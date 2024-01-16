package hskl.cnse.chat.config;

import static org.springframework.security.config.Customizer.withDefaults;

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

        // @Bean
        // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http
        // .csrf(AbstractHttpConfigurer::disable)
        // .authorizeHttpRequests((authorize) ->
        // authorize.requestMatchers("/register/**", "/styles.css", "/scripts.js")
        // .permitAll()
        // .requestMatchers("/index").permitAll()
        // .requestMatchers("/chat").hasRole("ADMIN"))
        // .formLogin(
        // form -> form
        // .loginPage("/login")
        // .loginProcessingUrl("/login")
        // .defaultSuccessUrl("/chat")
        // .permitAll())
        // .logout(
        // logout -> logout
        // .logoutRequestMatcher(
        // new AntPathRequestMatcher("/logout"))
        // .permitAll());
        // return http.build();
        // }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(
                                                authorizeHttp -> {
                                                        authorizeHttp.requestMatchers("/").permitAll();
                                                        authorizeHttp.requestMatchers("/favicon.svg").permitAll();
                                                        authorizeHttp.requestMatchers("/error").permitAll();
                                                        authorizeHttp.requestMatchers("/register").permitAll();
                                                        authorizeHttp.requestMatchers("/login").permitAll();
                                                        authorizeHttp.requestMatchers("/chat").hasRole("ADMIN");
                                                        authorizeHttp.requestMatchers("static/css/**").permitAll();
                                                        authorizeHttp.requestMatchers("static/js/**").permitAll();
                                                        authorizeHttp.anyRequest().authenticated();
                                                })
                                .formLogin(withDefaults())
                                .formLogin(
                                                form -> form
                                                                .loginPage("/login")
                                                                .loginProcessingUrl("/login")
                                                                .defaultSuccessUrl("/chat")
                                                                .permitAll())
                                .oauth2Login(withDefaults())
                                .httpBasic(withDefaults())
                                .build();
        }


}