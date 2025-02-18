package com.corrinedev.gundurability.mixin;

import com.tacz.guns.client.tooltip.ClientGunTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ClientGunTooltip.class, remap = false)
public abstract class TooltipMixin implements ClientTooltipComponent {
    @Shadow @Final private ItemStack gun;

    @Shadow private @Nullable MutableComponent packInfo;

    @Shadow private MutableComponent damage;

    @Inject(method = "renderText", at = @At("HEAD"))
    public void addText(Font font, int pX, int pY, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource, CallbackInfo ci) {
        if (this.gun.getOrCreateTag().contains("Durability")) {
            MutableComponent durability = MutableComponent.create(Component.literal("Durability: ").getContents()).withStyle(ChatFormatting.DARK_GRAY).append(MutableComponent.create(Component.literal(String.valueOf(this.gun.getOrCreateTag().getInt("Durability"))).getContents()).withStyle(ChatFormatting.AQUA));
            this.damage.append(durability);
        }
    }
}
