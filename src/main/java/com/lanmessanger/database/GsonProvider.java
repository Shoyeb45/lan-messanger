package main.java.com.lanmessanger.database;

import com.google.gson.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonProvider {

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();

        // Custom serializer/deserializer for LocalDateTime
        builder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        });

        builder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        });

        return builder.create();
    }
}
