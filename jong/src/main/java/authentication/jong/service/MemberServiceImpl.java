package authentication.jong.service;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import authentication.jong.SecurityConfig;
import authentication.jong.DTO.LoginDto;
import authentication.jong.DTO.MemberDto;
import authentication.jong.DTO.SignUpDto;
import authentication.jong.DTO.TokenDto;
import authentication.jong.Entity.Member;
import authentication.jong.Jwt.JwtTokenProvider;
import authentication.jong.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public TokenDto login(LoginDto loginDto) {
        //email이랑 password를 기반으로 인증 객체 생성, 지금은 authentication = false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        //검증 진행. loadUserByUsername 실행됨.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //인증정보로 JWT 생성
        TokenDto tokenDTO = jwtTokenProvider.generateToken(authentication);

        return tokenDTO;
    }

    @Transactional
    @Override
    public MemberDto signUp(SignUpDto signUpDto) {
        //이름 중복(아마 다른 메소드를 만들어서 사용할 듯)
        // if(memberRepository.findByEmail(signUpDto.getEmail()).isPresent()){
        //     throw new IllegalArgumentException("이미 사용 중인 사용자 이메일입니다.");
        // }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = signUpDto.toEntity(encodedPassword, signUpDto.getAccountType());
        return MemberDto.toDto(memberRepository.save(member));
    }

    

    
}
