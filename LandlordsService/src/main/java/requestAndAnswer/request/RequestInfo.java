package requestAndAnswer.request;

import requestAndAnswer.request.command.Request;

import java.io.Serializable;

public class RequestInfo implements Serializable {
    private Request request;

    public RequestInfo(Request request){
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
