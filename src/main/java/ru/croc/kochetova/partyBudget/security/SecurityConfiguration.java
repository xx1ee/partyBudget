package ru.croc.kochetova.partyBudget.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.croc.kochetova.partyBudget.security.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers( "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registration").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                        .requestMatchers(HttpMethod.GET, "/membershipApplication").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/membershipApplication").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/user").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/donate").hasAnyAuthority("PARTY_MEMBER", "USER")
                        .requestMatchers(HttpMethod.POST, "/donate").hasAnyAuthority("PARTY_MEMBER", "USER")
                        .requestMatchers(HttpMethod.GET, "/member").hasAuthority("PARTY_MEMBER")
                        .requestMatchers(HttpMethod.POST, "/receivingMoneyApplication").hasAuthority("PARTY_MEMBER")
                        .requestMatchers(HttpMethod.GET, "/receivingMoneyApplication").hasAuthority("PARTY_MEMBER")
                        .requestMatchers(HttpMethod.GET, "/admin").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/receivingMoneyApplicationRequests").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/updateStatusReceivingMoney").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/findExpenses").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/findExpenses").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/findIncomes").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/findIncomes").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/exportIncomes").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/exportExpenses").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/requests").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/members").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/deleteMember").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/requests").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/updateStatus").hasAuthority("ADMIN")
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer -> AbstractAuthenticationFilterConfigurer.successHandler(new AuthenticationSuccessHandler()).permitAll());

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
}
