package marketplace.services;

import marketplace.domain.UserDto;
import marketplace.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface UserService {

    void saveUser(Map<String, String[]> parameterMap, UserDto userDto) throws SQLException, IOException;

    User getUser(String email) throws IOException, SQLException;

}
