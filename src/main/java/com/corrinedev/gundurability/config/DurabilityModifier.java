package com.corrinedev.gundurability.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public record DurabilityModifier(String gunId, int maxDurability, float jamMultiplier, int jamTime) {

    public static class DurabilityModifierSerializer implements JsonDeserializer<DurabilityModifier> {

        public DurabilityModifier deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            var obj = jsonElement.getAsJsonObject();
            String gunId = obj.has("gunId") ? obj.get("gunId").getAsString() : "";
            int maxDurability = obj.has("maxDurability") ? obj.get("maxDurability").getAsInt() : 2000;
            float jamMultiplier = obj.has("jamMultiplier") ? obj.get("jamMultiplier").getAsFloat() : 1f;
            int jamTime = obj.has("jamTime") ? obj.get("jamTime").getAsInt() : 100;
            return new DurabilityModifier(gunId, maxDurability, jamMultiplier, jamTime);
        }
    }

}
