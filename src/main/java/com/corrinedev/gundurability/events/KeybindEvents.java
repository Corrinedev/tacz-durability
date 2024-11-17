package com.corrinedev.gundurability.events;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.network.InspectDurabilityMessage;
import com.tacz.guns.client.input.InspectKey;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeybindEvents {
    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        if(event.getItemStack().getItem() instanceof ModernKineticGunItem) {
            event.getToolTip().add(MutableComponent.create(Component.literal("Durability: " + String.valueOf(event.getItemStack().getOrCreateTag().getInt("Durability"))).getContents()).withStyle(ChatFormatting.GRAY));
        }
    }

    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        if(InspectKey.INSPECT_KEY.consumeClick()) {
            Gundurability.PACKET_HANDLER.sendToServer(new InspectDurabilityMessage(0, 0));
            InspectDurabilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
        }
    }
}
