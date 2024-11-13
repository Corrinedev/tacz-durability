package com.corrinedev.gundurability.repair;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.repair.client.CleaningGui;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ReparKitItem extends Item {
    public static ItemStack currentitem;
    public ReparKitItem() {
        super(new Properties().durability(500));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player player, InteractionHand p_41434_) {
      //  Minecraft.getInstance().setScreen(new CleaningGui());
        return super.use(p_41432_, player, p_41434_);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        boolean allow = false;

            if (pSlot.getItem().getItem() instanceof ModernKineticGunItem) {

                double percent = (double) pSlot.getItem().getOrCreateTag().getInt("Durability") / Config.MAXDURABILITY.get();
                percent = percent * 100;

                if(percent > 50 && percent <= 75) {

                allow = true;

                ItemStack newstack = pStack.copy();

                    //currentitem = pPlayer.getInventory().getItem(pPlayer.getInventory().findSlotMatchingItem(newstack));
                pPlayer.addItem(newstack);
                int slot = 1;
                    for (int i = 0; i < 36; i++) {
                        if(pPlayer.getSlot(i).get() == newstack) {
                            slot = i;
                            break;
                        }
                    }
                    Minecraft.getInstance().setScreen(new CleaningGui(pSlot.getItem(), newstack, slot));
                    System.out.println(newstack);
                pStack.setCount(0);


            } else {
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("This repair tool can only be used between 1000 and 1800 durability!"), true);
                }
        }
        return allow;
    }
}
