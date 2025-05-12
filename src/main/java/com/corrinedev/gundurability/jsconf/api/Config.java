package com.corrinedev.gundurability.jsconf.api;

import com.corrinedev.gundurability.config.DurabilityModifier;
import com.corrinedev.jsconf.JSConf;
import com.corrinedev.jsconf.api.ConfigValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config extends com.corrinedev.jsconf.api.Config {
    public static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(DurabilityModifier.class, new DurabilityModifier.DurabilityModifierSerializer()).setPrettyPrinting().create();

    public Config(String fileName) {
        super((fileName));
    }

    public ConfigValue<?> getValue(String key) {
        return this.VALUES.get(key);
    }

    public ConfigValue<?> getValueAndUpdate(String key) {
        try {
            this.readConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.VALUES.get(key);
    }

    public Config update() {
        try {
            this.readConfig();
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void register() {
        try {
            this.readConfig();
            JSConf.LOGGER.info("Config {} was loaded, values = {}", this.fileName, this.VALUES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Config addValue(ConfigValue<?> value) {
        this.VALUES.put(value.element, value);
        return this;
    }

    private void readConfig() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);

            for(String e : obj.keySet()) {
                JsonElement element = obj.get(e);
                ConfigValue<?> val = this.VALUES.get(e);
                val.set(GSON.fromJson(element, val.getType()));
                JSConf.LOGGER.info("type = {}", val.get().getClass().getName());
            }
        } else {
            this.firstWrite();
        }

    }

    private void firstWrite() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if (!file.exists()) {
            FileWriter writer = new FileWriter(file);
            JsonObject config = new JsonObject();

            for(ConfigValue<?> value : this.VALUES.values()) {
                config.add(value.element, value.getAsJson());
            }

            writer.write(GSON.toJson(config));
            writer.close();
        }

    }
}
