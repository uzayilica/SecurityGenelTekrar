package com.uzay.securitygeneltekrarr.security;

import com.uzay.securitygeneltekrarr.entity.Car;
import com.uzay.securitygeneltekrarr.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableAutoConfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    OAuth2LoginSuccessHandler auth2LoginSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http




               .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf->csrf.disable()
//                        .ignoringRequestMatchers("/csrf-token")
//                       .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                )
                .authorizeHttpRequests(
                        request->request
                                .anyRequest().permitAll())

//                                .requestMatchers("/authentication-login").permitAll()
//                                .requestMatchers("/csrf-token").permitAll()
//                                .requestMatchers("/customloginurl").permitAll()
//                                .requestMatchers("/api/v1/security/**").permitAll()
//                                .requestMatchers("/acik").permitAll()
//                                .requestMatchers("/register").permitAll()
//                                .requestMatchers("/admin").hasRole("ADMIN")
//                                .requestMatchers("/admin-ozel").hasRole("ADMIN")
//                                .requestMatchers("/user-ozel").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                                .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .oauth2Login(oauth->oauth
                        .successHandler(auth2LoginSuccessHandler)
                        .loginPage("/customloginurl")

                )
                .logout(logout -> logout
                        .logoutUrl("/cikis")
                        .logoutSuccessUrl("/deneme") // logout sonrası yönlendirilecek sayfa
                        .deleteCookies("JSESSIONID") // session cookie'sini sil
                        .invalidateHttpSession(true) // session'ı geçersiz kıl
                        .clearAuthentication(true) // authentication bilgisini temizle
                )
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Spesifik originleri belirtin
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",     // React development server
                "http://localhost:8080",     // Your frontend domain
                "https://yourdomain.com"     // Production domain
        ));

        // Credentials'a izin ver
        configuration.setAllowCredentials(true);

        // İzin verilen HTTP metodları
        configuration.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        // İzin verilen headerlar
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Response headerlarına izin ver
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization"
        ));

        // Preflight request cache süresi (saniye)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails uzay = User
                .builder()
                .username("igü")
                .password(passwordEncoder().encode("4267462"))
                .authorities("ROLE_USER")
                .build();

        manager.createUser(uzay);
        return manager;


    }



    @Bean
    public Car car(){
        return new Car();
    }


}
