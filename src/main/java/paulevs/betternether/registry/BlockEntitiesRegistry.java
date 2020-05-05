package paulevs.betternether.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNBrewingStandBlockEntity;
import paulevs.betternether.blockentities.BlockEntityChestOfDrawers;
import paulevs.betternether.blockentities.BlockEntityForge;
import paulevs.betternether.blockentities.BlockEntityFurnace;
import paulevs.betternether.blockentities.NetherSignBlockEntity;

public class BlockEntitiesRegistry
{
	public static final BlockEntityType<? extends BlockEntity> CINCINNASITE_FORGE = BlockEntityType.Builder.create(BlockEntityForge::new, BlocksRegistry.CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<? extends BlockEntity> NETHERRACK_FURNACE = BlockEntityType.Builder.create(BlockEntityFurnace::new, BlocksRegistry.NETHERRACK_FURNACE).build(null);
	public static final BlockEntityType<? extends BlockEntity> CHEST_OF_DRAWERS = BlockEntityType.Builder.create(BlockEntityChestOfDrawers::new, BlocksRegistry.CHEST_OF_DRAWERS).build(null);
	public static final BlockEntityType<? extends BlockEntity> NETHER_BREWING_STAND = BlockEntityType.Builder.create(BNBrewingStandBlockEntity::new, BlocksRegistry.NETHER_BREWING_STAND).build(null);
	public static final BlockEntityType<NetherSignBlockEntity> NETHER_SIGN = BlockEntityType.Builder.create(NetherSignBlockEntity::new, BlocksRegistry.STALAGNATE_SIGN).build(null);
	
	public static void register()
	{
		RegisterBlockEntity("forge", CINCINNASITE_FORGE);
		RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
		RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
		RegisterBlockEntity("nether_brewing_stand", NETHER_BREWING_STAND);
		RegisterBlockEntity("nether_sign", NETHER_SIGN);
	}
	
	public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type)
	{
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), type);
	}
}