package hskl.cnse.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import hskl.cnse.chat.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        /*
         * @Bean
         * SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         * return http
         * .authorizeHttpRequests(auth -> {
         * auth.requestMatchers("/", "/register", "/favicon.ico", "/swagger-ui/**",
         * "/login**", "/styles.css", "/scripts.css").permitAll();
         * auth.requestMatchers("/actuator/**").hasRole("ADMIN");
         * auth.requestMatchers("/chat", "/index", "/secured/**").hasRole("USER");
         * auth.anyRequest().authenticated();
         * })
         * .oauth2Login(oauth2 -> oauth2
         * .defaultSuccessUrl("/index.html", true))
         * .formLogin(withDefaults())
         * .logout(logout -> logout
         * .logoutSuccessUrl("/login.html"))
         * .build();
         * }
         * 
         */

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                                .antMatchers("/register").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .formLogin().loginPage("/login").permitAll()
                                .and()
                                .logout().permitAll();
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
         * @Bean
         * public InMemoryUserDetailsManager userDetailsService(PasswordEncoder
         * passwordEncoder) {
         * UserDetails user1 = User.withUsername("user1")
         * .password(passwordEncoder.encode("password"))
         * .roles("USER")
         * .build();
         * 
         * UserDetails user2 = User.withUsername("user2")
         * .password(passwordEncoder.encode("password"))
         * .roles("USER")
         * .build();
         * 
         * UserDetails user3 = User.withUsername("user3")
         * .password(passwordEncoder.encode("password"))
         * .roles("USER")
         * .build();
         * 
         * UserDetails admin = User.withUsername("admin")
         * .password(passwordEncoder.encode("adminpassword"))
         * .roles("ADMIN")
         * .build();
         * 
         * return new InMemoryUserDetailsManager(user1, user2, user3, admin);
         * }
         */

}
