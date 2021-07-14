package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockGeyser extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 4, 15);

	public BlockGeyser() {
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).luminance(10).nonOpaque());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
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
			world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
		if (random.nextDouble() < 0.1D) {
			world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
		if (random.nextDouble() < 0.1D) {
			world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.hurt(DamageSource.IN_FIRE, 3F);
			entity.setSecondsOnFire(1);
		}

		super.stepOn(world, pos, state, entity);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}
}
