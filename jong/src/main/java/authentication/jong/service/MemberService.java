package authentication.jong.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import authentication.jong.DTO.LoginDto;
import authentication.jong.DTO.MemberDto;
import authentication.jong.DTO.TokenDto;
import authentication.jong.DTO.SignUpDto;

public interface MemberService {
    public TokenDto login(LoginDto loginDto);
    public MemberDto signUp(SignUpDto signUpDto);
}