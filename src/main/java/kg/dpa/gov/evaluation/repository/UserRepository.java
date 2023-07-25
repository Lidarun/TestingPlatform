package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsUserByUsername(String username);
    List<User> findAllByRoleContains(Role role);

    List<User> findAllByCourses(Course course);

//    boolean existsByCourses(Course course);
}
