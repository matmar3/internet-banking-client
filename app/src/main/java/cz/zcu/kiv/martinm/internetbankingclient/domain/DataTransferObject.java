package cz.zcu.kiv.martinm.internetbankingclient.domain;

import java.io.Serializable;

/**
 * Defines generic application object
 * @param <PK> Type of object key
 */
public interface DataTransferObject<PK extends Serializable> {

    PK getId();

}