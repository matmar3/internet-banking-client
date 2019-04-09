package cz.zcu.kiv.martinm.internetbankingclient;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.client.InternetBankingRESTClient;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Account;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Transaction;
import cz.zcu.kiv.martinm.internetbankingclient.domain.adapter.TransactionAdapter;

import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.REST_API_TRANSACTIONS;
import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.REST_API_TRANSACTIONS_COUNT;
import static cz.zcu.kiv.martinm.internetbankingclient.client.Routes.getRESTApi;

public class TransactionsActivity extends AsyncActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Account account;

    boolean isLoading = false;
    private int pageCounter = 0;
    Long totalCountOfItems = 0L;

    List<Transaction> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        recyclerView = findViewById(R.id.transaction_recycler_view);
        account = (Account) ApplicationCache.getInstance().get("selectedAccount");
        setTitle("Historie transakcí");

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new FetchCountOfTransactions().execute();
        new FetchTransactions().execute();
        initScrollListener();
    }

    private void setAccounts(List<Transaction> transactions) {
        if (transactions == null) {
            listItems = new ArrayList<>();
        }
        else {
            listItems = new ArrayList<>(transactions);
        }

        adapter = new TransactionAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
    }

    private class FetchTransactions extends AsyncTask<Void, Void, List<Transaction>> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected List<Transaction> doInBackground(Void... params) {
            return getTransactions(pageCounter++);
        }

        @Override
        protected void onPostExecute(List<Transaction> result) {
            dismissProgressDialog();
            setAccounts(result);
        }

    }

    private class FetchCountOfTransactions extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Long doInBackground(Void... params) {
            InternetBankingRESTClient client = InternetBankingRESTClient.getInstance(TransactionsActivity.this);

            String URL = String.format(getRESTApi() + REST_API_TRANSACTIONS_COUNT, account.getId());
            String responseEntity = client.fetchResponseEntity(URL);

            return new Gson().fromJson(responseEntity, Long.class);
        }

        @Override
        protected void onPostExecute(Long result) {
            dismissProgressDialog();
            totalCountOfItems = result;
        }

    }

    private List<Transaction> getTransactions(int page) {
        InternetBankingRESTClient client = InternetBankingRESTClient.getInstance(TransactionsActivity.this);

        String URL = String.format(getRESTApi() + REST_API_TRANSACTIONS, account.getId(), page);
        String responseEntity = client.fetchResponseEntity(URL);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
        Type listType = new TypeToken<List<Transaction>>() {}.getType();

        return gson.fromJson(responseEntity, listType);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listItems.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });

    }

    private void loadMore() {
        Handler handler = new Handler();
        handler.post(() -> {
            List<Transaction> transactions = getTransactions(pageCounter);
            if (transactions.size() + listItems.size() > totalCountOfItems) {
                isLoading = false;
                return;
            }

            listItems.addAll(transactions);
            adapter.notifyDataSetChanged();
            isLoading = false;
        });
    }
}
