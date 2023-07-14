package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.dpa.gov.evaluation.enums.Role;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Укажите полное имя")
    String fullName;

    private String username;

    @NotEmpty(message = "Обязательное поле")
    @Email(message = "Пожалуйста, введите действительный e-mail адрес")
    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> role;

    @NotEmpty(message = "Обязательное поле")
    @Size(min = 7, message = "Пароль должен содержать не менее 8-ми символов, " +
            "в том числе цифры, прописные и строчные буквы")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany
    private Set<Course> courses;
}