package com.freephone.justfofun.freephone.restful.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by imorn on 16/1/5.
 */
public class EnumSerializer<T extends Enum<T> & GsonEnum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {

    private Class<T> mClazz;

    public EnumSerializer(Class<T> clazz) {
        mClazz = clazz;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        T[] ts = mClazz.getEnumConstants();
        if(json.isJsonNull()){
            return ts[0].getDefault();
        }
        int code = json.getAsInt();
        for (T t : ts) {
            if (t.code() == code) {
                return t;
            }
        }
        return ts[0].getDefault();
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.code());
    }
}
