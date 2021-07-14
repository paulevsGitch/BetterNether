package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.structures.StructureType;

public class FloodedDeltas extends NetherBiome {
	private static final MutableBlockPos POS = new MutableBlockPos();

	public FloodedDeltas(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(104, 95, 112)
				.setLoop(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
				.setAdditions(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD)
				.setMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS)
				.setStalactites(false)
				.setParticleConfig(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.12F)));

		addStructure("blackstone_stalactite", STALACTITE_BLACKSTONE, StructureType.FLOOR, 0.2F, true);
		addStructure("stalactite_stalactite", STALACTITE_BASALT, StructureType.FLOOR, 0.2F, true);

		addStructure("blackstone_stalagmite", STALAGMITE_BLACKSTONE, StructureType.CEIL, 0.1F, true);
		addStructure("basalt_stalagmite", STALAGMITE_BASALT, StructureType.CEIL, 0.1F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		POS.set(pos);
		int d = MHelper.randRange(2, 4, random);
		BlockState state = isLavaValid(world, pos) ? Blocks.LAVA.defaultBlockState() : (random.nextInt(16) > 0 ? Blocks.BASALT.defaultBlockState() : Blocks.AIR.defaultBlockState());
		BlocksHelper.setWithoutUpdate(world, POS, state);
		if (state.getBlock() == Blocks.LAVA)
			world.getChunk(pos.getX() >> 4, pos.getZ() >> 4).markPosForPostprocessing(POS.set(pos.getX() & 15, pos.getY(), pos.getZ() & 15));
		POS.set(pos);
		for (int h = 1; h < d; h++) {
			POS.setY(pos.getY() - h);
			if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
				BlocksHelper.setWithoutUpdate(world, POS, Blocks.BASALT.defaultBlockState());
			else
				break;
		}
	}

	protected boolean isLavaValid(LevelAccessor world, BlockPos pos) {
		return validWall(world, pos.below()) &&
				validWall(world, pos.north()) &&
				validWall(world, pos.south()) &&
				validWall(world, pos.east()) &&
				validWall(world, pos.west());
	}

	protected boolean validWall(LevelAccessor world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isLava(state) || state.isCollisionShapeFullBlock(world, pos);
	}
}
