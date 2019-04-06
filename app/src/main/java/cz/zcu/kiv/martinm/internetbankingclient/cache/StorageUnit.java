package cz.zcu.kiv.martinm.internetbankingclient.cache;

public interface StorageUnit<V, T> {

    public T getSaveTime();

    public V getStoredValue();

}
