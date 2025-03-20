package e_commecre.dto;

import java.time.LocalDateTime;
import java.util.List;

import e_commecre.entity.Order;
import e_commecre.entity.Review;
import e_commecre.entity.Role;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	long id;
	String firstName;
	String fullName;
	String address;
	String email;
	String phoneNumber;
	String createAt;
}
