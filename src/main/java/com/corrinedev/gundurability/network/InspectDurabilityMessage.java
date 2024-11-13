
package com.corrinedev.gundurability.network;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.init.GundurabilityModAttributes;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InspectDurabilityMessage {
	int type, pressedms;

	public InspectDurabilityMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public InspectDurabilityMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(InspectDurabilityMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(InspectDurabilityMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender(), message.type, message.pressedms);
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {
			if(entity.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
				if(entity.getMainHandItem().getOrCreateTag().getBoolean("Jammed")) {
					Gundurability.queueServerWork((int) entity.getAttribute(GundurabilityModAttributes.HANDLING.get()).getValue() * 20, () -> {
						if(entity.getMainHandItem().getItem() instanceof ModernKineticGunItem) {
							entity.getMainHandItem().getOrCreateTag().putBoolean("Jammed", false);
							entity.displayClientMessage(MutableComponent.create(Component.literal("Jam Cleared!").getContents()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.YELLOW), true);
						}
					});
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Gundurability.addNetworkMessage(InspectDurabilityMessage.class, InspectDurabilityMessage::buffer, InspectDurabilityMessage::new, InspectDurabilityMessage::handler);
	}
}
