package cz.zcu.kiv.martinm.internetbankingclient.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.zcu.kiv.martinm.internetbankingclient.R;
import cz.zcu.kiv.martinm.internetbankingclient.TransactionsActivity;
import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Account;

/**
 * Defines adapter for Account objects based on RecyclerView.
 */
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private Context context;

    private List<Account> accounts;

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountNumber;
        TextView cardNumber;
        TextView balance;

        AccountViewHolder(View itemView) {
            super(itemView);
            this.accountNumber = itemView.findViewById(R.id.account_number);
            this.cardNumber = itemView.findViewById(R.id.card_number);
            this.balance = itemView.findViewById(R.id.balance);
        }
    }

    public AccountAdapter(Context context, List<Account> accounts) {
        this.context = context.getApplicationContext();
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.account_item, parent, false);

        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TransactionsActivity.class);
            ApplicationCache.getInstance().put("selectedAccount", account);
            context.startActivity(intent);
        });
        holder.accountNumber.setText(account.getAccountNumber());
        holder.cardNumber.setText(account.getCardNumber());
        holder.balance.setText(
                String.format("%s %s", account.getBalance().toString(), account.getCurrency())
        );
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
}
