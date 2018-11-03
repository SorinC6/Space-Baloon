package ro.itspace.sorin.pfff;

public class User {

    public String userID;
    public String userName;
    public int userScore;

    public User() {
    }

    public User(String userID, String userName, int userScore) {
        this.userID = userID;
        this.userName = userName;
        this.userScore = userScore;
    }

    public User(int userScore) {
        this.userScore = userScore;
    }

    public User(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public User(String userName, int userScore) {
        this.userName = userName;
        this.userScore = userScore;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}
