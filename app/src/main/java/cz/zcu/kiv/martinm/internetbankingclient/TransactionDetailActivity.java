package cz.zcu.kiv.martinm.internetbankingclient;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Account;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Transaction;

public class TransactionDetailActivity extends BaseActivity {

    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm";

    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        transaction = (Transaction) ApplicationCache.getInstance().get("selectedTransaction");
        setTitle("Detail transakce");

        Account userAcc = (Account) ApplicationCache.getInstance().get("selectedAccount");
        Account receiver = transaction.getReceiver();

        TextView amount = findViewById(R.id.transaction_detail_amount);
        TextView date = findViewById(R.id.transaction_detail_date);
        TextView accFrom = findViewById(R.id.transaction_detail_acc_from);
        TextView accTo = findViewById(R.id.transaction_detail_acc_to);
        TextView constantSymbol = findViewById(R.id.transaction_detail_constSym);
        TextView variableSymbol = findViewById(R.id.transaction_detail_varSym);
        TextView specificSymbol = findViewById(R.id.transaction_detail_specificSym);
        TextView message = findViewById(R.id.transaction_detail_message);

        if (receiver != null && receiver.getId().equals(userAcc.getId())) {
            String amountFormat = String.format("+ %s", transaction.getReceivedAmount());
            amount.setText(amountFormat);
            amount.setTextColor(Color.GREEN);
        }
        else {
            String amountFormat = String.format("- %s", transaction.getSentAmount());
            amount.setText(amountFormat);
            amount.setTextColor(Color.RED);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        date.setText(dateFormat.format(transaction.getCreatedDate()));
        accFrom.setText(transaction.getSenderAccountNumber());
        accTo.setText(transaction.getReceiverAccountNumber());
        constantSymbol.setText(transaction.getConstantSymbol());
        variableSymbol.setText(transaction.getVariableSymbol());
        specificSymbol.setText(transaction.getSpecificSymbol());
        message.setText(transaction.getMessage());
    }
}
