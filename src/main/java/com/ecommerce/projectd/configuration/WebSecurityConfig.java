package com.ecommerce.projectd.configuration;

import com.ecommerce.projectd.components.RateLimitFilterComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ecommerce.projectd.security.filters.JWTAuthorizationFilter;
import com.ecommerce.projectd.security.filters.UserAuthenticationFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final RateLimitFilterComponent rateLimitFilterComponent;
    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             JWTAuthorizationFilter jwtAuthorizationFilter1,
                             RateLimitFilterComponent rateLimitFilterComponent){
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter1;
        this.rateLimitFilterComponent = rateLimitFilterComponent;
    }

    private static final String[] AUTHORIZED_REQUEST = {"/user/reg","/file/*","/rabbit/init","/irrigation/create/**","/system/user/**","/irrigation/system/**","/system/create"};

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5173","http://localhost:5173"
                        )
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")
                        .allowedHeaders("*")
                        .exposedHeaders("*");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authManager) throws Exception{

        UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authManager);
        authenticationFilter.setFilterProcessesUrl("/login");

        return http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTHORIZED_REQUEST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UserAuthenticationFilter.class)
                .addFilterBefore(rateLimitFilterComponent, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

