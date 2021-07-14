package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockInkBush extends BlockCommonPlant {
	public BlockInkBush() {
		super(MaterialColor.COLOR_BLACK);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.INK_BUSH_SEED);
	}
}
