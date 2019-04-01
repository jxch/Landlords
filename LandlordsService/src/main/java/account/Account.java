package account;

public class Account {
    public static final int normalIntegral = 10000;

    private String username;
    private int integral;

    public void setAccount(String username, int integral){
        this.username = username;
        this.integral = integral;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}