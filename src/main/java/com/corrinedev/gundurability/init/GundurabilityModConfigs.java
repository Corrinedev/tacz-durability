package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.ConfigClient;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.corrinedev.gundurability.Config;
import com.corrinedev.gundurability.Gundurability;

@Mod.EventBusSubscriber(modid = Gundurability.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GundurabilityModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC, "tacz-durability[SERVER].toml");
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC, "tacz-durability[CLIENT].toml");
		});
	}
}
