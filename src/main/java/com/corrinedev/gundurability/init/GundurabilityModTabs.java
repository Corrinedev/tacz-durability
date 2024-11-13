
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.corrinedev.gundurability.Gundurability;

public class GundurabilityModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gundurability.MODID);
	public static final RegistryObject<CreativeModeTab> TACZ_DURABILITY = REGISTRY.register("tacz_durability",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.gundurability.tacz_durability")).icon(() -> new ItemStack(GundurabilityModItems.BRASS_BRUSH.get())).displayItems((parameters, tabData) -> {
				tabData.accept(GundurabilityModItems.WD_40.get());
				tabData.accept(GundurabilityModItems.GUN_BARREL.get());
				tabData.accept(GundurabilityModItems.GUN_BOLT.get());
				tabData.accept(GundurabilityModItems.RECOIL_SPRING.get());
				tabData.accept(GundurabilityModItems.BRASS_BRUSH.get());
				tabData.accept(GundurabilityModBlocks.REPAIR_TABLE.get().asItem());
			})

					.build());
}
