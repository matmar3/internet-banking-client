package cz.zcu.kiv.martinm.internetbankingclient;

import android.app.ProgressDialog;

/**
 * Defines async activity, that display progress dialog when AsyncTask running.
 */
public abstract class AsyncActivity extends BaseActivity {

    private ProgressDialog progressDialog;

    private boolean destroyed = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    public void showLoadingProgressDialog() {
        this.showProgressDialog("Načítání ...");
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

}