package e_commecre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	long id;
	String firstName;
	String fullName;
	String password;
    String emailOrPhone;
	String createAt;
}
