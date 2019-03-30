package cz.zcu.kiv.martinm.internetbankingclient.parser;

public interface Parser<T, E> {

    public E parse(T data);

}
