package authentication.jong.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;

    @Builder
    public LoginDto (String email, String password){
        this.email = email;
        this.password = password;
    }
}
