package cz.zcu.kiv.martinm.internetbankingclient.cache;

/**
 * Defines default storage unit for cache that use Long as timestamp.
 *
 * @param <V> - Value type
 */
public class ApplicationStorageUnit<V> implements StorageUnit<V, Long> {

    private V value;

    private Long saveTime;

    public ApplicationStorageUnit(V value) {
        this.value = value;
        this.saveTime = System.currentTimeMillis();
    }

    @Override
    public Long getSaveTime() {
        return saveTime;
    }

    @Override
    public V getStoredValue() {
        return value;
    }

}
