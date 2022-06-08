package org.betterx.betternether.registry;

import org.betterx.bclib.blocks.BaseBarrelBlock;
import org.betterx.bclib.blocks.BaseChestBlock;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blockentities.BNBrewingStandBlockEntity;
import org.betterx.betternether.blockentities.BlockEntityChestOfDrawers;
import org.betterx.betternether.blockentities.BlockEntityForge;
import org.betterx.betternether.blockentities.BlockEntityFurnace;
import org.betterx.betternether.blocks.BlockNetherFurnace;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;

public class BlockEntitiesRegistry {
    public static final BlockEntityType<BlockEntityForge> CINCINNASITE_FORGE = BlockEntityType.Builder.of(
            BlockEntityForge::new,
            NetherBlocks.CINCINNASITE_FORGE
    ).build(null);
    public static final BlockEntityType<BlockEntityFurnace> NETHERRACK_FURNACE = BlockEntityType.Builder.of(
            BlockEntityFurnace::new,
            getFurnaces()
    ).build(null);
    public static final BlockEntityType<BlockEntityChestOfDrawers> CHEST_OF_DRAWERS = BlockEntityType.Builder.of(
            BlockEntityChestOfDrawers::new,
            NetherBlocks.CHEST_OF_DRAWERS
    ).build(null);
    public static final BlockEntityType<BNBrewingStandBlockEntity> NETHER_BREWING_STAND = BlockEntityType.Builder.of(
            BNBrewingStandBlockEntity::new,
            NetherBlocks.NETHER_BREWING_STAND
    ).build(null);

    public static void register() {
        RegisterBlockEntity("forge", CINCINNASITE_FORGE);
        RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
        RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
        RegisterBlockEntity("nether_brewing_stand", NETHER_BREWING_STAND);
    }

    public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(BetterNether.MOD_ID, name), type);
    }

    private static Block[] getChests() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BaseChestBlock)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }

    private static Block[] getBarrels() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BaseBarrelBlock)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }

    private static Block[] getFurnaces() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BlockNetherFurnace)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }
}
