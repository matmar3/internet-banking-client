package cz.zcu.kiv.martinm.internetbankingclient.client;

/**
 * Defines all possible routes to server that can be used.
 */
public class Routes {

    public static final String HOST_NAME = "https://internet-banking-app.herokuapp.com/";
    public static final String REST_API_PROFILE = "profile";
    public static final String REST_API_ACCOUNTS = "accounts";
    public static final String REST_API_TRANSACTIONS = "account/%d/transactions?page=%d";
    public static final String REST_API_TRANSACTIONS_COUNT = "account/%d/transactions/count";
    public static final String REST_API_NEW_TRANSACTION = "create-transaction";

    private static final String SIGN_IN = "login-process";
    private static final String LOGGED_IN = "ib/";
    private static final String REST_API = "rest/api/";


    public static String getLogin() {
        return HOST_NAME + SIGN_IN;
    }

    public static String getRESTApi() {
        return HOST_NAME + REST_API;
    }
}
