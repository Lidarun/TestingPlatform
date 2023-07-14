package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
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

    private String key;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;
}
