package paulevs.betternether.registers;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.tileentities.TileEntityForge;
import paulevs.betternether.tileentities.TileEntityFurnace;

public class BlockEntitiesRegister
{
	public static final BlockEntityType<?> CINCINNASITE_FORGE = BlockEntityType.Builder.create(TileEntityForge::new, BlocksRegister.BLOCK_CINCINNASITE_FORGE).build(null);
	public static final BlockEntityType<?> NETHERRACK_FURNACE = BlockEntityType.Builder.create(TileEntityFurnace::new, BlocksRegister.BLOCK_NETHERRACK_FURNACE).build(null);
	
	public static void register()
	{
		RegisterBlockEntity("forge", CINCINNASITE_FORGE);
		RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
	}
	
	public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type)
	{
		Registry.register(Registry.BLOCK_ENTITY, new Identifier(BetterNether.MOD_ID, name), type);
	}
}
