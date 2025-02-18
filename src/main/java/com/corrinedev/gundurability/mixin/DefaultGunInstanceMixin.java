package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.api.item.nbt.GunItemDataAccessor;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = GunItemDataAccessor.class, remap = false)
public interface DefaultGunInstanceMixin {
    @Overwrite
    default void setGunId(ItemStack gun, ResourceLocation gunId) {
        if(!gun.getOrCreateTag().contains("Durability")) gun.getOrCreateTag().putInt("Durability", Config.getDurability(gunId.toString()));
        CompoundTag nbt = gun.getOrCreateTag();
        if (gunId != null) {
            nbt.putString("GunId", gunId.toString());
        }
    }
}
