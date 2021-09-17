package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.blocks.BaseLeavesBlock;

public class BlockNetherSakuraLeaves extends BaseLeavesBlock {
	private static final Random RANDOM = new Random();
	private static final int COLOR = MHelper.color(251, 113, 143);

	public BlockNetherSakuraLeaves(Block sapling) {
		super(sapling, MaterialColor.COLOR_PINK, (settings)-> settings.lightLevel((state) -> {
			return 13;
		}));
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return super.getShadeBrightness(state, view, pos) * 0.5F + 0.5F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		if (random.nextInt(10) == 0) {
			BlockPos blockPos = pos.below();
			if (world.isEmptyBlock(blockPos)) {
				double x = (double) pos.getX() + random.nextDouble();
				double y = (double) pos.getY() - 0.05D;
				double z = (double) pos.getZ() + random.nextDouble();
				world.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public int getColor(BlockState state, BlockGetter world, BlockPos pos) {
		return COLOR;
	}
}