package com.corrinedev.gundurability.network;


import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.repair.OpenScreenClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class S2CCleaningScreenPacket {
    public int slot;
    public int rs;
    public ItemStack stack;

    public S2CCleaningScreenPacket(int slot, int rs, ItemStack stack) {
        this.slot = slot;
        this.rs = rs;
        this.stack = stack;
    }

    public S2CCleaningScreenPacket(FriendlyByteBuf buf) {
       buf.readInt();
       buf.readInt();
       buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(slot);
        buf.writeInt(rs);
        buf.writeItem(stack);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> OpenScreenClient.openScreen(stack, slot, rs));
        });
        ctx.get().setPacketHandled(true);
    }
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Gundurability.addNetworkMessage(S2CCleaningScreenPacket.class, S2CCleaningScreenPacket::toBytes, S2CCleaningScreenPacket::new, S2CCleaningScreenPacket::handle);
    }
}
