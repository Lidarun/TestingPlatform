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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Укажите полное имя")
    private String fullName;

    private String username;

    @NotEmpty(message = "Обязательное поле")
    @Email(message = "Пожалуйста, введите действительный e-mail адрес")
    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_users_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> role;

    @NotEmpty(message = "Обязательное поле")
    @Size(min = 7, message = "Пароль должен содержать не менее 8-ми символов, " +
            "в том числе цифры, прописные и строчные буквы")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "tb_users_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.getUsers().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getUsers().remove(this);
    }
}