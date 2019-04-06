package cz.zcu.kiv.martinm.internetbankingclient;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.cache.StorageUnit;
import cz.zcu.kiv.martinm.internetbankingclient.client.InternetBankingRESTClient;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Account;
import cz.zcu.kiv.martinm.internetbankingclient.domain.User;
import cz.zcu.kiv.martinm.internetbankingclient.domain.adapter.AccountAdapter;

import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.REST_API_ACCOUNTS;
import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.getRESTApi;

public class AccountOverviewActivity extends AsyncActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Account> listItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);
        recyclerView = findViewById(R.id.account_recycler_view);

        User user = (User) ApplicationCache.getInstance().get("user");
        getSupportActionBar().setTitle(user.getFirstName() + " " + user.getLastName());

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // get data
        new FetchAccounts().execute();
    }

    private void setAccounts(List<Account> accounts) {
        if (accounts == null) {
            listItems = new ArrayList<>();
        }
        else {
            listItems = new ArrayList<>(accounts);
        }

        // specify an adapter (see also next example)
        adapter = new AccountAdapter(listItems);
        recyclerView.setAdapter(adapter);
    }

    private class FetchAccounts extends AsyncTask<Void, Void, List<Account>> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected List<Account> doInBackground(Void... params) {
            InternetBankingRESTClient client = InternetBankingRESTClient.getInstance(AccountOverviewActivity.this);

            String responseEntity = client.fetchResponseEntity(getRESTApi() + REST_API_ACCOUNTS);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Account>>() {}.getType();

            return gson.fromJson(responseEntity, listType);
        }

        @Override
        protected void onPostExecute(List<Account> result) {
            dismissProgressDialog();
            setAccounts(result);
        }

    }

}