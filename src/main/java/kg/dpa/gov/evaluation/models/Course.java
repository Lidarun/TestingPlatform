package kg.dpa.gov.evaluation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    List<Module> modules;

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

    public void addModule(Module module) {
        modules.add(module);
        module.setCourse(this);
    }

}
