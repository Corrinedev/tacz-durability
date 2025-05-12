package com.corrinedev.gundurability.mixin;

import com.corrinedev.gundurability.config.Config;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Supplier;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false, priority = 1100)
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
     * @return
     * @author Corrindev
     * @reason Apply Inaccuracy based on Durability
     */

    @ModifyVariable(method = "shootOnce", at = @At("STORE"), name = "inaccuracy", ordinal = 0)
    public float shootOnce(float inaccuracy) {
        float inaccuracyF =
                inaccuracy
                    *
                (((float)
                Math.abs(
                        shooter.getMainHandItem().getOrCreateTag().getInt("Durability")
                            -
                        Config.getDurability(shooter.getMainHandItem().getOrCreateTag().getString("GunId")))
                            /
                        Config.INACCURACYRATE.get()) + 1
        );

        if(Config.DEBUG.get()) {
            System.out.println(inaccuracyF);
        }
        return inaccuracyF;
    }
}
