package kr.ac.duksung.account;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {

    private String UserIdToken;
    private String UserEmail;
    private String UserPassword;
    private String UserAge;
    private String UserName;

    public UserAccount() { }


    public String getUserIdToken() { return UserIdToken; }
    public void setUserIdToken(String userIdToken) {
        this.UserIdToken = userIdToken;
    }

    public String getUserEmail() { return UserEmail; }
    public void setUserEmail(String userId) {
        this.UserEmail = userId;
    }

    public String getUserPassword() { return UserPassword; }
    public void setUserPassword(String userPassword) {
        this.UserPassword = userPassword;
    }

    public String getUserAge() { return UserAge; }
    public void setUserAge(String userAge) {
        this.UserAge = userAge;
    }

    public String getUserName() { return UserName; }
    public void setUserName(String userName) {
        this.UserName = userName;
    }
}

