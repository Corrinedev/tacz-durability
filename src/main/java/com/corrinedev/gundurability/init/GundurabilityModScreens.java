
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.client.gui.RepairGUIScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GundurabilityModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(GundurabilityModMenus.REPAIR_GUI.get(), RepairGUIScreen::new);
		});
	}
}
