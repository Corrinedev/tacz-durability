
package com.corrinedev.gundurability.client.screens;

import com.corrinedev.gundurability.ConfigClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import com.corrinedev.gundurability.execution.YellowOverlayProcedure;
import com.corrinedev.gundurability.execution.ReturnOverlayProcedure;
import com.corrinedev.gundurability.execution.RedOverlayProcedure;
import com.corrinedev.gundurability.execution.OrangeOverlayProcedure;
import com.corrinedev.gundurability.execution.HoldingOverlayProcedure;
import com.corrinedev.gundurability.execution.DurabilityOverlayDisplayOverlayIngameProcedure;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class DurabilityOverlayOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		if(ConfigClient.SHOWGUI.get()) {
			int w = event.getWindow().getGuiScaledWidth();
			int h = event.getWindow().getGuiScaledHeight();
			Level world = null;
			double x = 0;
			double y = 0;
			double z = 0;
			Player entity = Minecraft.getInstance().player;
			if (entity != null) {
				world = entity.level();
				x = entity.getX();
				y = entity.getY();
				z = entity.getZ();
			}
			if (DurabilityOverlayDisplayOverlayIngameProcedure.execute(entity)) {
				if (HoldingOverlayProcedure.execute(entity))
					event.getGuiGraphics().drawString(Minecraft.getInstance().font,

							ReturnOverlayProcedure.execute(entity), w - 97, h - 16, -13382656, false);
				if (YellowOverlayProcedure.execute(entity))
					event.getGuiGraphics().drawString(Minecraft.getInstance().font,

							ReturnOverlayProcedure.execute(entity), w - 97, h - 16, -154, false);
				if (OrangeOverlayProcedure.execute(entity))
					event.getGuiGraphics().drawString(Minecraft.getInstance().font,

							ReturnOverlayProcedure.execute(entity), w - 97, h - 16, -26317, false);
				if (RedOverlayProcedure.execute(entity))
					event.getGuiGraphics().drawString(Minecraft.getInstance().font,

							ReturnOverlayProcedure.execute(entity), w - 97, h - 16, -39322, false);
			}
		}
	}
}
