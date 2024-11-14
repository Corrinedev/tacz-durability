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

            assert player != null;
            ItemStack RepairStack = player.getSlot(gui.repairStackSlot).get();
            if(player.getSlot(gui.repairStackSlot).get().getItem() instanceof ReparKitItem) {
                ReparKitItem repair = (ReparKitItem) RepairStack.getItem();

                if (tick != 40) {
                    tick++;
                } else {
                    tick = 0;
                    if (gui.cleaning) {
                        percent = (double) gui.gunStack.getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
                        percent = percent * 100;
                        //Yellow
                        if (percent > repair.min && percent <= repair.max) {
                            player.playSound(repair.sound);
                            float repairpercent = (float) Config.MAXDURABILITY.get() / 100;

                            repairpercent = repair.durability * repairpercent;

                            //System.out.println(gui.repairStack);\

                            gui.gunStack.getOrCreateTag().putInt("Durability", (int) (gui.gunStack.getOrCreateTag().getInt("Durability") + repairpercent));
                            if (RepairStack.getDamageValue() != RepairStack.getMaxDamage()) {
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
}
