
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.world.inventory.RepairGUIMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GundurabilityModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Gundurability.MODID);
	public static final RegistryObject<MenuType<RepairGUIMenu>> REPAIR_GUI = REGISTRY.register("repair_gui", () -> IForgeMenuType.create(RepairGUIMenu::new));
}
