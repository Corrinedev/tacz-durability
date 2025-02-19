package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.client.tooltip.ClientGunTooltip;
import com.tacz.guns.item.GunTooltipPart;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = ClientGunTooltip.class, remap = false)
public abstract class TooltipMixin implements ClientTooltipComponent {
    @Shadow @Final private ItemStack gun;

    @Shadow private @Nullable MutableComponent packInfo;

    @Shadow private MutableComponent damage;
    @Shadow private MutableComponent weight;

    @Shadow private MutableComponent headShotMultiplier;

    @Shadow private MutableComponent armorIgnore;

    @Shadow protected abstract boolean shouldShow(GunTooltipPart part);

    @Shadow private MutableComponent tips;

    @Shadow private @Nullable MutableComponent gunType;

    @Shadow private MutableComponent levelInfo;

    @Shadow private Component ammoName;

    @Shadow private MutableComponent ammoCountText;

    @Shadow private @Nullable List<FormattedCharSequence> desc;

    /**
     * @author Corrinedev
     * @reason Because injects modifying locals is annoying
     */
    @Overwrite
    public void renderText(Font font, int pX, int pY, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        int yOffset = pY;
        if (this.shouldShow(GunTooltipPart.DESCRIPTION) && this.desc != null) {
            yOffset = pY + 2;

            for(FormattedCharSequence sequence : desc) {
                font.drawInBatch(sequence, (float)pX, (float)yOffset, 11184810, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yOffset += 10;
            }
        }

        if (this.shouldShow(GunTooltipPart.AMMO_INFO)) {
            yOffset += 4;
            font.drawInBatch(ammoName, (float)(pX + 20), (float)yOffset, 16755200, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(ammoCountText, (float)(pX + 20), (float)(yOffset + 10), 7829367, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 20;
        }

        if (this.shouldShow(GunTooltipPart.BASE_INFO)) {
            yOffset += 4;
            font.drawInBatch(levelInfo, (float)pX, (float)yOffset, 7829367, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
            if (gunType != null) {
                font.drawInBatch(this.gunType, (float)pX, (float)yOffset, 7829367, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yOffset += 10;
            }

            font.drawInBatch(this.damage, (float)pX, (float)yOffset, 7829367, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
            if (this.gun.getOrCreateTag().contains("Durability")) {
                MutableComponent durability = MutableComponent.create(Component.literal("Durability: ").getContents()).append(MutableComponent.create(Component.literal(String.valueOf(this.gun.getOrCreateTag().getInt("Durability"))).getContents()).withStyle(ChatFormatting.AQUA)).append(MutableComponent.create(Component.literal("/").getContents()).withStyle(ChatFormatting.GRAY)).append(MutableComponent.create(Component.literal(String.valueOf(Config.getDurability(gun.getOrCreateTag().getString("GunId")))).getContents()).withStyle(ChatFormatting.AQUA));
                font.drawInBatch(durability, (float)pX, (float)yOffset, 7829367, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                yOffset += 10;
            }
        }

        if (shouldShow(GunTooltipPart.EXTRA_DAMAGE_INFO)) {
            yOffset += 4;
            font.drawInBatch(armorIgnore, (float)pX, (float)yOffset, 16755200, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
            font.drawInBatch(headShotMultiplier, (float)pX, (float)yOffset, 16755200, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
            font.drawInBatch(weight, (float)pX, (float)yOffset, 16777215, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
        }

        if (this.shouldShow(GunTooltipPart.UPGRADES_TIP)) {
            yOffset += 4;
            font.drawInBatch(tips, (float)pX, (float)yOffset, 16777215, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            yOffset += 10;
        }

        if (this.shouldShow(GunTooltipPart.PACK_INFO) && this.packInfo != null) {
            yOffset += 4;
            font.drawInBatch(this.packInfo, (float)pX, (float)yOffset, 16777215, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        }

    }
    @Inject(method = "getHeight", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void addHeight(CallbackInfoReturnable<Integer> cir, int height) {
        cir.setReturnValue(height + 14);
    }
}
