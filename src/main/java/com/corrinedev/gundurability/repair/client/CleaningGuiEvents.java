package com.corrinedev.gundurability.repair.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber
public class CleaningGuiEvents {

    public static int x1=0;
    public static int x2=0;
    public static int y1=0;
    public static int y2=0;

    @SubscribeEvent
    public static void renderItemInGUI(RenderGuiEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if(Minecraft.getInstance().screen instanceof CleaningGui gui) {
            int w = event.getGuiGraphics().guiWidth();
            int h = event.getGuiGraphics().guiHeight();
            x1 = event.getGuiGraphics().guiWidth() / 2 - 120;
            x2 = event.getGuiGraphics().guiWidth() / 2 + 120;
            y1 = event.getGuiGraphics().guiHeight() / 2 - 50;
            y2 = event.getGuiGraphics().guiHeight() / 2 + 50;

            renderItem(w / 2, h / 2, event.getGuiGraphics(), 250F, gui.gunStack);
            if(gui.gunStack.getOrCreateTag().getInt("Durability") >= 1800) {
                event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, "You can't repair this item further!", w / 2, h / 2 - 50, -1);
            }
        }
    }



    public static void renderItem(int width, int height, GuiGraphics pGuiGraphics, float scale, ItemStack itemstack) {

        Minecraft minecraft = Minecraft.getInstance();
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate((float)(width), (float)(height), 180.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        poseStack.scale(scale, scale, scale);
        PoseStack modelStack = RenderSystem.getModelViewStack();
        modelStack.pushPose();
        modelStack.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        ItemStack itemStack = itemstack;
        BakedModel model = minecraft.getItemRenderer().getModel(itemStack, minecraft.player.level(), minecraft.player, minecraft.player.getId() + ItemDisplayContext.GROUND.ordinal());
        minecraft.getItemRenderer().render(itemStack, ItemDisplayContext.GROUND, false, new PoseStack(), buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        buffer.endBatch();
        modelStack.popPose();
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}
