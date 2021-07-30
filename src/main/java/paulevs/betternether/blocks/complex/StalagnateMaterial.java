package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import ru.bclib.complexmaterials.entry.BlockEntry;

public class StalagnateMaterial extends NetherWoodenMaterial{
	
	public StalagnateMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		super(name, woodColor, planksColor);
	}
	
	@Override
	protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		super.initDefault(blockSettings, itemSettings);
		
		addBlockEntry(new BlockEntry(BLOCK_OPTIONAL_TRUNK, false, (complexMaterial, settings) -> {
			return new BlockStalagnate();
		}));
		
		addBlockEntry(new BlockEntry(BLOCK_OPTIONAL_SEED, true, (complexMaterial, settings) -> {
			return new BlockStalagnateSeed();
		}));
		
		addBlockEntry(new BlockEntry(BLOCK_BOWL, false, (complexMaterial, settings) -> {
			return new BlockStalagnateBowl(getBlock(BLOCK_OPTIONAL_TRUNK));
		}));
	}
}
