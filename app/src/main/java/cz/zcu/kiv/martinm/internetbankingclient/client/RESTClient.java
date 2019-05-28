package cz.zcu.kiv.martinm.internetbankingclient.client;

import android.content.Context;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Provides generic implementation for REST client.
 */
public abstract class RESTClient implements WebClient {

    private OkHttpClient httpClient;

    /**
     * Defines cookie cache to store SESSIONID for application. Initialize HttpClient.
     *
     * @param context application context
     */
    RESTClient(Context context) {
        CookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        httpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    @Override
    public Response doGet(String targetUrl, Map<String, String> params) {
        Request request = new Request.Builder()
                .url(buildUrl(targetUrl, params))
                .build();

        try {
            return httpClient.newCall(request).execute();

        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return null;
        }
    }

    @Override
    public Response doPost(String targetUrl, Map<String, String> data) {
        Request request = new Request.Builder()
                .url(targetUrl)
                .post(buildFormBody(data))
                .build();

        try {
            return  httpClient.newCall(request).execute();

        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return null;
        }
    }

    public Response doPostJSON(String targetUrl, String json) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );
        Request request = new Request.Builder()
                .url(targetUrl)
                .post(body)
                .build();

        try {
            return  httpClient.newCall(request).execute();

        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return null;
        }
    }

    private HttpUrl buildUrl(String targetUrl, Map<String, String> params) {
        HttpUrl.Builder prototype =
                Objects.requireNonNull(HttpUrl.parse(targetUrl)).newBuilder();

        if (params != null) params.forEach(prototype::addQueryParameter);

        return prototype.build();
    }

    private RequestBody buildFormBody(Map<String, String> data) {
        FormBody.Builder prototype = new FormBody.Builder();
        if (data != null) data.forEach(prototype::add);

        return prototype.build();
    }

}
