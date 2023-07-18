package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String pathImage;
    private String name;
    private boolean state = false;
    private boolean access = false;
    private String key;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "tb_users_courses",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", pathImage='" + pathImage + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", access=" + access +
                ", key='" + key + '\'' +
                '}';
    }

    public void addUser(User user) {
        users.add(user);
        user.getCourses().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getCourses().remove(this);
    }
}
