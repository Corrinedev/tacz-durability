package com.corrinedev.gundurability.item;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class TooltipEvent {
    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack().getItem() instanceof RepairItem item) {
            if(Minecraft.getInstance().options.advancedItemTooltips && item.getGunIds() != null) {
                event.getTooltipElements().add(Either.left(Component.literal("-Compatible Guns-").withStyle(ChatFormatting.GRAY)));
                for (String gunId : item.getGunIds()) {
                    event.getTooltipElements().add(Either.left(MutableComponent.create(Component.literal(gunId).getContents()).withStyle(ChatFormatting.DARK_GRAY)));
                }
            }
        }
    }
}