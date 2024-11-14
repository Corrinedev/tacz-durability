package com.corrinedev.gundurability.repair.client;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.repair.ReparKitItem;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tacz.guns.util.RenderDistance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
        if(Minecraft.getInstance().screen instanceof CleaningGui gui) {
            Player player = Minecraft.getInstance().player;

            assert player != null;
            ItemStack RepairStack = player.getSlot(gui.repairStackSlot).get();
            if (player.getSlot(gui.repairStackSlot).get().getItem() instanceof ReparKitItem) {
                ReparKitItem repair = (ReparKitItem) RepairStack.getItem();
                int w = event.getGuiGraphics().guiWidth();
                int h = event.getGuiGraphics().guiHeight();
                x1 = event.getGuiGraphics().guiWidth() / 2 - 120;
                x2 = event.getGuiGraphics().guiWidth() / 2 + 120;
                y1 = event.getGuiGraphics().guiHeight() / 2 - 50;
                y2 = event.getGuiGraphics().guiHeight() / 2 + 50;

                // renderItem(w / 2, h / 2, event.getGuiGraphics(), 250F, gui.gunStack);
                float offset = (float) Math.abs(repair.max - 100) / 100;
                renderGun(80, w / 2, h / 2, gui.gunStack, offset);
                double percent = (double) gui.gunStack.getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
                percent = percent * 100;

                if (!(percent > repair.min && percent <= repair.max)) {
                    event.getGuiGraphics().drawCenteredString(Minecraft.getInstance().font, "You can't repair this item further!", w / 2, h / 2 - 50, -1);
                }
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
    public static void renderGun(float scale, int leftPos, int topPos, ItemStack itemStack, float percentoffset) {
        float percent = (float) itemStack.getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
        RenderDistance.markGuiRenderTimestamp();
        float rotationPeriod = 8.0F;
        int xPos = leftPos;
        int yPos = topPos;
        float rotPitch = 15.0F;
        Window window = Minecraft.getInstance().getWindow();
      //  RenderSystem.enableScissor(scissorX, scissorY, scissorW, scissorH);
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, percent + percentoffset, percent + percentoffset, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((float)xPos, (float)yPos, 200.0F);
        posestack.translate(8.0, 8.0, 0.0);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale((float)scale, (float)scale, (float)scale);
        float rot = (float)(System.currentTimeMillis() % (long)((int)(rotationPeriod * 1000.0F))) * (360.0F / (rotationPeriod * 1000.0F));
        posestack.mulPose(Axis.XP.rotationDegrees(rotPitch));
       // posestack.mulPose(Axis.YP.rotationDegrees(rot));
        RenderSystem.applyModelViewMatrix();
        PoseStack tmpPose = new PoseStack();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        Lighting.setupForFlatItems();
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, 15728880, OverlayTexture.NO_OVERLAY, tmpPose, bufferSource, (Level)null, 0);
        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        Lighting.setupFor3DItems();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
     //   RenderSystem.disableScissor();
    }
}
