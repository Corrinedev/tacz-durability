
package com.corrinedev.gundurability.item;

import com.corrinedev.gundurability.config.DurabilityItemHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GunBarrelItem extends RepairItem {
	public GunBarrelItem() {
		super(1, 17.5f, 100f, 0f, DurabilityItemHolder.Slots.BARREL);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
	}
}
