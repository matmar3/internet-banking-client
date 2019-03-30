package cz.zcu.kiv.martinm.internetbankingclient.client;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class InternetBankingRESTClient extends RESTClient {

    private static InternetBankingRESTClient instance;

    private InternetBankingRESTClient(Context context) {
        super(context);
    }

    public static InternetBankingRESTClient getInstance(Context context) {
        if (instance == null) {
            instance = new InternetBankingRESTClient(context);
        }

        return instance;
    }

    public boolean signUp(String username, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        Response response = doPost(Routes.getLogin(), credentials);

        return response != null
                && response.isSuccessful()
                && isLoggedIn(response.request().url().toString());
    }

    public String fetchResponseEntity(String url) {
        Response response = doGet(url, null);

        if (response != null) {
            ResponseBody responseBody = response.body();

            if (responseBody != null) {
                try {
                    return responseBody.string();
                } catch (IOException e) {
                    Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);
                }
            }
        }

        return null;
    }

    private boolean isLoggedIn(String url) {
        return !url.equals(Routes.getLogin());
    }

}
