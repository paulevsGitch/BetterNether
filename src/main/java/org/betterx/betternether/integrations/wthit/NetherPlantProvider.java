package org.betterx.betternether.integrations.wthit;

import org.betterx.betternether.blocks.BlockCommonPlant;

import net.minecraft.network.chat.Component;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.component.PairComponent;

import org.jetbrains.annotations.Nullable;

public enum NetherPlantProvider implements IBlockComponentProvider {

    INSTANCE;

    private static void addMaturityTooltip(ITooltip tooltip, float growthValue) {
        growthValue *= 100.0F;
        if (growthValue < 100.0F) {
            tooltip.addLine(new PairComponent(
                    Component.translatable("tooltip.waila.crop_growth"),
                    Component.literal(String.format("%.0f%%", growthValue))
            ));
        } else {
            tooltip.addLine(new PairComponent(
                    Component.translatable("tooltip.waila.crop_growth"),
                    Component.translatable("tooltip.waila.crop_mature")
            ));
        }
    }

    @Nullable
    @Override
    public ITooltipComponent getIcon(IBlockAccessor accessor, IPluginConfig config) {
        return null;
    }

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(WailaPlugin.Options.CROP_PROGRESS)) {
            if (accessor.getBlock() instanceof BlockCommonPlant crop) {
                addMaturityTooltip(
                        tooltip,
                        accessor.getBlockState().getValue(crop.getAgeProperty()) / (float) crop.getMaxAge()
                );
            }
        }
    }

}
