
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityItemHolder;
import com.corrinedev.gundurability.item.*;
import com.corrinedev.gundurability.repair.ReparKitItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.corrinedev.gundurability.Gundurability.MODID;

@Mod.EventBusSubscriber
public class GundurabilityModItems {
	public static final HashMap<ResourceLocation, Item> REGISTRY = new HashMap<>();
	public static final DeferredRegister<Item> REPAIRTABLEREGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> REPAIR_TABLE = block(GundurabilityModBlocks.REPAIR_TABLE);

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REPAIRTABLEREGISTER.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
	@SubscribeEvent
	public static void register(RegisterEvent event) {
		event.register(ForgeRegistries.ITEMS.getRegistryKey(), (items) -> {
			for(DurabilityItemHolder holder : Config.ITEMS.get()) {
				if(holder.gunTag() != null) {
					List<String> gunIds = new ArrayList<>();
					for (JsonElement e : holder.gunTag().getAsJsonArray("gunIds")) gunIds.add(e.getAsString());
					Pair<String, List<String>> pair = Pair.of(holder.gunTag().getAsJsonPrimitive("tagName").getAsString(), gunIds);
					RepairItem item = new RepairItem(holder.uses(), holder.durability(), holder.maxDurability(), holder.minDurability(), holder.slot(), pair);
					ResourceLocation resourceLocation = new ResourceLocation(holder.id());
					REGISTRY.put(resourceLocation, item);
					items.register(resourceLocation, item);
				} else {
					RepairItem item = new RepairItem(holder.uses(), holder.durability(), holder.maxDurability(), holder.minDurability(), holder.slot());
					ResourceLocation resourceLocation = new ResourceLocation(holder.id());
					REGISTRY.put(resourceLocation, item);
					items.register(resourceLocation, item);
				}
			}
		});
	}
}
