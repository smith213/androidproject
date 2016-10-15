package com.freephone.justfofun.freephone.restful;

import com.freephone.justfofun.freephone.restful.serializer.BooleanSerializer;
import com.freephone.justfofun.freephone.restful.serializer.EnumSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by imorn on 15/12/14.
 */
public class GsonCreator {

    private static volatile Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            synchronized (GsonCreator.class) {
                if (gson == null) {
                    gson = createNewGson();
                }
            }
        }
        return gson;
    }

    private static Gson createNewGson() {
        BooleanSerializer booleanSerializer = new BooleanSerializer();
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(boolean.class, booleanSerializer)
                .registerTypeAdapter(Boolean.class, booleanSerializer)
                .registerTypeAdapter(ResultCode.class, new EnumSerializer<>(ResultCode.class))
//                .registerTypeAdapter(Gender.class, new EnumSerializer<>(Gender.class))
//                .registerTypeAdapter(PushServiceType.class, new EnumSerializer<>(PushServiceType.class))
//                .registerTypeAdapter(UserRole.class, new EnumSerializer<>(UserRole.class))
//                .registerTypeAdapter(UpgradeState.class, new EnumSerializer<>(UpgradeState.class))
//                .registerTypeAdapter(ImContentType.class, new EnumSerializer<>(ImContentType.class))
//                .registerTypeAdapter(PatientSource.class,new EnumSerializer<>(PatientSource.class))
//                .registerTypeAdapter(GroupType.class,new EnumSerializer<>(GroupType.class))
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
