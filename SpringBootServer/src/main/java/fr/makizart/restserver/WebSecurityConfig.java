package fr.makizart.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Updated CORS configuration usage
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                      //  .requestMatchers("/login", "/api/login").permitAll() // Autoriser l'accès public aux endpoints de login
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/#/login")
                        .defaultSuccessUrl("/#/admin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/#/login")
                        .permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("MyApp"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS configuration bean
   /* @Bean
    public CorsConfigurationSource corsConfigurationSource() throws UnknownHostException {
        // Fetch IP address of the host
        String ipAddress = InetAddress.getLocalHost().getHostAddress();

        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(Arrays.asList("http://172.18.0.1:4200", "http://172.18.0.1:8080")); // Frontend URLs
        configuration.setAllowedOrigins(Arrays.asList("http://" + ipAddress + ":4200", "http://" + ipAddress + ":8080")); // Frontend URLs
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // Allows credentials like cookies and headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        /*try {
            // Fetch the local IP address
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            configuration.setAllowedOrigins(Arrays.asList(
                    "http://" + ipAddress + ":4200",
                    "http://" + ipAddress + ":8080"
            )); // Autoriser les URLs frontend
        } catch (UnknownHostException e) {
            // Fallback to localhost if the local IP cannot be retrieved
            configuration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:4200",
                    "http://localhost:8080"
            ));
        }
        */

        // Configuration des origines autorisées
        configuration.setAllowedOrigins(Arrays.asList(
               // "http://localhost:4200",        // Localhost pour Angular
               // "http://172.18.0.1:4200",      // IP de votre front-end Angular
                "http://172.18.0.1:8080"       // IP de votre back-end Spring Boot
        ));


        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // Autorise les cookies et les headers comme les tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}