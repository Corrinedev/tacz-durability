
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import com.corrinedev.gundurability.network.InspectDurabilityMessage;
import com.corrinedev.gundurability.network.DurabilityMessage;
import com.corrinedev.gundurability.Gundurability;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class GundurabilityModKeyMappings {
	public static final KeyMapping INSPECT_DURABILITY = new KeyMapping("key.gundurability.inspect_durability", GLFW.GLFW_KEY_H, "key.categories.gameplay") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				Gundurability.PACKET_HANDLER.sendToServer(new InspectDurabilityMessage(0, 0));
				InspectDurabilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};
	public static final KeyMapping DURABILITY = new KeyMapping("key.gundurability.durability", GLFW.GLFW_KEY_H, "key.categories.gameplay") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				Gundurability.PACKET_HANDLER.sendToServer(new DurabilityMessage(0, 0));
				DurabilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(INSPECT_DURABILITY);
		event.register(DURABILITY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				INSPECT_DURABILITY.consumeClick();
				DURABILITY.consumeClick();
			}
		}
	}
}
