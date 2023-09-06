package kg.dpa.gov.evaluation.models.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.function.Predicate;

@Getter
public enum Role implements GrantedAuthority, Predicate<Role> {
    ROLE_SUPER_ADMIN("Администрация"),
    ROLE_ADMIN("Помощник"),
    ROLE_TEACHER("Учитель"),
    ROLE_USER("Пользователь");

    public final String name;
    private Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public boolean test(Role role) {
        return role.equals(ROLE_ADMIN) || role.equals(ROLE_USER);
    }
}
