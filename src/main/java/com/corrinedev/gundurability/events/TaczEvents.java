package com.corrinedev.gundurability.events;


import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.config.BiomeModifier;
import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityModifier;
import com.corrinedev.gundurability.init.GundurabilityModGameRules;
import com.corrinedev.gundurability.init.GundurabilityModSounds;
import com.corrinedev.gundurability.util.Work;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TaczEvents {
    public static final String JAM = "Jammed";
    public static final String DURABILITY = "Durability";
    public static final String OTHERHANDLE = "gundurability$handlingJamming";

    @SubscribeEvent
    public static void onShootEvent(GunFireEvent event) {

         if(event.getLogicalSide().isServer()) {
             if(!(event.getShooter() instanceof Player) && event.getGunItemStack().getOrCreateTag().getBoolean(JAM)) handleUnjammingForNonPlayers(event.getShooter());
             if (event.getShooter().getMainHandItem().getOrCreateTag().contains(DURABILITY)) {
                 if (!event.getGunItemStack().getOrCreateTag().getBoolean(JAM) && event.getGunItemStack().getOrCreateTag().getInt(DURABILITY) > 0) {
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
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt(DURABILITY, event.getShooter().getMainHandItem().getOrCreateTag().getInt(DURABILITY) - Math.round(3 * (gunModifier * biomeModifier)));
                         } else {
                             event.getShooter().getMainHandItem().getOrCreateTag().putInt(DURABILITY, event.getShooter().getMainHandItem().getOrCreateTag().getInt(DURABILITY) - Math.round(1 * (gunModifier * biomeModifier)));
                         }
                 }

                 if (!event.getShooter().getMainHandItem().getOrCreateTag().getBoolean(JAM) && event.getGunItemStack().getOrCreateTag().getInt(DURABILITY) != 0) {
                     boolean allowjam = true;
                     for (int i = 0; i < Config.GUN_LIST.get().size(); i++) {
                         if(event.getGunItemStack().getOrCreateTag().getString("GunId").equals(Config.GUN_LIST.get().get(i).toString())) {
                             allowjam = false;
                         }

                     }
                    if(Config.JAMCHANCE.get() != 0 && allowjam) {
                        if (Mth.nextInt(RandomSource.create(), -1, Math.round((float) event.getShooter().getMainHandItem().getOrCreateTag().getInt(DURABILITY) / Config.JAMCHANCE.get())) == 0) {

                            event.getShooter().getMainHandItem().getOrCreateTag().putBoolean(JAM, true);
                            event.getShooter().playSound(GundurabilityModSounds.JAMSFX.get());
                            if(event.getShooter() instanceof Player player)
                                player.displayClientMessage(MutableComponent.create(Component.literal("Jammed!").getContents()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.RED), true);
                        }
                    }
                 } else {
                     if (event.getShooter().level().getGameRules().getBoolean(GundurabilityModGameRules.GUNBREAK) || Config.GUNSBREAK.get()) {
                         if (event.getGunItemStack().getOrCreateTag().getInt(DURABILITY) <= 0) {
                             event.getShooter().getMainHandItem().setCount(0);
                             event.getShooter().playSound(SoundEvents.ITEM_BREAK);

                             if(event.getShooter() instanceof Player player)
                                 player.displayClientMessage(MutableComponent.create(Component.literal("Your Gun Broke").getContents()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED), true);
                         }
                     }
                     event.setCanceled(true);
                 }
             } else
                 event.getShooter().getMainHandItem().getOrCreateTag().putInt(DURABILITY, Config.getDurability(event.getShooter().getMainHandItem().getOrCreateTag().getString("GunId")));
         }
         if(event.getLogicalSide().isClient()) {
             if(event.getGunItemStack().getOrCreateTag().getBoolean(JAM) || event.getGunItemStack().getOrCreateTag().getInt(DURABILITY) <=0) {
                 event.setCanceled(true);
             }
         }
    }

    public static void handleUnjammingForNonPlayers(LivingEntity livingEntity) {
        if(livingEntity.getPersistentData().contains(OTHERHANDLE) && livingEntity.getPersistentData().getBoolean(OTHERHANDLE)) return;
        int jamTime = 100;
        livingEntity.getPersistentData().putBoolean(OTHERHANDLE, true);
        if(livingEntity.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
            for (DurabilityModifier modifier : Config.DURABILITY_LIST.get()) {
                if (livingEntity.getMainHandItem().getOrCreateTag().getString("GunId").equals(modifier.gunId())) {
                    jamTime = modifier.jamTime();
                }
            }
        }
        if (livingEntity.getMainHandItem().getOrCreateTag().getBoolean(JAM)) {
            Gundurability.queueServerWork(new Work<>(livingEntity, jamTime) {
                public boolean cancel = false;
                public void tick() {
                    if(!(entity.getMainHandItem().getItem() instanceof ModernKineticGunItem)) cancel = true;
                }
                public void run() {
                    if (entity.getMainHandItem().getItem() instanceof ModernKineticGunItem && !this.cancel) {
                        entity.getMainHandItem().getOrCreateTag().putBoolean(JAM, false);
                        entity.getPersistentData().putBoolean(OTHERHANDLE, false);
                    }
                }
            });
        }
    }
}