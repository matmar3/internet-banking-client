package cz.zcu.kiv.martinm.internetbankingclient.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cz.zcu.kiv.martinm.internetbankingclient.R;
import cz.zcu.kiv.martinm.internetbankingclient.TransactionDetailActivity;
import cz.zcu.kiv.martinm.internetbankingclient.cache.ApplicationCache;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Account;
import cz.zcu.kiv.martinm.internetbankingclient.domain.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private final int VIEW_TYPE_LOADING = 1;

    private Context context;

    private List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.transaction_item, parent, false);

            return new TransactionViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TransactionViewHolder) {
            populateItemRows((TransactionViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    @Override
    public int getItemViewType(int position) {
        return transactions.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        final String DATE_PATTERN = "dd.MM.yyyy HH:mm";

        TextView transactionLabel;
        TextView transactionDate;
        TextView transactionAccLabel;
        TextView transaction;
        TextView amount;

        TransactionViewHolder(View itemView) {
            super(itemView);
            this.transactionLabel = itemView.findViewById(R.id.transaction_label);
            this.transactionDate = itemView.findViewById(R.id.transaction_date);
            this.transactionAccLabel = itemView.findViewById(R.id.transaction_acc_label);
            this.transaction = itemView.findViewById(R.id.transaction);
            this.amount = itemView.findViewById(R.id.amount);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        Account userAcc = (Account) ApplicationCache.getInstance().get("selectedAccount");
        Account receiver = transaction.getReceiver();

        if (receiver != null && receiver.getId().equals(userAcc.getId())) {
            String amount = String.format("+ %s", transaction.getReceivedAmount());
            holder.transactionLabel.setText(R.string.wor_item_transaction_label_posVal);
            holder.transactionAccLabel.setText(R.string.row_item_transaction_acc_from);
            holder.transaction.setText(transaction.getSenderAccountNumber());
            holder.amount.setText(amount);
            holder.amount.setTextColor(Color.GREEN);
        }
        else {
            String amount = String.format("- %s", transaction.getSentAmount());
            holder.transactionLabel.setText(R.string.wor_item_transaction_label_consVal);
            holder.transactionAccLabel.setText(R.string.row_item_transaction_acc_to);
            holder.transaction.setText( transaction.getReceiverAccountNumber());
            holder.amount.setText(amount);
            holder.amount.setTextColor(Color.RED);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(holder.DATE_PATTERN);
        holder.transactionDate.setText(dateFormat.format(transaction.getCreatedDate()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TransactionDetailActivity.class);
            ApplicationCache.getInstance().put("selectedTransaction", transaction);
            context.startActivity(intent);
        });
    }
}