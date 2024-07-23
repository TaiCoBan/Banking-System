package me.project.bankingsystem.configuration;

import me.project.bankingsystem.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
            "/accounts/delete/{accId}"
    };

    private static final String[] USER_ADMIN_SOURCES = {
            "/accounts/create",
            "/accounts/get/{accId}",
            "/accounts/get-all/{cusId}",
            "/accounts/update/{accId}",
            "/accounts/delete/{accId}",
            "/transactions/deposit/{accId}",
            "/transactions/withdraw/{accId}/{atmId}"
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
            "/ATMs/uninstall/{atmId}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers(PUBLIC_SOURCES).permitAll()
                        .requestMatchers(USER_ADMIN_SOURCES).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(USER_SOURCES).hasRole("USER")
                        .requestMatchers(ADMIN_SOURCES).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

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
