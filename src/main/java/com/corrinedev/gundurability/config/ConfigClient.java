package com.corrinedev.gundurability.config;

import com.corrinedev.jsconf.api.Config;
import com.corrinedev.jsconf.api.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigClient {
	public static final Config BUILDER = new Config("gundurability-client");
	public static final ConfigValue<Boolean> SHOWGUI;
	public static final ConfigValue<Boolean> SHOWRED;
	static {
		SHOWGUI = new ConfigValue<>(true, "showGui", BUILDER, boolean.class);
		SHOWRED = new ConfigValue<>(true, "redLowDurability", BUILDER, boolean.class);
	}

}
