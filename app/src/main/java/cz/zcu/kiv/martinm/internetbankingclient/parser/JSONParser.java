package cz.zcu.kiv.martinm.internetbankingclient.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

import cz.zcu.kiv.martinm.internetbankingclient.domain.DataTransferObject;

/**
 * Defines generic JSON parser.
 * @param <E> - type of entity to get from JSON
 */
public abstract class JSONParser<E extends DataTransferObject> implements Parser<String, E> {

    @SuppressWarnings("unchecked")
    @Override
    public E parse(String data) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
        return gson.fromJson(data, (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
