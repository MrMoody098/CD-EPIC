package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.questions.Player;
import com.cd.quizwhiz.uiframework.UIEventListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;
import com.cd.quizwhiz.userstuff.Auth;
import com.cd.quizwhiz.userstuff.User;

public class LoginPage extends UIPage<AppState> {
/*REFACTORING:
Final Keyword: Made the playerType, nextPage, and purpose fields final to ensure they are not modified after initialization.

Separation of Concerns: Extracted methods attemptAuthentication, handleSuccessfulLogin, handlePlayerTwoLogin, and handleFailedLogin to improve readability and maintainability.

Reduced Code Duplication: Reused the logic for handling successful and failed login attempts to avoid duplicating code.
*/
    private final Player playerType;
    private final UIPage<AppState> nextPage;
    private final String purpose;

    public LoginPage(Player loginType, UIPage<AppState> nextPage, String purpose) {
        super("login");
        this.playerType = loginType;
        this.nextPage = nextPage;
        this.purpose = purpose;
    }

    public LoginPage(Player loginType, UIPage<AppState> nextPage) {
        this(loginType, nextPage, "");
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        ui.getContext().setVariable("purpose", this.purpose);
        return true;
    }

    @UIEventListener(type = "click", id = "login-button")
    public void onLoginButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        // Attempt to authenticate the user
        if (attemptAuthentication(ui, username, password)) {
            handleSuccessfulLogin(ui, username);
        } else {
            handleFailedLogin(ui);
        }
    }

    private boolean attemptAuthentication(UI<AppState> ui, String username, String password) {
        return Auth.login(username, password);
    }

    private void handleSuccessfulLogin(UI<AppState> ui, String username) {
        User user = new User(username);

        switch (this.playerType) {
            case Player1:
                ui.getState().user = user;
                break;

            case Player2:
                handlePlayerTwoLogin(ui, user, username);
                break;
        }

        ui.loadPage(nextPage);
    }

    private void handlePlayerTwoLogin(UI<AppState> ui, User user, String username) {
        // Check if the same user just tried to log in twice
        if (ui.getState().user.getUsername().equals(username)) {
            ui.setElementText("error-toast", "The second player must be a different user to the first!");
            ui.setElementVisibility("error-toast", true);
        } else {
            ui.getState().multiplayerUserTwo = user;
        }
    }

    private void handleFailedLogin(UI<AppState> ui) {
        ui.setElementVisibility("error-toast", true);
    }

    @UIEventListener(type = "click", id = "signup-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new SignupPage(this.playerType, this.nextPage, this.purpose));
    }
}
