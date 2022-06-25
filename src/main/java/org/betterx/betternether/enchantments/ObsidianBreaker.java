package org.betterx.betternether.enchantments;

import org.betterx.bclib.api.v2.DiggerItemSpeed;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEnchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ObsidianBreaker extends Enchantment {
    public ObsidianBreaker() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    public static float getDestroySpeedMultiplier(BlockState state, float baseSpeed, float level) {
        if ((state.is(org.betterx.worlds.together.tag.v3.CommonBlockTags.NETHER_STONES)
                || state.is(org.betterx.worlds.together.tag.v3.CommonBlockTags.NETHER_ORES)
                || state.is(org.betterx.worlds.together.tag.v3.CommonBlockTags.NETHER_PORTAL_FRAME)
                || state.is(org.betterx.worlds.together.tag.v3.CommonBlockTags.IS_OBSIDIAN)
                || state.is(Blocks.OBSIDIAN)
                || state.is(Blocks.CRYING_OBSIDIAN)
                || state.is(NetherBlocks.BLUE_CRYING_OBSIDIAN)
                || state.is(NetherBlocks.WEEPING_OBSIDIAN)
                || state.is(NetherBlocks.BLUE_WEEPING_OBSIDIAN))) {
            float speed = baseSpeed * level * 6;
            return speed;
        }
        return baseSpeed;
    }

    private static Optional<Float> calculateSpeed(
            ItemStack stack,
            BlockState state,
            float initialSpeed,
            float currentSpeed
    ) {
        if (stack != null) {
            final int obsidianLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    NetherEnchantments.OBSIDIAN_BREAKER,
                    stack
            );
            if (obsidianLevel > 0) {
                return Optional.of(ObsidianBreaker.getDestroySpeedMultiplier(state, currentSpeed, obsidianLevel));
            }
        }
        return Optional.empty();
    }

    public static void register() {
        DiggerItemSpeed.addModifier(ObsidianBreaker::calculateSpeed);
    }
}
