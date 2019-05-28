package cz.zcu.kiv.martinm.internetbankingclient.cache;

/**
 * Defines methods to store objects in cache with timestamp.
 *
 * @param <K> - key Type
 * @param <V> - Value Type
 */
public interface Cache<K, V> {

    public boolean isExists(K key);

    public V get(K key);

    public boolean isUpToDate(K key);

    public void put(K key, V value);

}
