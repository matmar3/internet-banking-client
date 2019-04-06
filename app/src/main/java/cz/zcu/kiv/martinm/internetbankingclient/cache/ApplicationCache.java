package cz.zcu.kiv.martinm.internetbankingclient.cache;

import java.util.HashMap;

import cz.zcu.kiv.martinm.internetbankingclient.domain.DataTransferObject;

public class ApplicationCache implements Cache<String, DataTransferObject> {

    private HashMap<String, ApplicationStorageUnit<DataTransferObject>> storage;

    private static ApplicationCache instance;

    private static final Long REFRESH_TIME = 1000 * 60 * 10L;

    private ApplicationCache() {
        storage = new HashMap<>();
    }

    public static ApplicationCache getInstance() {
        if (instance == null) {
            instance = new ApplicationCache();
        }

        return instance;
    }

    @Override
    public boolean isExists(String key) {
        return storage.containsKey(key);
    }

    @Override
    public DataTransferObject get(String key) {
        return storage.get(key).getStoredValue();
    }

    @Override
    public boolean isUpToDate(String key) {
        if (!storage.containsKey(key)) {
            return false;
        }

        Long currentTime = System.currentTimeMillis();
        Long diff = currentTime - storage.get(key).getSaveTime();

        return diff < REFRESH_TIME;
    }

    @Override
    public void put(String key, DataTransferObject value) {
        if (storage.containsKey(key)) {
            storage.replace(key, new ApplicationStorageUnit<>(value));
        } else {
            storage.put(key, new ApplicationStorageUnit<>(value));
        }
    }

}
