package com.corrinedev.gundurability.repair;

import com.corrinedev.gundurability.repair.client.CleaningGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

public class OpenScreenClient {
    public static DistExecutor.SafeRunnable openScreen(ItemStack stack, int slot, int repairItemSwitch ) {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                Minecraft.getInstance().setScreen(new CleaningGui(stack, slot, repairItemSwitch));
            }
        };
    }
}
