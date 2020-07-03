package paulevs.betternether.blockentities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNChestBlockEntity extends ChestBlockEntity
{
	@Environment(EnvType.CLIENT)
	private String material = "normal";
	@Environment(EnvType.CLIENT)
	private boolean update = true;
	
	public BNChestBlockEntity()
	{
		super(BlockEntitiesRegistry.CHEST);
	}
	
	@Environment(EnvType.CLIENT)
	public String getMaterial()
	{
		if (update)
		{
			material = world == null ? "normal" : Registry.BLOCK.getId(world.getBlockState(pos).getBlock()).getPath();
			update = false;
		}
		return material;
	}
}
