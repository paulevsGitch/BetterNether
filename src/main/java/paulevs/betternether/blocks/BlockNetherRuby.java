package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;

import ru.bclib.interfaces.CustomItemProvider;

public class BlockNetherRuby extends BlockBase implements CustomItemProvider {
	public BlockNetherRuby() {
		super(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK));
	}

	@Override
	public BlockItem getCustomItem(ResourceLocation blockID, FabricItemSettings settings) {
		return new BlockItem(this, settings.fireproof());
	}
}
