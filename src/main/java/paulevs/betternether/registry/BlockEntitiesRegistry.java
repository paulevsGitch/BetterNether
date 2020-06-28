package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.blockentities.BNChestBlockEntity;
import paulevs.betternether.blockentities.BlockEntityChestOfDrawers;
import paulevs.betternether.blockentities.BlockEntityForge;
import paulevs.betternether.blockentities.BlockEntityFurnace;
import paulevs.betternether.blocks.BNChest;

public class BlockEntitiesRegistry
{
	public static final BlockEntityType<?> CINCINNASITE_FORGE = BlockEntityType.Builder.create(BlockEntityForge::new, BlocksRegistry.CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<?> NETHERRACK_FURNACE = BlockEntityType.Builder.create(BlockEntityFurnace::new, BlocksRegistry.NETHERRACK_FURNACE).build(null);
	public static final BlockEntityType<?> CHEST_OF_DRAWERS = BlockEntityType.Builder.create(BlockEntityChestOfDrawers::new, BlocksRegistry.CHEST_OF_DRAWERS).build(null);
	public static final BlockEntityType<?> NETHER_BREWING_STAND = BlockEntityType.Builder.create(BNBrewingStandBlockEntity::new, BlocksRegistry.NETHER_BREWING_STAND).build(null);
	public static final BlockEntityType<BNChestBlockEntity> CHEST = BlockEntityType.Builder.create(BNChestBlockEntity::new, getChests()).build(null);
	
	public static void register()
	{
		RegisterBlockEntity("forge", CINCINNASITE_FORGE);
		RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
		RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
		RegisterBlockEntity("nether_brewing_stand", NETHER_BREWING_STAND);
		RegisterBlockEntity("chest", CHEST);
	}
	
	public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type)
	{
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), type);
	}
	
	private static Block[] getChests()
	{
		List<Block> result = new ArrayList<Block>();
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new Identifier(BetterNether.MOD_ID, name));
			if (block instanceof BNChest)
				result.add(block);
		});
		for (Block b: result)
			System.out.println(b);
		return result.toArray(new Block[] {});
	}
}
