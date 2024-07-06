package authentication.jong.DTO;

import java.util.Date;

import authentication.jong.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class MemberDto {
    private String email;
    private String contact;
    private String password;
    private LocalDateTime registrationData;
    private LocalDateTime lastAccessTime;

    @Builder
    public MemberDto(String email, String contact, String password, LocalDateTime registrationData, LocalDateTime lastAccessTime){
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.lastAccessTime = lastAccessTime;
        this.registrationData = registrationData;
    }

    static public MemberDto toDto(Member member){
        return MemberDto.builder()
            .contact(member.getContact())
            .email(member.getEmail())
            .lastAccessTime(member.getLastAccessTime())
            .password(member.getPassword())
            .registrationData(member.getRegistrationData()).build();
    }

    public Member toEntity(){
        return Member.builder()
        .contact(contact)
        .email(email)
        .lastAccessTime(lastAccessTime)
        .password(password)
        .registrationData(registrationData).build();
    }

}
