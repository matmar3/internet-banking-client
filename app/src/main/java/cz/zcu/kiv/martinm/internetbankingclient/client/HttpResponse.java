package cz.zcu.kiv.martinm.internetbankingclient.client;

import org.springframework.http.HttpStatus;

public interface HttpResponse {

    public HttpStatus getStatusCode();

    public int getRawStatusCode();

    public String getStatusText();

    public String getBody();

}
