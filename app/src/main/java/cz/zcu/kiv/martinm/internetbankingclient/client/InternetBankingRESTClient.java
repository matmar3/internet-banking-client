package cz.zcu.kiv.martinm.internetbankingclient.client;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InternetBankingRESTClient extends RESTClient {

    public static final String HOST_NAME = "https://internet-banking-app.herokuapp.com";
    public static final String SIGN_IN = "/login-process";

    private static InternetBankingRESTClient instance;

    private InternetBankingRESTClient() {

    }

    public static InternetBankingRESTClient getInstance() {
        if (instance == null) {
            instance = new InternetBankingRESTClient();
        }

        return instance;
    }

    public boolean signUp(String username, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        Optional<HttpResponse> response = doPost(HOST_NAME + SIGN_IN, credentials);
        if (response.isPresent()) {
            HttpStatus status = response.get().getStatusCode();
            return status.is2xxSuccessful() || status.is3xxRedirection();
        }
        return false;
    }

}
