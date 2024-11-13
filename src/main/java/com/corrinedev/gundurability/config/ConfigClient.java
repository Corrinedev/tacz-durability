package com.corrinedev.gundurability;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigClient {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHOWGUI;
	static {
		BUILDER.push("maxdurability");
		SHOWGUI = BUILDER.comment("Show the overlay clientside?").define("showgui", true);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
