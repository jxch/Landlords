package requestAndAnswer.request;

import requestAndAnswer.request.command.Request;

public class RegisteredRequestInfo extends LoginRequestInfo {
    private String email;

    public RegisteredRequestInfo() {
        setRequest(Request.registered);
    }

    public void setRegisteredInfo(String username, String password, String email){
        setLoginInfo(username, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
