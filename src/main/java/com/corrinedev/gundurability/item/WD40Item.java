
package com.corrinedev.gundurability.item;

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

public class WD40Item extends ReparKitItem {
	public WD40Item() {
		super(new Properties().durability(20).rarity(Rarity.COMMON), 30, new ResourceLocation("gundurability", "textures/item/wd40.png"), 60, 80, SoundEvents.GENERIC_SPLASH);
	}



	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.literal("Restores between 60% and 80% Durability"));
	}
}
