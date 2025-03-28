package com.corrinedev.gundurability.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GunItemDisplayRenderer {

    @SubscribeEvent
    public static void renderGunHolo(RenderHandEvent event) {
        if(event.getItemStack().getItem() instanceof ModernKineticGunItem)
            renderNameTag(Component.literal("Ammo: " + event.getItemStack().getOrCreateTag().getInt("GunCurrentAmmoCount")), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
    }

    protected static void renderNameTag(Component p_114499_, PoseStack p_114500_, MultiBufferSource p_114501_, int p_114502_) {
        float f = 0f;
        int i = "deadmau5".equals(p_114499_.getString()) ? -10 : 0;
        p_114500_.pushPose();
        p_114500_.translate(0.5F, f, -8.0F);
        p_114500_.mulPose(new Quaternionf(2.135E-3, -9.976E-1, 5.833E-2, 3.650E-2));
        //p_114500_.mulPose();
        p_114500_.scale(0.05F, 0.05F, 0.05F);
        Matrix4f matrix4f = p_114500_.last().pose();
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int)(f1 * 255.0F) << 24;
        Font font = Minecraft.getInstance().font;
        float f2 = (float)(-font.width(p_114499_) / 2);
        font.drawInBatch(p_114499_, f2, (float)i, 553648127, false, matrix4f, p_114501_, Font.DisplayMode.SEE_THROUGH, j, p_114502_);
        font.drawInBatch(p_114499_, f2, (float) i, -1, false, matrix4f, p_114501_, Font.DisplayMode.NORMAL, 0, p_114502_);

        p_114500_.popPose();
    }
}
