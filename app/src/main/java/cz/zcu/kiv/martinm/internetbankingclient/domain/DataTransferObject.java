package cz.zcu.kiv.martinm.internetbankingclient.domain;

import java.io.Serializable;

public interface DataTransferObject<PK extends Serializable> {

    PK getId();

}