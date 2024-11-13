
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

import com.corrinedev.gundurability.Gundurability;

public class GundurabilityModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Gundurability.MODID);
	public static final RegistryObject<SoundEvent> JAMSFX = REGISTRY.register("jamsfx", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("gundurability", "jamsfx")));
}
