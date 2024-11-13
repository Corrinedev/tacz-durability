
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import com.corrinedev.gundurability.block.RepairTableBlock;
import com.corrinedev.gundurability.Gundurability;

public class GundurabilityModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Gundurability.MODID);
	public static final RegistryObject<Block> REPAIR_TABLE = REGISTRY.register("repair_table", RepairTableBlock::new);
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
