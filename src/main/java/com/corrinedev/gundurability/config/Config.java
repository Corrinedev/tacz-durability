package com.corrinedev.gundurability;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Integer> MAXDURABILITY;
	public static final ForgeConfigSpec.ConfigValue<Integer> SWAMPBIOMEMODIFIER;
	public static final ForgeConfigSpec.ConfigValue<Integer> JUNGLEBIOMEMODIFIER;
	public static final ForgeConfigSpec.ConfigValue<Integer> DESERTBIOMEMODIFIER;
	public static final ForgeConfigSpec.ConfigValue<Integer> WATERMODIFIER;
	public static final ForgeConfigSpec.ConfigValue<Integer> COLDBIOMEMODIFIER;
	public static final ForgeConfigSpec.ConfigValue<Boolean> GUNSBREAK;
	public static final ForgeConfigSpec.ConfigValue<Integer> JAMCHANCE;
	static {
		BUILDER.push("maxdurability");
		MAXDURABILITY = BUILDER.comment("The durability the gun starts with and the max it can repair to").define("maxdurability", 2000);
		BUILDER.pop();
		BUILDER.push("gunsbreak");
		GUNSBREAK = BUILDER.comment("Do guns break or not").define("gunsbreak", false);
		BUILDER.pop();
		BUILDER.push("jamchance");
		JAMCHANCE = BUILDER.comment("Set to 1 to stop jamming entirely, do NOT set below 1").define("jamchance", 20);
		BUILDER.pop();
		BUILDER.push("swampmodifier");
		SWAMPBIOMEMODIFIER = BUILDER.comment("Jam chance increase, random value from 1 to the config value (increased number = lower multiplier)").define("swampmodifier", 2);
		BUILDER.pop();
		BUILDER.push("junglemodifier");
		JUNGLEBIOMEMODIFIER = BUILDER.comment("Jam chance increase, random value from 1 to the config value (increased number = lower multiplier)").define("junglemodifier", 3);
		BUILDER.pop();
		BUILDER.push("watermodifier");
		WATERMODIFIER = BUILDER.comment("Jam chance increase, random value from 1 to the config value (increased number = lower multiplier)").define("watermodifier", 1);
		BUILDER.pop();
		BUILDER.push("desertmodifier");
		DESERTBIOMEMODIFIER = BUILDER.comment("Jam chance increase, random value from 1 to the config value (increased number = lower multiplier)").define("desertmodifier", 4);
		BUILDER.pop();
		BUILDER.push("coldmodifier");
		COLDBIOMEMODIFIER = BUILDER.comment("Jam chance increase, random value from 1 to the config value (increased number = lower multiplier)").define("coldmodifier", 4);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
