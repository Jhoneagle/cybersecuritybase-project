package sec.project.domain.models;

import sec.project.validators.FieldMatch;
import sec.project.validators.Username;

import javax.validation.constraints.NotEmpty;

/**
 * Validation object for registration form.
 *
 * @see FieldMatch
 * @see Username
 */
@FieldMatch(first = "password", second = "passwordAgain", message = "The password fields must match!") // Checks if both fields match perfectly.
public class UserValidator {
    // Custom username check.
    @Username
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordAgain;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public UserValidator(String username, String password, String passwordAgain, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserValidator() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}