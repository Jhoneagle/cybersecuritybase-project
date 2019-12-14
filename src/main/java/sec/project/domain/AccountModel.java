package sec.project.domain;

import org.hibernate.validator.constraints.NotEmpty;
import sec.project.validators.*;

/**
 * Validation object for registration form.
 *
 * @see FieldMatch
 * @see Username
 * @see Password
 */
@FieldMatch(first = "password", second = "passwordAgain", message = "The password fields must match!") // Checks if both fields match perfectly.
public class AccountModel {
    // Custom username check.
    @NotEmpty
    @Username
    private String username;

    // Custom password check.
    @NotEmpty
    @Password
    private String password;

    @NotEmpty
    private String passwordAgain;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public AccountModel(String username, String password, String passwordAgain, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AccountModel() {
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