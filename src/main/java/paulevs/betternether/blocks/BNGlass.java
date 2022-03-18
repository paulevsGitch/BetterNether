package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ru.bclib.interfaces.tools.AddMineablePickaxe;

import java.util.List;

public class BNGlass extends BlockBaseNotFull implements AddMineablePickaxe {
	public BNGlass(Block block) {
		super(FabricBlockSettings.copyOf(block)
				//TODO:1.18.2 test this
				//.breakByTool(FabricToolTags.PICKAXES)
				.requiresTool()
				.resistance(0.3F)
				.nonOpaque()
				.isSuffocating((arg1, arg2, arg3) -> {
					return false;
				})
				.isViewBlocking((arg1, arg2, arg3) -> {
					return false;
				}));
		this.setRenderLayer(BNRenderLayer.TRANSLUCENT);
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean skipRendering(BlockState state, BlockState neighbor, Direction facing) {
		return neighbor.getBlock() == this ? true : super.skipRendering(state, neighbor, facing);
	}
}