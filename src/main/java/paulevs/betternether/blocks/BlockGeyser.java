package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

import java.util.Random;

public class BlockGeyser extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 4, 15);

	public BlockGeyser() {
		super(BlocksHelper.copySettingsOf(Blocks.NETHERRACK).nonOpaque().luminance(10));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		for (int i = 0; i < 5; i++) {
			world.addParticle(
					ParticleTypes.FLAME,
					pos.getX() + 0.4 + random.nextDouble() * 0.1,
					pos.getY() + 0.125,
					pos.getZ() + 0.4 + random.nextDouble() * 0.1,
					random.nextDouble() * 0.02 - 0.01,
					0.05D + random.nextDouble() * 0.05,
					random.nextDouble() * 0.02 - 0.01);

			world.addParticle(
					ParticleTypes.LARGE_SMOKE,
					pos.getX() + 0.4 + random.nextDouble() * 0.1,
					pos.getY() + 0.125,
					pos.getZ() + 0.4 + random.nextDouble() * 0.1,
					random.nextDouble() * 0.02 - 0.01,
					0.05D + random.nextDouble() * 0.05,
					random.nextDouble() * 0.02 - 0.01);

			world.addParticle(
					ParticleTypes.LAVA,
					pos.getX() + 0.4 + random.nextDouble() * 0.1,
					pos.getY() + 0.125 + random.nextDouble() * 0.1,
					pos.getZ() + 0.4 + random.nextDouble() * 0.1,
					random.nextDouble() * 0.02 - 0.01,
					0.05D + random.nextDouble() * 0.05,
					random.nextDouble() * 0.02 - 0.01);
		}

		if (random.nextDouble() < 0.1D) {
			world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
		if (random.nextDouble() < 0.1D) {
			world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
		if (random.nextDouble() < 0.1D) {
			world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.damage(DamageSource.IN_FIRE, 3F);
			entity.setOnFireFor(1);
		}

		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
}
