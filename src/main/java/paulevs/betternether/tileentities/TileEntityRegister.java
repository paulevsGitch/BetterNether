package paulevs.betternether.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegister
{
	public static void register()
	{
		registerTE("cincinnasite_forge", TileEntityForge.class);
		registerTE("netherrack_furnace", TileEntityNetherrackFurnace.class);
		registerTE("universal_chest", TileEntityChestUniversal.class);
	}
	
	private static void registerTE(String name, Class<? extends TileEntity> te)
	{
		GameRegistry.registerTileEntity(te, new ResourceLocation("betternether", name));
	}
}
