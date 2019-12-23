package sec.project.domain.models;

import sec.project.validators.FieldMatch;

import javax.validation.constraints.NotEmpty;

@FieldMatch(first = "password", second = "passwordAgain", message = "The password fields must match!") // Checks if both fields match perfectly.
public class UpdatePasswordValidator {
    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordAgain;

    public UpdatePasswordValidator(String password, String passwordAgain) {
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

    public UpdatePasswordValidator() {
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
}
