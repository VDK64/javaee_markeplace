package marketplace.domain;

import lombok.Data;

/**
 * It's a data transfer object class to deliver data to frontend.
 * Uses to fill input tags in register page.
 */
@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String maleGenderChecked;
    private String femaleGenderChecked;
    private String email;

}
