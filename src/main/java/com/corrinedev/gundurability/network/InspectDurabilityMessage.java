
package com.corrinedev.gundurability.network;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityModifier;
import com.corrinedev.gundurability.util.Work;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InspectDurabilityMessage {

	public InspectDurabilityMessage() {
	}

	public InspectDurabilityMessage(FriendlyByteBuf buffer) {
	}

	public static void buffer(InspectDurabilityMessage message, FriendlyByteBuf buffer) {
	}

	public static void handler(InspectDurabilityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender());
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(ServerPlayer entity) {
		int jamTime = 100;

		if(entity.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
			for (DurabilityModifier modifier : Config.DURABILITY_LIST.get()) {
				if (entity.getMainHandItem().getOrCreateTag().getString("GunId").equals(modifier.gunId())) {
					jamTime = modifier.jamTime();
				}
			}
		}
		if (entity.getMainHandItem().getOrCreateTag().getBoolean("Jammed")) {
			Gundurability.queueServerWork(new Work<>(entity, jamTime) {
				public boolean cancel = false;
				public void tick() {
					if(!(entity.getMainHandItem().getItem() instanceof ModernKineticGunItem)) cancel = true;
				}
				public void run() {
					if (entity.getMainHandItem().getItem() instanceof ModernKineticGunItem && !this.cancel) {
						entity.getMainHandItem().getOrCreateTag().putBoolean("Jammed", false);
						entity.displayClientMessage(MutableComponent.create(Component.literal("Jam Cleared!").getContents()).withStyle(ChatFormatting.YELLOW), true);
					}
				}
			});
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Gundurability.addNetworkMessage(InspectDurabilityMessage.class, InspectDurabilityMessage::buffer, InspectDurabilityMessage::new, InspectDurabilityMessage::handler);
	}
}
