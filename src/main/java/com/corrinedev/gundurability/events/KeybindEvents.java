package com.corrinedev.gundurability.events;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.network.InspectDurabilityMessage;
import com.tacz.guns.client.input.InspectKey;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class KeybindEvents {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        if(InspectKey.INSPECT_KEY.consumeClick()) {
            Gundurability.PACKET_HANDLER.sendToServer(new InspectDurabilityMessage(0, 0));
            InspectDurabilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
        }
    }
}
