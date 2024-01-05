//package ma.youcode.myrhbackendapi.security.config;
//
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
//import lombok.RequiredArgsConstructor;
//import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
//import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
//import ma.youcode.myrhbackendapi.utils.Env;
//import org.jboss.logging.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.util.List;
//import java.util.logging.Level;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final RecruiterRepository recruiterRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return (username) -> (UserDetails) recruiterRepository.findRecruiterByEmail(username)
//                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with email: " + username));
//    }
////    @Bean
////    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
////        PasswordEncoder passwordEncoder = passwordEncoder();
////        return new InMemoryUserDetailsManager(
////                User.withUsername("mohamed")
////                        .password(passwordEncoder.encode("mohamed1234"))
////                        .authorities("USER")
////                        .build(),
////                User.withUsername("ahmed")
////                        .password(passwordEncoder.encode("ahmed1234"))
////                        .authorities("USER", "ADMIN")
////                        .build()
////        );
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .securityMatchers(matchers -> matchers.requestMatchers("/api/v1/**"))
//                .authorizeHttpRequests((authorizedRequests) -> authorizedRequests.requestMatchers("/api/v1/auth/login")
//                        .permitAll()
//                        .requestMatchers("/api/v1/auth/**")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationManager(authenticationManager())
//                .formLogin(AbstractHttpConfigurer::disable)
//                // .httpBasic(Customizer.withDefaults()) //!: HttpBasic requires to send the user and password with each request
//                .oauth2ResourceServer(OAuth2Configurer -> OAuth2Configurer.jwt(Customizer.withDefaults()))
//                .build();
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder() {
//        return new NimbusJwtEncoder(new ImmutableSecret<>(Env.get("JWT_SECRET_KEY").getBytes()));
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec spec = new SecretKeySpec(Env.get("JWT_SECRET_KEY").getBytes(), "RSA");
//        return NimbusJwtDecoder.withSecretKey(spec).macAlgorithm(MacAlgorithm.HS512).build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//        return new ProviderManager(daoAuthenticationProvider);
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//}
