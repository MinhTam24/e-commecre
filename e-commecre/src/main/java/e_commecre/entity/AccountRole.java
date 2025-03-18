package e_commecre.entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class AccountRole {
	@Id

	long accountId;
	
	long roleId;
	
}
