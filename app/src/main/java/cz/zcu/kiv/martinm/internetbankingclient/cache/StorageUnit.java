package cz.zcu.kiv.martinm.internetbankingclient.cache;

/**
 * Defines unit that can be stored in cache.
 *
 * @param <V> - Value type
 * @param <T> - Timestamp type
 */
public interface StorageUnit<V, T> {

    public T getSaveTime();

    public V getStoredValue();

}
