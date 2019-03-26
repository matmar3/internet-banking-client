package cz.zcu.kiv.martinm.internetbankingclient.client;

import org.springframework.http.HttpStatus;

public class DefaultHttpResponse implements HttpResponse {

    private HttpStatus status;

    private String body;

    public DefaultHttpResponse(HttpStatus status, String body) {
        this.status = status;
        this.body = body;
    }

    @Override
    public HttpStatus getStatusCode() {
        return status;
    }

    @Override
    public int getRawStatusCode() {
        return status.value();
    }

    @Override
    public String getStatusText() {
        return status.toString();
    }

    @Override
    public String getBody() {
        return body;
    }
}