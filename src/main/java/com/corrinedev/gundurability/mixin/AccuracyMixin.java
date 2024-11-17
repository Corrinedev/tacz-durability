package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ModernKineticGunItem.class, remap = false)
public class AccuracyMixin {

    /**
     * @author Corrindev
     * @reason Apply Inaccuracy based on Durability
     */

    @Overwrite
    protected void doSpawnBulletEntity(Level world, LivingEntity shooter, float pitch, float yaw, float speed, float inaccuracy, ResourceLocation ammoId, ResourceLocation gunId, boolean tracer, BulletData bulletData) {
        EntityKineticBullet bullet = new EntityKineticBullet(world, shooter, ammoId, gunId, tracer, bulletData);
        bullet.shootFromRotation(bullet, pitch, yaw, 0.0F, speed, Math.min(inaccuracy, inaccuracy * (((float) Math.abs(shooter.getMainHandItem().getOrCreateTag().getInt("Durability") - Config.MAXDURABILITY.get()) / Config.INACCURACYRATE.get()) + 1)));
        world.addFreshEntity(bullet);
    }
}
