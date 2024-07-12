package authentication.jong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import authentication.jong.Jwt.JwtAuthenticationFilter;
import authentication.jong.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        //REST API라서 basic auth, csrf 보안 사용 X
        .httpBasic().disable()
        .csrf().disable()
        //세션을 사용하지 않음
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
        //로그인은 모두 허가
        .requestMatchers("/member/login").permitAll()
        //test API는 로그인한 사람만 허가
        .requestMatchers("/member/test").hasAnyRole("0","1","2")
        .requestMatchers("/member/signup").permitAll()
        //나머지 모두는 로그인해야 가능
        .anyRequest().authenticated()
        .and()
        //JWT필터를 User~Filter전에 실행한다.
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
}
