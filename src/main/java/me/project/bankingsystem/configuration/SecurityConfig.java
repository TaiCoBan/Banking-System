package me.project.bankingsystem.configuration;

import me.project.bankingsystem.filter.JwtAuthFilter;
import me.project.bankingsystem.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_SOURCES = {
            "/customers/add"
    };

    private static final String[] USER_SOURCES = {
            "/customers/get",
            "/customers/update/{cusId}",
            "/accounts/create",
            "/accounts/get/{accId}",
            "/accounts/get-all/{cusId}",
            "/accounts/update/{accId}",
            "/accounts/delete/{accId}",
            "/test/user"
    };

    private static final String[] USER_ADMIN_SOURCES = {
            "/accounts/create",
            "/accounts/get/{accId}",
            "/accounts/get-all/{cusId}",
            "/accounts/update/{accId}",
            "/accounts/delete/{accId}",
            "/transactions/deposit/{accId}",
            "/transactions/withdraw/{accId}/{atmId}",
            "/auth/generate-token"
    };

    private static final String[] ADMIN_SOURCES = {
            "/customers/get-all",
            "/customers/delete/{cusId}",
            "/accounts/create",
            "/accounts/get/{accId}",
            "/accounts/get-all/{cusId}",
            "/accounts/update/{accId}",
            "/accounts/delete/{accId}",
            "/ATMs/install",
            "/ATMs/cash-to-atm/{atmId}",
            "/ATMs/uninstall/{atmId}",
            "/test/admin"
    };

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/customers/add",
                                "/auth/generate-token").permitAll()
                        .requestMatchers("/accounts/create",
                                "/accounts/get/{accId}",
                                "/accounts/get-all/{cusId}",
                                "/accounts/update/{accId}",
                                "/accounts/delete/{accId}",
                                "/transactions/deposit/{accId}",
                                "/transactions/withdraw/{accId}/{atmId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/customers/get",
                                "/customers/update/{cusId}",
                                "/accounts/create",
                                "/accounts/get/{accId}",
                                "/accounts/get-all/{cusId}",
                                "/accounts/update/{accId}",
                                "/accounts/delete/{accId}",
                                "/test/user").hasRole("USER")
                        .requestMatchers("/customers/get-all",
                                "/customers/delete/{cusId}",
                                "/accounts/create",
                                "/accounts/get/{accId}",
                                "/accounts/get-all/{cusId}",
                                "/accounts/update/{accId}",
                                "/accounts/delete/{accId}",
                                "/ATMs/install",
                                "/ATMs/cash-to-atm/{atmId}",
                                "/ATMs/uninstall/{atmId}",
                                "/test/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
