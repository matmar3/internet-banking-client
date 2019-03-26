package cz.zcu.kiv.martinm.internetbankingclient.client;

import java.util.Map;
import java.util.Optional;

public interface WebClient {

    public Optional<HttpResponse> doGet(String targetUrl, Map<String, String> params);

    public Optional<HttpResponse> doPost(String targetUrl, Map<String, String> data);

}
