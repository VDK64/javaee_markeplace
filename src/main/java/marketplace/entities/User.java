package marketplace.entities;

import lombok.Data;
import marketplace.types.Gender;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity for database store. Represents User.
 */
@Data
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;
    private Set<Role> authorities;

    public User() {
        this.authorities = new HashSet<>();
    }

}
