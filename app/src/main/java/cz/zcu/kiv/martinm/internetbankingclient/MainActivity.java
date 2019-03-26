package cz.zcu.kiv.martinm.internetbankingclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.zcu.kiv.martinm.internetbankingclient.client.InternetBankingRESTClient;

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

    private void displayResponse(Boolean response) {
        Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show();
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
            return InternetBankingRESTClient.getInstance().signUp(username, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dismissProgressDialog();
            if (result != null) displayResponse(result);
        }

    }

}
