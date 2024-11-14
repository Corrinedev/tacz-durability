package com.corrinedev.gundurability.repair;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.repair.client.CleaningGui;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReparKitItem extends Item {
    public float durability;
    public ResourceLocation resourcelocation;
    public int min;
    public int max;
    public SoundEvent sound;
    public ReparKitItem(Properties properties, float durability, ResourceLocation image, int min, int max, SoundEvent sound) {
        super(properties);
        this.durability = durability;
        this.resourcelocation = image;
        this.min = min;
        this.max = max;
        this.sound = sound;
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

                if(percent > min && percent <= max) {

                allow = true;

                ItemStack newstack = pStack.copy();

                    //currentitem = pPlayer.getInventory().getItem(pPlayer.getInventory().findSlotMatchingItem(newstack));
                pPlayer.addItem(newstack);
                int slot = 1;
                    for (int i = 0; i < 36; i++) {
                        if(pPlayer.getSlot(i).get().getItem() == this) {
                            slot = i;
                            break;
                        }
                    }
                    Minecraft.getInstance().setScreen(new CleaningGui(pSlot.getItem(), newstack, slot, resourcelocation));
                pStack.setCount(0);


            } else {
                    Minecraft.getInstance().player.displayClientMessage(Component.literal("This repair tool can only be used between " + min + " and " + max + " durability!"), true);
                }
        }
        return allow;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        p_41423_.add(MutableComponent.create(Component.literal("Drag onto a damaged gun to use").getContents()).withStyle(ChatFormatting.GRAY));
    }
}
