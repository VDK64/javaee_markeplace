package marketplace.validation;

import marketplace.domain.UserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface UserValidator {

    void validate(Map<String, String[]> parameterMap, UserDto userDTO) throws IOException, SQLException;

}
