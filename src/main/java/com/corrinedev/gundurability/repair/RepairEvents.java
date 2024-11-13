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
    public static double percent;

    @SubscribeEvent
    public static void cleaningGuiRag(TickEvent.PlayerTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (Minecraft.getInstance().screen instanceof CleaningGui gui) {
            if (tick != 40) {
                tick++;
            } else {
                tick = 0;
                if (gui.cleaning) {
                    percent = (double) gui.gunStack.getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
                    percent = percent * 100;
                    //Yellow
                    if(percent > 50 && percent <= 75) {
                        assert player != null;
                        player.playSound(SoundEvents.WOOL_HIT);
                        gui.gunStack.getOrCreateTag().putInt("Durability", gui.gunStack.getOrCreateTag().getInt("Durability") + 10);

                        //System.out.println(gui.repairStack);\
                        ItemStack RepairStack = player.getSlot(gui.repairStackSlot).get();
                        System.out.println(RepairStack);
                        System.out.println(gui.repairStackSlot);
                        if(RepairStack.getDamageValue() != RepairStack.getMaxDamage()) {
                            RepairStack.setDamageValue(RepairStack.getDamageValue() + 1);
                        } else {
                          RepairStack.setCount(0);
                        }
                    }
                }
            }
        }
    }
}
