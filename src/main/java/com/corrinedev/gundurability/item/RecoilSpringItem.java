
package com.corrinedev.gundurability.item;

import com.corrinedev.gundurability.config.DurabilityItemHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RecoilSpringItem extends RepairItem {
	public RecoilSpringItem() {
		super(1, 30, 90f, 10f, DurabilityItemHolder.Slots.SPRING);
	}
}
