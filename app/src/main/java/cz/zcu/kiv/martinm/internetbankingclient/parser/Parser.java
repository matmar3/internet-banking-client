package cz.zcu.kiv.martinm.internetbankingclient.parser;

/**
 * Defines generic parser.
 *
 * @param <T> - type of data to parse
 * @param <E> - type of entity to get
 */
public interface Parser<T, E> {

    public E parse(T data);

}
