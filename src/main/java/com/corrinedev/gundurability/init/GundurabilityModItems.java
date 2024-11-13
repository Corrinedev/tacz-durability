
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.item.*;
import com.corrinedev.gundurability.repair.ReparKitItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GundurabilityModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Gundurability.MODID);
	public static final RegistryObject<Item> WD_40 = REGISTRY.register("wd_40", WD40Item::new);
	public static final RegistryObject<Item> GUN_BARREL = REGISTRY.register("gun_barrel", GunBarrelItem::new);
	public static final RegistryObject<Item> GUN_BOLT = REGISTRY.register("gun_bolt", GunBoltItem::new);
	public static final RegistryObject<Item> RECOIL_SPRING = REGISTRY.register("recoil_spring", RecoilSpringItem::new);
	public static final RegistryObject<Item> BRASS_BRUSH = REGISTRY.register("brass_brush", BrassBrushItem::new);
	public static final RegistryObject<Item> BRASS_BRUSH_NEW = REGISTRY.register("brass_brush_new", ReparKitItem::new);
	public static final RegistryObject<Item> REPAIR_TABLE = block(GundurabilityModBlocks.REPAIR_TABLE);

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
