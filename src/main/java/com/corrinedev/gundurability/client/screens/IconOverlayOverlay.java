
package com.corrinedev.gundurability.client.screens;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.ConfigClient;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class IconOverlayOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		if (ConfigClient.SHOWGUI.get()) {
            assert Minecraft.getInstance().player != null;
            if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
                int w = event.getWindow().getGuiScaledWidth();
                int h = event.getWindow().getGuiScaledHeight();

                Player entity = Minecraft.getInstance().player;
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.setShaderColor(1f,1f,1f,1f);
                float percent = (float) entity.getMainHandItem().getOrCreateTag().getInt("Durability") / Config.getDurability(Minecraft.getInstance().player.getMainHandItem().getOrCreateTag().getString("GunId"));

                //RenderSystem.setShaderColor(1f, percent, percent, 1f);

                percent *= 100;
                if (!entity.getMainHandItem().getOrCreateTag().getBoolean("Jammed")) {
//Yellow
                    if (percent > 50 && percent <= 75) {
                        event.getGuiGraphics().blit(new ResourceLocation("gundurability:textures/screens/yellow.png"), w - 115, h - 25, 0, 0, 16, 16, 16, 16);
                    }
//Orange
                    else if (percent > 25 && percent <= 50) {
                        event.getGuiGraphics().blit(new ResourceLocation("gundurability:textures/screens/orange.png"), w - 115, h - 25, 0, 0, 16, 16, 16, 16);
                    }
//Red
                    else if (percent <= 25) {
                        event.getGuiGraphics().blit(new ResourceLocation("gundurability:textures/screens/reds.png"), w - 115, h - 25, 0, 0, 16, 16, 16, 16);
                    }
//Green
                    else {
                        event.getGuiGraphics().blit(new ResourceLocation("gundurability:textures/screens/green.png"), w - 115, h - 25, 0, 0, 16, 16, 16, 16);
                    }
                } else {
                    event.getGuiGraphics().blit(new ResourceLocation("gundurability:textures/screens/jam.png"), w - 115, h - 25, 0, 0, 16, 16, 16, 16);
                }
                RenderSystem.depthMask(true);
                RenderSystem.defaultBlendFunc();
                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }
        }
	}
}
