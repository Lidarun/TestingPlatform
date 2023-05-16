package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kg.dpa.gov.evaluation.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    @NotEmpty(message = "Обязательное поле")
    String username;

    @Email
    @Column(unique = true)
    @NotEmpty(message = "Обязательное поле")
    String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    Set<Role> role;

    @NotEmpty(message = "Обязательное поле")
    String password;

    @Transient
    String confirmPassword;
}
