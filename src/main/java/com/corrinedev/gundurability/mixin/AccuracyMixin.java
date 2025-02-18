package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunFire;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.InaccuracyType;
import com.tacz.guns.sound.SoundManager;
import com.tacz.guns.util.CycleTaskHelper;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.LogicalSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false,priority = 1100)
public abstract class AccuracyMixin {

    @Shadow public abstract boolean reduceAmmoOnce();

    @Shadow private Supplier<Float> pitchSupplier;

    @Shadow private Supplier<Float> yawSupplier;

    @Shadow private LivingEntity shooter;

    @Shadow private ItemStack itemStack;

    @Shadow private AbstractGunItem abstractGunItem;

    @Shadow private CommonGunIndex gunIndex;

    @Shadow private ResourceLocation gunId;

    @Shadow private ResourceLocation gunDisplayId;

    /**
     * @author Corrindev
     * @reason Apply Inaccuracy based on Durability
     */

    @Overwrite
    public void shootOnce(boolean consumeAmmo) {
        GunData gunData = this.gunIndex.getGunData();
        BulletData bulletData = this.gunIndex.getBulletData();
        IGunOperator gunOperator = IGunOperator.fromLivingEntity(this.shooter);
        AttachmentCacheProperty cacheProperty = gunOperator.getCacheProperty();
        if (cacheProperty != null) {
            InaccuracyType inaccuracyType = InaccuracyType.getInaccuracyType(this.shooter);
            float inaccuracy = Math.max(0.0F, (Float)((Map)cacheProperty.getCache("inaccuracy")).get(inaccuracyType));
            if (inaccuracyType == InaccuracyType.AIM) {
                inaccuracy = Math.max(0.0F, (Float)((Map)cacheProperty.getCache("aim_inaccuracy")).get(inaccuracyType));
            }

            Pair<Integer, Boolean> silence = (Pair)cacheProperty.getCache("silence");
            int soundDistance = (Integer)silence.first();
            boolean useSilenceSound = (Boolean)silence.right();
            float speed = (Float)cacheProperty.getCache("ammo_speed");
            float processedSpeed = Mth.clamp(speed / 20.0F, 0.0F, Float.MAX_VALUE);
            int bulletAmount = Math.max(bulletData.getBulletAmount(), 1);
            FireMode fireMode = this.abstractGunItem.getFireMode(this.itemStack);
            int cycles = fireMode == FireMode.BURST ? gunData.getBurstData().getCount() : 1;
            long period = fireMode == FireMode.BURST ? gunData.getBurstShootInterval() : 1L;
            float finalInaccuracy = inaccuracy;
            CycleTaskHelper.addCycleTask(() -> {
                if (this.shooter.isDeadOrDying()) {
                    return false;
                } else if (!this.shooter.getMainHandItem().equals(this.itemStack)) {
                    return false;
                } else {
                    boolean fire = !MinecraftForge.EVENT_BUS.post(new GunFireEvent(this.shooter, this.itemStack, LogicalSide.SERVER));
                    if (fire) {
                        NetworkHandler.sendToTrackingEntity(new ServerMessageGunFire(this.shooter.getId(), this.itemStack), this.shooter);
                        if (consumeAmmo && !this.reduceAmmoOnce()) {
                            return false;
                        }

                        float pitch = this.pitchSupplier != null ? (Float)this.pitchSupplier.get() : this.shooter.getXRot();
                        float yaw = this.yawSupplier != null ? (Float)this.yawSupplier.get() : this.shooter.getYRot();
                        Level world = this.shooter.level();
                        ResourceLocation ammoId = gunData.getAmmoId();

                        for(int i = 0; i < bulletAmount; ++i) {
                            float inaccuracyF = finalInaccuracy * (((float) Math.abs(shooter.getMainHandItem().getOrCreateTag().getInt("Durability") - Config.MAXDURABILITY.get()) / Config.INACCURACYRATE.get()) + 1);

                            if(Config.DEBUG.get()) {
                                System.out.println(inaccuracyF);
                            }
                            boolean isTracer = bulletData.hasTracerAmmo() && gunOperator.nextBulletIsTracer(bulletData.getTracerCountInterval());
                            EntityKineticBullet bullet = new EntityKineticBullet(world, this.shooter, this.itemStack, ammoId, this.gunId, isTracer, gunData, bulletData);
                            bullet.shootFromRotation(bullet, pitch, yaw, 0.0F, processedSpeed, inaccuracyF);
                            world.addFreshEntity(bullet);
                        }

                        if (soundDistance > 0) {
                            String soundId = useSilenceSound ? SoundManager.SILENCE_3P_SOUND : SoundManager.SHOOT_3P_SOUND;
                            SoundManager.sendSoundToNearby(this.shooter, soundDistance, this.gunId, this.gunDisplayId, soundId, 0.8F, 0.9F + this.shooter.getRandom().nextFloat() * 0.125F);
                        }
                    }

                    return true;
                }
            }, period, cycles);
        }
    }
}
