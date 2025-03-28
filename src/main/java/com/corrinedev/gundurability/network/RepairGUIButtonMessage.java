
package com.corrinedev.gundurability.network;

import com.corrinedev.gundurability.Gundurability;
import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.item.RepairItem;
import com.corrinedev.gundurability.world.inventory.RepairGUIMenu;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RepairGUIButtonMessage {
	private final int buttonID, x, y, z;

	public RepairGUIButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public RepairGUIButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(RepairGUIButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(RepairGUIButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		Gundurability.addNetworkMessage(RepairGUIButtonMessage.class, RepairGUIButtonMessage::buffer, RepairGUIButtonMessage::new, RepairGUIButtonMessage::handler);
	}

	public static void execute(Player entity) {
		if(entity instanceof ServerPlayer && entity.containerMenu instanceof RepairGUIMenu containerMenu) {
			int durability = Config.MAXDURABILITY.get();

			if (containerMenu.getSlot(0).getItem().getItem() instanceof ModernKineticGunItem) {
				durability = Config.getDurability(containerMenu.getSlot(0).getItem().getOrCreateTag().getString("GunId"));
			}
			double percent = (double) durability / 100;


			Slot gunSlot = containerMenu.customSlots.get(0);
			int repairAmount = 0;
			if (!gunSlot.getItem().getOrCreateTag().contains("Durability")) {
				return;
			}
			for (Slot slot : containerMenu.customSlots.values()) {
				if (slot.getItem().getItem() instanceof RepairItem repairItem) {
					if (repairItem.isBetween(gunSlot.getItem())) {
						if(repairItem.getGunIds() == null || repairItem.getGunIds().contains(gunSlot.getItem().getOrCreateTag().getString("GunId"))) {
							repairAmount += repairItem.getRepairAmount(gunSlot.getItem());
							slot.getItem().setDamageValue(slot.getItem().getDamageValue() + 1);
							if(slot.getItem().getDamageValue() >= slot.getItem().getMaxDamage()) slot.getItem().setCount(0);
						}
					}
				}
			}
			repairAmount = Mth.clamp(repairAmount + gunSlot.getItem().getOrCreateTag().getInt("Durability"), 0, Config.getDurability(gunSlot.getItem().getOrCreateTag().getString("GunId")));
			gunSlot.getItem().getOrCreateTag().putInt("Durability", repairAmount);
		}
	}
}
