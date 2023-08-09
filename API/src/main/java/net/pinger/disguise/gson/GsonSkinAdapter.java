package net.pinger.disguise.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.pinger.disguise.skin.Skin;
import net.pinger.disguise.skin.SkinModel;

import java.lang.reflect.Type;

public class GsonSkinAdapter implements JsonSerializer<Skin>, JsonDeserializer<Skin> {

    @Override
    public Skin deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject().getAsJsonObject("properties");

        // Retrieve value, signature, model and profile
        String value = object.get("value").getAsString();
        String signature = object.get("signature").getAsString();

        SkinModel model;
        if (!object.has("model")) {
            model = SkinModel.STEVE;
        } else {
            model = SkinModel.getModel(object.get("model").getAsString());
        }

        String profile = null;
        if (object.has("profile")) {
            profile = object.get("profile").getAsString();
        }

        // Return the created object
        return new Skin(value, signature, model, profile);
    }

    @Override
    public JsonElement serialize(Skin skin, Type type, JsonSerializationContext context) {
        // Create a new json object
        JsonObject object = new JsonObject();

        // Fill in the values
        JsonObject properties = new JsonObject();
        properties.addProperty("value", skin.getValue());
        properties.addProperty("signature", skin.getSignature());
        properties.addProperty("model", skin.getModel().getType());

        // Check if we should add profile
        if (skin.getProfile() != null) {
            properties.addProperty("profile", skin.getProfile());
        }

        // Add the given properties
        object.add("properties", properties);

        // Return the object
        return object;
    }
}
