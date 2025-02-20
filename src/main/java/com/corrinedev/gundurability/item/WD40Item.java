
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

public class WD40Item extends RepairItem {
	public WD40Item() {
		super(12, 5.5f, 70f, 40f, DurabilityItemHolder.Slots.MISC);
	}
}
