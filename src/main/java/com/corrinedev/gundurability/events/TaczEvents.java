package com.corrinedev.gundurability.events;


import com.corrinedev.gundurability.config.BiomeModifier;
import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityModifier;
import com.corrinedev.gundurability.init.GundurabilityModGameRules;
import com.corrinedev.gundurability.init.GundurabilityModSounds;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TaczEvents {

   //@SubscribeEvent
   //public static void durabilityConfirm(TickEvent.PlayerTickEvent event) {
   //    if(event.player.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
   //        if (!event.player.getMainHandItem().getOrCreateTag().contains("Durability")) {
   //            event.player.getMainHandItem().getOrCreateTag().putInt("Durability", Config.getDurability(event.player.getMainHandItem().getOrCreateTag().getString("GunId")));
   //        }
   //    }
   //}

    @SubscribeEvent
    public static void onShootEvent(GunFireEvent event) {
 
         if(event.getLogicalSide().isServer()) {
             if (event.getShooter().getMainHandItem().getOrCreateTag().contains("Durability")) {
                 if (!event.getGunItemStack().getOrCreateTag().getBoolean("Jammed") && event.getGunItemStack().getOrCreateTag().getInt("Durability") > 0) {
                     //Biome modifier code start
                     float biomeModifier = 1;
                     float gunModifier = 1;
                     double randomNumber = Math.random();

                     for( DurabilityModifier modifier : Config.DURABILITY_LIST.get()) {
                         if(event.getGunItemStack().getOrCreateTag().getString("GunId").equals(modifier.gunId())) {
                             if (randomNumber < 0.5) {
                                 // Round down
                                 gunModifier = (int) Math.floor(modifier.jamMultiplier());
                             } else {
                                 // Round up
                                 gunModifier = (int) Math.ceil(modifier.jamMultiplier());
                             }
                         }
                     }
                     for (BiomeModifier modifier : Config.BIOMEMODIFIERS.get()) {
                         if(event.getShooter().level().getBiome(event.getShooter().blockPosition()).is(new ResourceLocation(modifier.biomeName()))) {
                             if (randomNumber < 0.5) {
                                 // Round down
                                 biomeModifier = (int) Math.floor(modifier.multiplier());
                             } else {
                                 // Round up
                                 biomeModifier = (int) Math.ceil(modifier.multiplier());
                             }
                         }
                     }
                         if (event.getShooter().getMainHandItem().getOrCreateTag().getString("GunFireMode").equals("BURST")) {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - Math.round(3 * (gunModifier * biomeModifier)));
                         } else {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") - Math.round(1 * (gunModifier * biomeModifier)));
                         }
                 }

                 if (!event.getShooter().getMainHandItem().getOrCreateTag().getBoolean("Jammed") && event.getGunItemStack().getOrCreateTag().getInt("Durability") != 0) {
                     boolean allowjam = true;
                     for (int i = 0; i < Config.GUN_LIST.get().size(); i++) {
                         if(event.getGunItemStack().getOrCreateTag().getString("GunId").equals(Config.GUN_LIST.get().get(i).toString())) {
                             allowjam = false;
                         }

                     }
                    if(Config.JAMCHANCE.get() != 0 && allowjam) {
                        if (Mth.nextInt(RandomSource.create(), -1, Math.round((float) event.getShooter().getMainHandItem().getOrCreateTag().getInt("Durability") / Config.JAMCHANCE.get())) == 0) {

                            event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("Jammed", true);
                            // event.getShooter().getMainHandItem().getOrCreateTag().putInt("SavedAmmo", event.getShooter().getMainHandItem().getOrCreateTag().getInt("GunCurrentAmmoCount"));
                            // event.getShooter().getMainHandItem().getOrCreateTag().putInt("GunCurrentAmmoCount", 0);
                            // event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("BulletInBarrel", false);
                            event.getShooter().playSound(GundurabilityModSounds.JAMSFX.get());
                            Player player = (Player)event.getShooter();
                            player.displayClientMessage(MutableComponent.create(Component.literal("Jammed!").getContents()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.RED), true);

                        }
                    }
                 } else {
                     if (event.getShooter().level().getGameRules().getBoolean(GundurabilityModGameRules.GUNBREAK) || Config.GUNSBREAK.get()) {
                         if (event.getGunItemStack().getOrCreateTag().getInt("Durability") <= 0) {
                             event.getShooter().getMainHandItem().setCount(0);
                             event.getShooter().playSound(SoundEvents.ITEM_BREAK);
                             Player player = (Player)event.getShooter();
                             player.displayClientMessage(MutableComponent.create(Component.literal("Your Gun Broke").getContents()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED), true);
                         }
                     }
                     event.setCanceled(true);
                 }
             } else {
                 // event.getShooter().getMainHandItem().getOrCreateTag().putBoolean("HasDurability", true);
                 event.getShooter().getMainHandItem().getOrCreateTag().putInt("Durability", Config.getDurability(event.getShooter().getMainHandItem().getOrCreateTag().getString("GunId")));
             }
         }
         if(event.getLogicalSide().isClient()) {
             if(event.getGunItemStack().getOrCreateTag().getBoolean("Jammed") || event.getGunItemStack().getOrCreateTag().getInt("Durability") <=0) {
                 event.setCanceled(true);
                 //event.getShooter().playSound(GundurabilityModSounds.JAMSFX.get());
             }
         }
    }
}