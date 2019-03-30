package cz.zcu.kiv.martinm.internetbankingclient.parser;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import cz.zcu.kiv.martinm.internetbankingclient.domain.DataTransferObject;

public abstract class JSONParser<E extends DataTransferObject> implements Parser<String, E> {

    @SuppressWarnings("unchecked")
    @Override
    public E parse(String data) {
        Gson gson =  new Gson();
        return gson.fromJson(data, (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
