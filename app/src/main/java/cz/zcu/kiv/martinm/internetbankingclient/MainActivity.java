package cz.zcu.kiv.martinm.internetbankingclient;

import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.client.InternetBankingRESTClient;
import cz.zcu.kiv.martinm.internetbankingclient.domain.User;
import cz.zcu.kiv.martinm.internetbankingclient.parser.UserJSONParser;

public class MainActivity extends AsyncActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Initiate the request to the protected service
        final Button submitButton = findViewById(R.id.btn_signup);
        submitButton.setOnClickListener(v -> new SignUpTask().execute());
    }

    private void displayResponse(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }

    private class SignUpTask extends AsyncTask<Void, Void, Boolean> {

        private String username;

        private String password;

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            EditText editText = findViewById(R.id.username);
            this.username = editText.getText().toString();

            editText = findViewById(R.id.password);
            this.password = editText.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isLoggedIn;
            InternetBankingRESTClient client = InternetBankingRESTClient.getInstance(MainActivity.this);

            isLoggedIn = client.signUp(username, password);
            if (isLoggedIn) {
                String responseEntity;

                responseEntity = client.fetchResponseEntity(getRESTApi() + REST_API_PROFILE);
                User user = new UserJSONParser().parse(responseEntity);
                ApplicationCache.getInstance().put("user", user);
            }

            return isLoggedIn;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dismissProgressDialog();
            if (!result) {
                displayResponse("Wrong username or password.");
            }
            else {
                if (ApplicationCache.getInstance().isExists("user")) {
                    Intent intent = new Intent(getApplicationContext(), AccountOverviewActivity.class);
                    startActivity(intent);
                }
            }
        }

    }

}
