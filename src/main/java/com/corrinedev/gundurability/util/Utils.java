package com.corrinedev.gundurability.util;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityModifier;
import com.ibm.icu.impl.Pair;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;

@Deprecated
public final class Utils {
    public static HashMap<String, Integer> DURABILITIES = new HashMap<>();

    @Deprecated
    public static int getMaxDurabilityFromStack(ItemStack stack) {
        if(DURABILITIES.containsKey(stack.getOrCreateTag().getString("GunId"))) {
            return DURABILITIES.get(stack.getOrCreateTag().getString("GunId"));
        } else {
            return Config.MAXDURABILITY.get();
        }
    }
    @Deprecated
    public static HashMap<String, Integer> parseDurability(List<DurabilityModifier> modifiers) {
        HashMap<String, Integer> pairs = new HashMap<>();

        for (DurabilityModifier modifier : modifiers) {
            pairs.put(modifier.gunId(), modifier.maxDurability());
        }
        return pairs;
    }
}
