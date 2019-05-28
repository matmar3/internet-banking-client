package cz.zcu.kiv.martinm.internetbankingclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.zcu.kiv.martinm.internetbankingclient.client.InternetBankingRESTClient;
import cz.zcu.kiv.martinm.internetbankingclient.domain.dto.TransactionDto;

/**
 * Defines form for performing transactions.
 */
public class TransactionFormActivity extends AsyncActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_form);
        setTitle("Nová transakce");

        final List<String> list = getIntent().getStringArrayListExtra("accounts");
        final Spinner sp = findViewById(R.id.newSender);
        ArrayAdapter<String> adp= new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    public void handlePerformTransaction(View view) {
        TransactionDto transaction = new TransactionDto();

        Spinner sender = findViewById(R.id.newSender);
        transaction.setSenderAccountNumber(sender.getSelectedItem().toString());

        EditText receiver = findViewById(R.id.newReceiver);
        transaction.setReceiverAccountNumber(receiver.getText().toString());

        EditText amount = findViewById(R.id.newAmount);
        transaction.setSentAmount(new BigDecimal(amount.getText().toString()));

        EditText date = findViewById(R.id.newDate);
        Date dateObject;
        String isoDate;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", new Locale("cs", "CZ"));
            String dob_var = date.getText().toString();
            dateObject = df.parse(dob_var);

            SimpleDateFormat dateFormatISO8601 = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    new Locale("cs", "CZ")
            );
            isoDate = dateFormatISO8601.format(dateObject);
        } catch (java.text.ParseException e) {
           isoDate = null;
        }
        transaction.setDueDate(isoDate);

        EditText constant = findViewById(R.id.newConstant);
        transaction.setConstantSymbol(constant.getText().toString());

        EditText variable = findViewById(R.id.newVariable);
        transaction.setVariableSymbol(variable.getText().toString());

        EditText specific = findViewById(R.id.newSpecific);
        transaction.setSpecificSymbol(specific.getText().toString());

        EditText message = findViewById(R.id.newMessage);
        transaction.setMessage(message.getText().toString());

        new PerformTransaction().execute(transaction);
    }

    /**
     * Send transaction data to server for processing. Returns TRUE if transaction was performed or
     * FALSE when transaction is no valid.
     */
    private class PerformTransaction extends AsyncTask<TransactionDto, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Boolean doInBackground(TransactionDto... params) {
            TransactionFormActivity ctx = TransactionFormActivity.this;
            InternetBankingRESTClient client = InternetBankingRESTClient.getInstance(ctx);

            Gson gson = new Gson();
            String responseEntity = client.performTransaction(4, gson.toJson(params[0]));

            return gson.fromJson(responseEntity, Boolean.class);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dismissProgressDialog();
            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
            if (result) {
                TransactionFormActivity.this.finish();
            }
        }

    }

}
