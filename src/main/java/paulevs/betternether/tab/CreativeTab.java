package paulevs.betternether.tab;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registers.BlocksRegister;

public class CreativeTab
{
	public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(
			new Identifier(BetterNether.MOD_ID, "items")).icon(() ->
			BlocksRegister.BLOCK_NETHER_GRASS.asItem().getStackForRender()).build();
}
