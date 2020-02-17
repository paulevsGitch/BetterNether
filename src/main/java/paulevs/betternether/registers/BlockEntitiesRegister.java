package paulevs.betternether.registers;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BlockEntityChestOfDrawers;
import paulevs.betternether.blockentities.BlockEntityForge;
import paulevs.betternether.blockentities.BlockEntityFurnace;

public class BlockEntitiesRegister
{
	public static final BlockEntityType<?> CINCINNASITE_FORGE = BlockEntityType.Builder.create(BlockEntityForge::new, BlocksRegister.CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<?> NETHERRACK_FURNACE = BlockEntityType.Builder.create(BlockEntityFurnace::new, BlocksRegister.NETHERRACK_FURNACE).build(null);
	public static final BlockEntityType<?> CHEST_OF_DRAWERS = BlockEntityType.Builder.create(BlockEntityChestOfDrawers::new, BlocksRegister.CHEST_OF_DRAWERS).build(null);
	
	public static void register()
	{
		RegisterBlockEntity("forge", CINCINNASITE_FORGE);
		RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
		RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
	}
	
	public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type)
	{
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), type);
	}
}
