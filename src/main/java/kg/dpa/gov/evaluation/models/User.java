package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.dpa.gov.evaluation.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotEmpty(message = "Обязательное поле")
    String username;

    @NotEmpty(message = "Обязательное поле")
    @Email(message = "Пожалуйста, введите действительный e-mail адрес")
    String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    Set<Role> role;

    @NotEmpty(message = "Обязательное поле")
    @Size(min = 7, message = "Пароль должен содержать не менее 8-ми символов, " +
            "в том числе цифры, прописные и строчные буквы")
    String password;

    @Transient
    String confirmPassword;
}
