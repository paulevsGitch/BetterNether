package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockInkBush extends BlockCommonPlant {
	public BlockInkBush() {
		super(MapColor.BLACK);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.INK_BUSH_SEED);
	}
}
