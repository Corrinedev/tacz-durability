
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.gundurability.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GundurabilityModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> GUNBREAK = GameRules.register("gunbreak", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
}
