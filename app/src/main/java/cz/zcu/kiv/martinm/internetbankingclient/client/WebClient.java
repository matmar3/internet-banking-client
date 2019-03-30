package cz.zcu.kiv.martinm.internetbankingclient.client;

import java.util.Map;

import okhttp3.Response;

public interface WebClient {

    public Response doGet(String targetUrl, Map<String, String> params);

    public Response doPost(String targetUrl, Map<String, String> data);

}
