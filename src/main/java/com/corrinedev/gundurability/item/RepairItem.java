package com.corrinedev.gundurability.item;

import com.corrinedev.gundurability.config.Config;
import com.corrinedev.gundurability.config.DurabilityItemHolder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RepairItem extends Item {
    private final DurabilityItemHolder.Slots slot;

    private final float repairAmount;
    private final float max;
    private final float min;
    private Pair<String, List<String>> GUNTAG = null;
    public RepairItem(int durability, float repairPercent, float max, float min, DurabilityItemHolder.Slots slot, Pair<String, List<String>> gunTags) {
        super(new Item.Properties().durability(durability).setNoRepair());
        this.repairAmount = repairPercent;
        this.slot = slot;
        this.max=max;
        this.min=min;
        this.GUNTAG = gunTags;

    }
    public RepairItem(int durability, float repairPercent, float max, float min, DurabilityItemHolder.Slots slot) {
        this(durability,repairPercent,max,min,slot, null);
    }

    public DurabilityItemHolder.Slots getSlot() {
        return slot;
    }
    public int getRepairAmount(ItemStack stack) {
        int maxDurability = Config.getDurability(stack.getOrCreateTag().getString("GunId"));
        int percent = (int) (((float) this.repairAmount / 100f) * maxDurability);
        System.out.println(percent);
        return percent;
    }
    public boolean isBetween(ItemStack stack) {
        int maxDurability = Config.getDurability(stack.getOrCreateTag().getString("GunId"));
        if (maxDurability <= 0) {
            return false; // Avoid division issues or invalid checks
        }

        float percentMin = (this.min / 100f) * maxDurability;
        float percentMax = (this.max / 100f) * maxDurability;

        int currentDurability = maxDurability - stack.getOrCreateTag().getInt("Durability");
        System.out.println(currentDurability);
        System.out.println(percentMax);
        System.out.println(percentMin);
        return currentDurability >= percentMin && currentDurability <= percentMax;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        MutableComponent repair = MutableComponent.create(Component.translatable("tooltip.gundurability.repair").getContents()).withStyle(ChatFormatting.DARK_GRAY)
                        .append(MutableComponent.create(Component.literal(String.valueOf(this.repairAmount)).getContents()).withStyle(ChatFormatting.AQUA));
        MutableComponent max = MutableComponent.create(Component.translatable("tooltip.gundurability.max").getContents()).withStyle(ChatFormatting.DARK_GRAY)
                .append(MutableComponent.create(Component.literal(String.valueOf(this.max)).getContents()).withStyle(ChatFormatting.AQUA));
        MutableComponent min = MutableComponent.create(Component.translatable("tooltip.gundurability.min").getContents()).withStyle(ChatFormatting.DARK_GRAY)
                .append(MutableComponent.create(Component.literal(String.valueOf(this.min)).getContents()).withStyle(ChatFormatting.AQUA));

        p_41423_.add(repair);
        p_41423_.add(max);
        p_41423_.add(min);

        if(this.GUNTAG != null) {
            MutableComponent compatibleGuns = MutableComponent.create(Component.translatable("tooltip.gundurability.guns").getContents()).withStyle(ChatFormatting.DARK_GRAY)
                    .append(MutableComponent.create(Component.literal(this.GUNTAG.getFirst()).getContents()).withStyle(ChatFormatting.AQUA));
            p_41423_.add(compatibleGuns);
        }
    }
}
