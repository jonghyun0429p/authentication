package authentication.jong.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
public class SignUpDto {
    private String email;
    private String contact;
    private String password;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationData;
    private String accountType;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessTime;

    @Builder
    public SignUpDto(String email, String contact, String password, LocalDateTime registrationData, String accountType, LocalDateTime lastAccessTime){
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.lastAccessTime = lastAccessTime;
        this.accountType = accountType;
        this.registrationData = registrationData;
    }

    public Member toEntity(String encodedPassword, String accountType){
        return Member.builder()
            .accountType(accountType)
            .contact(contact)
            .email(email)
            .lastAccessTime(lastAccessTime)
            .password(encodedPassword)
            .registrationData(registrationData).build();
    }
}