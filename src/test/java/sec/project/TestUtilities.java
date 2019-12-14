package sec.project;

import sec.project.domain.Account;

public class TestUtilities {
    public static Account createAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return account;
    }
}
