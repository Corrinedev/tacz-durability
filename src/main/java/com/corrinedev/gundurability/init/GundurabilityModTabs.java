
package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.config.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GundurabilityModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gundurability.MODID);
	public static final RegistryObject<CreativeModeTab> TACZ_DURABILITY = REGISTRY.register("tacz_durability",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.gundurability.tacz_durability")).icon(() -> new ItemStack(GundurabilityModItems.REGISTRY.get(new ResourceLocation(Config.TABLOCATION.get())))).displayItems((parameters, tabData) -> {

				tabData.accept(GundurabilityModItems.REPAIR_TABLE.get());
				GundurabilityModItems.REGISTRY.values().forEach(item -> {if(!item.toString().equals("gundurability:ak_barrel")) tabData.accept(item);});

			}).build());
}
