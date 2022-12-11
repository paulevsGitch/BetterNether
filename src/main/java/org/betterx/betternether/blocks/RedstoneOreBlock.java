package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseOreBlock;
import org.betterx.bclib.interfaces.BlockModelProvider;
import org.betterx.bclib.interfaces.TagProvider;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.util.List;
import java.util.function.ToIntFunction;

public class RedstoneOreBlock extends RedStoneOreBlock implements BlockModelProvider, TagProvider {
    private final int minCount;
    private final int maxCount;

    public RedstoneOreBlock() {
        super(
                FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
                                   .hardness(3F)
                                   .resistance(5F)
                                   .requiresTool()
                                   .sounds(SoundType.NETHERRACK)
                                   .randomTicks()
                                   .lightLevel(litBlockEmission(9)));

        this.minCount = 1;
        this.maxCount = 3;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int i) {
        return (blockState) -> {
            return (Boolean) blockState.getValue(BlockStateProperties.LIT) ? i : 0;
        };
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return BaseOreBlock.getDroppedItems(
                this,
                Items.REDSTONE,
                maxCount,
                minCount,
                Tiers.IRON.getLevel(),
                state,
                builder
        );
    }

    @Override
    public BlockModel getItemModel(ResourceLocation resourceLocation) {
        return getBlockModel(resourceLocation, defaultBlockState());
    }

    @Override
    public void addTags(List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
        blockTags.add(CommonBlockTags.NETHERRACK);
        blockTags.add(CommonBlockTags.ORES);
    }
}
