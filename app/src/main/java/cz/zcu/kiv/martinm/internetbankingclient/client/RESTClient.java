package cz.zcu.kiv.martinm.internetbankingclient.client;

import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

public abstract class RESTClient implements WebClient {

    @Override
    public Optional<HttpResponse> doGet(String targetUrl, Map<String, String> params) {
        // Populate the HTTP Basic Authentitcation header with the username and password
        HttpHeaders requestHeaders = new HttpHeaders();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (params != null) map.setAll(params);

        URI buildedUrl = UriComponentsBuilder.fromHttpUrl(targetUrl)
                .queryParams(map)
                .build()
                .encode()
                .toUri();

        // create request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Make the network request
            Log.d(this.getClass().getSimpleName(), targetUrl);
            ResponseEntity<String> response = restTemplate.exchange(buildedUrl, HttpMethod.GET, request, String.class);

            return Optional.of(new DefaultHttpResponse(response.getStatusCode(), response.getBody()));
        } catch (HttpClientErrorException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return Optional.empty();
        } catch (ResourceAccessException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return Optional.empty();
        }
    }

    @Override
    public Optional<HttpResponse> doPost(String targetUrl, Map<String, String> data) {
        // Populate the HTTP Basic Authentitcation header with the username and password
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        //requestHeaders.set("Set-Cookie", "JSESSIONID=" + sessionId);

        // map data
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(data);

        // create request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Make the network request
            Log.d(this.getClass().getSimpleName(), targetUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, request, String.class);

            return Optional.of(new DefaultHttpResponse(response.getStatusCode(), response.getBody()));
        } catch (HttpClientErrorException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return Optional.empty();
        } catch (ResourceAccessException e) {
            Log.e(this.getClass().getSimpleName(), e.getLocalizedMessage(), e);

            return Optional.empty();
        }
    }

}
