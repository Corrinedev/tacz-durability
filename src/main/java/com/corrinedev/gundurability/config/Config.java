package com.corrinedev.gundurability.config;

import com.corrinedev.jsconf.api.ConfigValue;
import com.google.common.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.Proxy;
import java.util.List;

public class Config {
	public static final com.corrinedev.jsconf.api.Config SPEC = new com.corrinedev.jsconf.api.Config("gundurability-common");
	public static final ConfigValue<Integer> MAXDURABILITY;
	public static final ConfigValue<List<BiomeModifier>> BIOMEMODIFIERS;
	public static final ConfigValue<Boolean> GUNSBREAK;
	public static final ConfigValue<Integer> JAMCHANCE;
	public static final ConfigValue<Integer> INACCURACYRATE;
	public static final ConfigValue<List<String>> GUN_LIST;
	public static final ConfigValue<List<DurabilityModifier>> DURABILITY_LIST;
	public static final ConfigValue<Boolean> DEBUG;
	public static final ConfigValue<List<DurabilityItemHolder>> ITEMS;
	public static final ConfigValue<String> TABLOCATION;
	static {
		MAXDURABILITY = new ConfigValue<>(2000, "maxDurability", SPEC, new TypeToken<Integer>(){}.getType());

		INACCURACYRATE = new ConfigValue<>(500, "inaccuracyRate", SPEC, new TypeToken<Integer>(){}.getType());

		GUNSBREAK = new ConfigValue<>(false, "doGunsBreak", SPEC, new TypeToken<Boolean>(){}.getType());

		JAMCHANCE = new ConfigValue<>(15, "jamChance", SPEC, new TypeToken<Integer>(){}.getType());

		BIOMEMODIFIERS = new ConfigValue<>(List.of(new BiomeModifier("minecraft:desert", 1.5f), new BiomeModifier("minecraft:river", 2f), new BiomeModifier("minecraft:plains", 0.8f)), "biomeModifiers", SPEC, new TypeToken<List<BiomeModifier>>(){}.getType());

		GUN_LIST = new ConfigValue<>(List.of("tacz:db_short", "tacz:db_long"), "unjammableList", SPEC, new TypeToken<List<String>>(){}.getType());

		DURABILITY_LIST = new ConfigValue<>(List.of(new DurabilityModifier("tacz:db_short", 1500, 2, 100), new DurabilityModifier("tacz:db_long", 1800, 1, 100)), "durabilityList", SPEC, new TypeToken<List<DurabilityModifier>>(){}.getType());

		DEBUG = new ConfigValue<>(false, "debug", SPEC, new TypeToken<Boolean>(){}.getType());

		ITEMS = new ConfigValue<>(List.of(DurabilityItemHolder.AK_BARREL, DurabilityItemHolder.GUN_BARREL, DurabilityItemHolder.GUN_BOLT, DurabilityItemHolder.RECOIL_SPRING, DurabilityItemHolder.BRASS_BRUSH, DurabilityItemHolder.WD40), "durabilityItems", SPEC, new TypeToken<List<DurabilityItemHolder>>(){}.getType());

		TABLOCATION = new ConfigValue<>("gundurability:brass_brush", "creativeTab", SPEC, new TypeToken<String>(){}.getType());
	}
	public static int getDurability(String gunId) {
		for (DurabilityModifier modifier : DURABILITY_LIST.get()) {
			if (modifier.gunId().equals(gunId)) {
				return modifier.maxDurability();
			}
		}

		return MAXDURABILITY.get();
	}
}
