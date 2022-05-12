package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class BlockGrayMold extends BlockMold {
	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);

	public BlockGrayMold() {
		super(MaterialColor.COLOR_GRAY);
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (random.nextInt(3) == 0) {
			world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble() * 0.5, pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		Vec3 vec3d = state.getOffset(view, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
}