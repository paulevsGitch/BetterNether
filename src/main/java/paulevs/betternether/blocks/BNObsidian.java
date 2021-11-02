package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import ru.bclib.blocks.BaseBlock;

public class BNObsidian extends BaseBlock {
	final Block transformsTo;
	public BNObsidian() { this(null); }
	public BNObsidian(Block transformsTo) {
		this(FabricBlockSettings.copyOf(Blocks.OBSIDIAN), transformsTo);
	}
	
	protected BNObsidian(Properties settings, Block transformsTo) {
		super(settings);
		this.transformsTo = transformsTo;
	
	}
	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos rodPos, boolean bl) {
		if (transformsTo !=null) {
			final BlockState updaterState = level.getBlockState(rodPos);
			if (updaterState.is(Blocks.LIGHTNING_ROD)) {
				if (updaterState.getValue(LightningRodBlock.POWERED)) {
					BNObsidian.onLightningUpdate(level, blockPos, transformsTo);
				}
			}
		}
	}
	
	public static void onLightningUpdate(Level level, BlockPos blockPos, Block transformsTo) {
		BlocksHelper.setWithoutUpdate(level, blockPos, transformsTo.defaultBlockState());
	}
}
