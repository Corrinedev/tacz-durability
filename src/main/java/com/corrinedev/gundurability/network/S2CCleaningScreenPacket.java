package com.corrinedev.gundurability.network;


import com.corrinedev.gundurability.repair.client.CleaningGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CCleaningScreenPacket {
    public int slot;

    public S2CCleaningScreenPacket(int slot) {
        this.slot = slot;
    }

    public S2CCleaningScreenPacket(FriendlyByteBuf buf) {
       buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(slot);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
        });
        ctx.get().setPacketHandled(true);
    }
}
