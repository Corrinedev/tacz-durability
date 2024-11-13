package com.corrinedev.gundurability.repair;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.repair.client.CleaningGui;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RepairEvents {
    public static int tick = 0;
    public static ItemStack currentitem;
    public static double percent;

    @SubscribeEvent
    public static void cleaningGuiRag(TickEvent.PlayerTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (Minecraft.getInstance().screen instanceof CleaningGui gui) {
            if (tick != 20) {
                tick++;
            } else {
                tick = 0;
                if (gui.cleaning) {
                    percent = (double) event.player.getMainHandItem().getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
                    percent = percent * 100;
                    //Yellow
                    if(percent > 50 && percent <= 75) {
                        player.playSound(SoundEvents.WOOL_HIT);
                        //if(event.side.isClient()) {
                        //    Network.sendToServer(new C2SCleaningPacket(20, gui.gunStack));
                        //}
                        gui.gunStack.getOrCreateTag().putInt("Durability", gui.gunStack.getOrCreateTag().getInt("Durability") + 5);
                    }
                }
            }
        }
    }
}
