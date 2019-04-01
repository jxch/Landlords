package requestAndAnswer.request;

import requestAndAnswer.request.command.Request;

public class ForgetPasswordRequestInfo extends RequestInfo {
    private String username;

    public ForgetPasswordRequestInfo() {
        super(Request.forgetPassword);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
