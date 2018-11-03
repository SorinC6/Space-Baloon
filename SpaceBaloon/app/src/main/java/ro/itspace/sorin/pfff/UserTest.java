package ro.itspace.sorin.pfff;

public class UserTest {

    public String userName;
    public int userScore;

    public UserTest(String userName, int userScore) {
        this.userName = userName;
        this.userScore = userScore;
    }

    public UserTest(){

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
