package com.corrinedev.gundurability.events;


import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.init.GundurabilityModGameRules;
import com.corrinedev.gundurability.init.GundurabilityModSounds;
import com.tacz.guns.api.event.common.GunFireEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TaczEvents {
    @SubscribeEvent
    public static void onShootEvent(GunFireEvent event) {
 
         if(event.getLogicalSide().isServer()) {
             if (!event.getGunItemStack().getOrCreateTag().getBoolean("Jammed") || event.getGunItemStack().getOrCreateTag().getInt("Durability") <=0) {
                 //Biome modifier code start
                 int biomeModifier = 0;
                 if (event.getShooter().isUnderWater()) {
                     biomeModifier = Config.WATERMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()).containsTag(BiomeTags.IS_JUNGLE)) {
                     biomeModifier = Config.JUNGLEBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()).containsTag(BiomeTags.IS_BADLANDS)) {
                     biomeModifier = Config.DESERTBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()).containsTag(BiomeTags.IS_TAIGA)) {
                     biomeModifier = Config.COLDBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()) == Biomes.DESERT) {
                     biomeModifier = Config.DESERTBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()) == Biomes.SWAMP) {
                     biomeModifier = Config.SWAMPBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()) == Biomes.MANGROVE_SWAMP) {
                     biomeModifier = Config.SWAMPBIOMEMODIFIER.get();
                 } else if (event.getShooter().level().getBiome(event.getShooter().blockPosition()) == Biomes.SNOWY_PLAINS) {
                     biomeModifier = Config.COLDBIOMEMODIFIER.get();
                 }


                 if (event.getShooter().getMainHandItem().getOrCreateTag().contains("Durability")) {
                     if (Mth.nextInt(RandomSource.create(), 1, biomeModifier) == 1 && !(biomeModifier == 0)) {
                         if (event.getShooter().getMainHandItem().getOrCreateTag().getString("GunFireMode").equals("BURST")) {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - 6);
                         } else {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - 2);
                         }
                     } else {
                         if (event.getShooter().getMainHandItem().getOrCreateTag().getString("GunFireMode").equals("BURST")) {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - 3);
                         } else {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - 1);
                         }
                     }
                 } else {
                    // event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("HasDurability", true);
                     event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", Config.MAXDURABILITY.get());
                 }

                 if (!event.getShooter().getMainHandItem().getOrCreateTag().getBoolean("Jammed")) {

                     if (Mth.nextInt(RandomSource.create(), -1, event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") / Config.JAMCHANCE.get()) == 0) {

                         event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("Jammed", true);
                       // event.getShooter().getMainHandItem().getOrCreateTag().putInt("SavedAmmo", event.getShooter().getMainHandItem().getOrCreateTag().getInt("GunCurrentAmmoCount"));
                       // event.getShooter().getMainHandItem().getOrCreateTag().putInt("GunCurrentAmmoCount", 0);
                       // event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("BulletInBarrel", false);
                         event.getShooter().playSound(GundurabilityModSounds.JAMSFX.get());
                         assert Minecraft.getInstance().player != null;
                         Minecraft.getInstance().player.displayClientMessage(MutableComponent.create(Component.literal("Jammed!").getContents()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.RED), true);

                     }
                 }
             } else {
                 if(event.getShooter().level().getGameRules().getBoolean(GundurabilityModGameRules.GUNBREAK) || Config.GUNSBREAK.get()) {
                     if(event.getGunItemStack().getOrCreateTag().getInt("Durability") <=0) {
                         event.getShooter().getMainHandItem().setCount(0);
                         event.getShooter().playSound(SoundEvents.ITEM_BREAK); 
                         assert Minecraft.getInstance().player != null;
                         Minecraft.getInstance().player.displayClientMessage(MutableComponent.create(Component.literal("Your Gun Broke").getContents()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED), true);
                     }
                 }
                 event.setCanceled(true);
             }
         }
         if(event.getLogicalSide().isClient()) {
             if(event.getGunItemStack().getOrCreateTag().getBoolean("Jammed")) {
                 event.setCanceled(true);
                 event.getShooter().playSound(GundurabilityModSounds.JAMSFX.get());
             }
         }
    }
}
