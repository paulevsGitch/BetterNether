package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.blockentities.BlockEntityChestOfDrawers;
import paulevs.betternether.blockentities.BlockEntityForge;
import paulevs.betternether.blockentities.BlockEntityFurnace;
import paulevs.betternether.blocks.BlockNetherFurnace;
import ru.bclib.blocks.BaseBarrelBlock;
import ru.bclib.blocks.BaseChestBlock;

public class BlockEntitiesRegistry {
	public static final BlockEntityType<BlockEntityForge> CINCINNASITE_FORGE = BlockEntityType.Builder.of(BlockEntityForge::new, NetherBlocks.CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<BlockEntityFurnace> NETHERRACK_FURNACE = BlockEntityType.Builder.of(BlockEntityFurnace::new, getFurnaces()).build(null);
	public static final BlockEntityType<BlockEntityChestOfDrawers> CHEST_OF_DRAWERS = BlockEntityType.Builder.of(BlockEntityChestOfDrawers::new, NetherBlocks.CHEST_OF_DRAWERS).build(null);
	public static final BlockEntityType<BNBrewingStandBlockEntity> NETHER_BREWING_STAND = BlockEntityType.Builder.of(BNBrewingStandBlockEntity::new, NetherBlocks.NETHER_BREWING_STAND).build(null);
	
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
		return result.toArray(new Block[] {});
	}

	private static Block[] getBarrels() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getModBlocks().forEach((block) -> {
			if (block instanceof BaseBarrelBlock)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getFurnaces() {
		List<Block> result = new ArrayList<Block>();
		NetherBlocks.getModBlocks().forEach((block) -> {
			if (block instanceof BlockNetherFurnace)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}
}
