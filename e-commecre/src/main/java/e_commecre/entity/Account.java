package e_commecre.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String firstName;
	String fullName;
	String password;
	String address;
	@Column(name = "email", nullable = true)
	String email;
	@Column(name = "phone_number", nullable = true)
	String phoneNumber;
	
	@OneToOne(mappedBy = "account")
	Cart cart;
	
	@Column(nullable = false, updatable = false, insertable = false)
	LocalDateTime createAt;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name = "account_role", 
	        joinColumns = @JoinColumn(name = "account_id"), 
	        inverseJoinColumns = @JoinColumn(name = "role_id")
	    )
	private List<Role> roles;
	
	@OneToMany(mappedBy = "customerId")
	private List<Order> order;
	
	@OneToMany(mappedBy = "customerId")
	private List<Review> review;

}
