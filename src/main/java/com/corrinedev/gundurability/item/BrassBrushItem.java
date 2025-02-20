
package com.corrinedev.gundurability.item;

import com.corrinedev.gundurability.config.DurabilityItemHolder;
import com.corrinedev.gundurability.repair.ReparKitItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrassBrushItem extends RepairItem {
	public BrassBrushItem() {
		super(16, 2.5f, 90f, 70f, DurabilityItemHolder.Slots.MISC);
	}



	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		//list.add(Component.literal("Repairs between 75% and 90%"));
	}
}
