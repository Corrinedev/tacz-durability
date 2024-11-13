package com.corrinedev.gundurability.repair.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class CleaningGui extends Screen {
    private final Minecraft minecraft;
    public int cleaningragX = 50;
    public int cleaningragXHitbox = 0;
    public int cleaningragXHitbox2 = 0;
    public int cleaningragY = 50;
    public int cleaningragYHitbox = 0;
    public int cleaningragYHitbox2 = 0;
    public ItemStack gunStack;
    public Slot gunStackSlot;
    public boolean cleaning = false;

    public ResourceLocation RAG = new ResourceLocation("gundurability", "textures/item/brushmc.png");
    //public ImageWidget ragWidget = new ImageWidget(cleaningragX, cleaningragY, 32, 32, RAG);


    public CleaningGui(ItemStack stack, Slot itemSlot) {
        super(Component.literal("Weapon Repair"));
        this.minecraft = Minecraft.getInstance();
        this.gunStack = stack;
        this.gunStackSlot = itemSlot;
        super.minecraft = Minecraft.getInstance();

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gui, int mousex, int mousey, float partialtick) {
        super.render(gui, mousex, mousey, partialtick);
       // System.out.println(cleaning);
       // RenderSystem.enableBlend();
        if (!isDragging()) {
            cleaning = false;
        }
        //  renderItem(gui.guiWidth() / 2, gui.guiHeight() / 2, gui, 175, this.gunStack);
       // gui.fill(CleaningGuiEvents.x1, CleaningGuiEvents.y1, CleaningGuiEvents.x2, CleaningGuiEvents.y2, -1);

      //  gui.blit(RAG, gui.guiHeight() / 2, gui.guiWidth() / 2, 0, 0, 64, 64);
       // gui.renderItem(new ItemStack(Items.SPONGE), cleaningragX, cleaningragY);


    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
       // cleaningragX = cleaningragX + Math.toIntExact(Math.round(pDragX));
       // cleaningragY = cleaningragY + Math.toIntExact(Math.round(pDragY));
        if((pMouseX > cleaningragXHitbox2 && pMouseX < cleaningragXHitbox) && (pMouseY > cleaningragYHitbox2 && pMouseY < cleaningragYHitbox)) {
            cleaningragX = Math.toIntExact(Math.round(pMouseX));
            cleaningragY = Math.toIntExact(Math.round(pMouseY));
            init();
            if ((pMouseX > CleaningGuiEvents.x1 && pMouseX < CleaningGuiEvents.x2) && (pMouseY > CleaningGuiEvents.y1 && pMouseY < CleaningGuiEvents.y2)) {
                    cleaning = true;
            } else {
                cleaning = false;
            }
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        } else {
            cleaning = false;
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    public static PoseStack renderItem(int width, int height, GuiGraphics pGuiGraphics, float scale, ItemStack itemstack) {

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
        return poseStack;
    }

    @Override
    public void onClose() {
        super.onClose();
    }
    @Override
    protected void init() {
        clearWidgets();
        //    int mousex = Math.toIntExact(Math.round(Minecraft.getInstance().mouseHandler.xpos()));
        //   int mousey = Math.toIntExact(Math.round(Minecraft.getInstance().mouseHandler.ypos()));
        //RenderSystem.enableDepthTest();
        ImageWidget ragWidget = new ImageWidget(cleaningragX - 16, cleaningragY - 16, 32, 32, RAG);
        addRenderableWidget(ragWidget);
        // ragWidget.visible = false;
        cleaningragXHitbox = ragWidget.getX() + ragWidget.getWidth() + 16;
        cleaningragXHitbox2 = ragWidget.getX() -16;
        cleaningragYHitbox = ragWidget.getY() + ragWidget.getHeight() + 16;
        cleaningragYHitbox2 = ragWidget.getY() - 16;
        super.init();
    }
}
