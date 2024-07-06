package authentication.jong.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "email")
public class Member implements UserDetails {
    @Id
    @Column(name = "member_email", unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String contact;

    @Column(name = "member_password", nullable = false)
    private String password;
    
    @Column(name = "registration_data", nullable = false)
    private LocalDateTime registrationData;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "last_access_time", nullable = false)
    private LocalDateTime lastAccessTime;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority(this.accountType));
        return authority;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Builder
    public Member(String email, String contact, String password, LocalDateTime registrationData, String accountType, LocalDateTime lastAccessTime){
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.lastAccessTime = lastAccessTime;
        this.accountType = accountType;
        this.registrationData = registrationData;
    }

}
