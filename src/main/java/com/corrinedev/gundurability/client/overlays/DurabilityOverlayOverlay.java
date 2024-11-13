
package com.corrinedev.gundurability.client.overlays;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.ConfigClient;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
		if (entity.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
			int color;

			double percent = (double) entity.getMainHandItem().getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
			percent = percent * 100;
			//Yellow
			if(percent > 50 && percent <= 75) {
				color = -154;
			}
			//Orange
			else if (percent > 25 && percent <= 50){
				color = -26317;
			}
			//Red
			else if (percent <= 25) {
				color = -39322;
			}
			//Green
			else {
				color = -13382656;
			}
			event.getGuiGraphics().drawString(Minecraft.getInstance().font, entity.getMainHandItem().getHoverName().getString(), w - 97, h - 16, color, false);
			}
		}
	}
}
