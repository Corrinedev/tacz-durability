package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(value = ModernKineticGunItem.class, remap = false,priority = 1100)
public abstract class AccuracyMixin {

    /**
     * @author Corrindev
     * @reason Apply Inaccuracy based on Durability
     */
    //@Inject(
    //        method = {"doSpawnBulletEntity"},
    //        target = {@Desc(value="doSpawnBulletEntity",
    //                args={Level.class, LivingEntity.class, float.class, float.class, float.class, float.class, ResourceLocation.class, ResourceLocation.class, boolean.class, BulletData.class})},
    //        at = {@At("HEAD")}, cancellable = true)
    @Overwrite
    protected void doSpawnBulletEntity(Level world, LivingEntity shooter, ItemStack gunItem, float pitch, float yaw, float speed, float inaccuracy, ResourceLocation ammoId, ResourceLocation gunId, boolean tracer, GunData gunData, BulletData bulletData) {

        float inaccuracyF = inaccuracy * (((float) Math.abs(shooter.getMainHandItem().getOrCreateTag().getInt("Durability") - Config.MAXDURABILITY.get()) / Config.INACCURACYRATE.get()) + 1);

        if(Config.DEBUG.get()) {

            System.out.println(inaccuracyF);

        }

        EntityKineticBullet bullet = new EntityKineticBullet(world, shooter, gunItem, ammoId, gunId, tracer, gunData, bulletData);
        bullet.shootFromRotation(bullet, pitch, yaw, 0.0F, speed, inaccuracyF);
        world.addFreshEntity(bullet);
    }
}
