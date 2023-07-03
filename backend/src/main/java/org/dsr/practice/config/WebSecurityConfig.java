package org.dsr.practice.config;

import jakarta.servlet.http.HttpServletResponse;
import org.dsr.practice.services.UsersService;
import org.dsr.practice.utils.auth.AccountingUserDetailsService;
import org.dsr.practice.utils.auth.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig implements WebMvcConfigurer{

    private AuthProvider authProvider;
    private String frontendUrl;
    private String rememberMeKey;
    DataSource dataSource;

    public WebSecurityConfig(@Value("${backend.rememberMeKey}") String rememberMeKey, @Value("${frontend.url}") String frontendUrl, @Autowired DataSource dataSource, @Autowired AuthProvider authProvider){
        this.dataSource = dataSource;
        this.authProvider = authProvider;
        this.frontendUrl =frontendUrl;
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    UserDetailsService userDetailsService(){
        return new AccountingUserDetailsService();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        var configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin(frontendUrl);
        configuration.addAllowedHeader("Access-Control-Request-Headers");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Access-Control-Request-Method");
        configuration.setAllowedMethods(Arrays.asList( "GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http
                .authenticationManager(authManager(http))
                .authorizeHttpRequests()
                .requestMatchers("/auth/sendCode", "/login", "/logout").permitAll()
                .requestMatchers("/api/*").hasAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .formLogin().passwordParameter("code").usernameParameter("email")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                }).failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .deleteCookies("JSESSIONID", "remember-me")
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()).userDetailsService(userDetailsService())
                .alwaysRemember(true)
                .tokenValiditySeconds(24*60*60*14)
                .key(rememberMeKey);
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}