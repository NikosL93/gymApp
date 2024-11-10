package com.gymApp.security;

import com.gymApp.model.Admin;
import com.gymApp.model.Member;
import com.gymApp.model.Role;
import com.gymApp.model.User;
import com.gymApp.repositories.AdminRepository;
import com.gymApp.repositories.MemberRepository;
import com.gymApp.repositories.UserRepository;
import com.gymApp.security.jwt.AuthEntryPointJwt;
import com.gymApp.security.jwt.AuthTokenFilter;
import com.gymApp.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/public/**").hasAnyRole("ADMIN","MEMBER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/auth/signin").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                );
        http.headers(headers ->headers.frameOptions(frameOptions ->frameOptions.sameOrigin()));
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**"));
    }
    //Δημιουργώ 2 αρχικούς users
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Create users if not already present

            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("123"));
                user1.setRole(Role.ROLE_MEMBER);
                //userRepository.save(user1); Δεν κάνω save γιατί αλλιώς πετάει σφάλμα detached entity to persist, μονό απ το owner entity κάνω save και η άλλη entity κάνει save αυτόματα.
                if (!memberRepository.existsByUserName("member1")) {
                    Member member1 = new Member("member1", "member1@example.com", passwordEncoder.encode("123"));
                    member1.setUser(user1);
                    member1.setRole(Role.ROLE_MEMBER);
                    memberRepository.save(member1);
                }
            }
            if (!userRepository.existsByUserName("user2")) {
                User user2 = new User("user2", "user2@example.com", passwordEncoder.encode("123"));
                user2.setRole(Role.ROLE_ADMIN);
                //userRepository.save(user2);
                if (!adminRepository.existsByUserName("admin1")) {
                    Admin admin1 = new Admin("admin1", "admin1@example.com", passwordEncoder.encode("123"));
                    admin1.setUser(user2);
                    admin1.setRole(Role.ROLE_ADMIN);
                    adminRepository.save(admin1);
                }
            }
        };
    }
}