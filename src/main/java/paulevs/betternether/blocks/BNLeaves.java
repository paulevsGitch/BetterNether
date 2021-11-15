package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import ru.bclib.blocks.BaseLeavesBlock;

import java.util.function.Consumer;

public class BNLeaves extends BaseLeavesBlock {
	public final static Material NETHER_LEAVES = new FabricMaterialBuilder(MaterialColor.PLANT).lightPassesThrough().destroyedByPiston().build();
	
	public BNLeaves(Block sapling, MaterialColor color) {
		super(sapling, color, (settings)-> {
			AbstractBlockSettingsAccessor accessor = (AbstractBlockSettingsAccessor)settings;
			accessor.setMaterial(NETHER_LEAVES);
		});
	}
	
	public BNLeaves(Block sapling, MaterialColor color, Consumer<FabricBlockSettings> customizeProperties) {
		super(sapling, color, (settings)-> {
			customizeProperties.accept(settings);
			AbstractBlockSettingsAccessor accessor = (AbstractBlockSettingsAccessor)settings;
			accessor.setMaterial(NETHER_LEAVES);
		});
	}
}
