package kg.dpa.gov.evaluation.mappers.impl;


import kg.dpa.gov.evaluation.mappers.EntityMapper;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.models.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements EntityMapper<User, UserDto> {

    @Override
    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getFullName(), user.getEmail(), "0/0");
    }

}
