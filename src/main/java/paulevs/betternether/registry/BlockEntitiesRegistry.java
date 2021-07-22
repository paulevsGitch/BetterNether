package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNBarrelBlockEntity;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.blockentities.BNChestBlockEntity;
import paulevs.betternether.blockentities.BNSignBlockEntity;
import paulevs.betternether.blockentities.BlockEntityChestOfDrawers;
import paulevs.betternether.blockentities.BlockEntityForge;
import paulevs.betternether.blockentities.BlockEntityFurnace;
import paulevs.betternether.blocks.BNBarrel;
import paulevs.betternether.blocks.BNChest;
import paulevs.betternether.blocks.BNSign;
import paulevs.betternether.blocks.BlockNetherFurnace;

public class BlockEntitiesRegistry {
	public static final BlockEntityType<BlockEntityForge> CINCINNASITE_FORGE = BlockEntityType.Builder.of(BlockEntityForge::new, NetherBlocks.CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<BlockEntityFurnace> NETHERRACK_FURNACE = BlockEntityType.Builder.of(BlockEntityFurnace::new, getFurnaces()).build(null);
	public static final BlockEntityType<BlockEntityChestOfDrawers> CHEST_OF_DRAWERS = BlockEntityType.Builder.of(BlockEntityChestOfDrawers::new, NetherBlocks.CHEST_OF_DRAWERS).build(null);
	public static final BlockEntityType<BNBrewingStandBlockEntity> NETHER_BREWING_STAND = BlockEntityType.Builder.of(BNBrewingStandBlockEntity::new, NetherBlocks.NETHER_BREWING_STAND).build(null);
	public static final BlockEntityType<BNChestBlockEntity> CHEST = BlockEntityType.Builder.of(BNChestBlockEntity::new, getChests()).build(null);
	public static final BlockEntityType<BNBarrelBlockEntity> BARREL = BlockEntityType.Builder.of(BNBarrelBlockEntity::new, getBarrels()).build(null);
	public static final BlockEntityType<BNSignBlockEntity> SIGN = BlockEntityType.Builder.of(BNSignBlockEntity::new, getSigns()).build(null);

	public static void register() {
		RegisterBlockEntity("forge", CINCINNASITE_FORGE);
		RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
		RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
		RegisterBlockEntity("nether_brewing_stand", NETHER_BREWING_STAND);
		RegisterBlockEntity("chest", CHEST);
		RegisterBlockEntity("barrel", BARREL);
		RegisterBlockEntity("sign", SIGN);
	}

	public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(BetterNether.MOD_ID, name), type);
	}

	private static Block[] getChests() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BNChest)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getBarrels() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BNBarrel)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getSigns() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BNSign)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getFurnaces() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BlockNetherFurnace)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}
}
