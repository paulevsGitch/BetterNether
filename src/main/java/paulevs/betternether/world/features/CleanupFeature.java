package paulevs.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBiomes;
import ru.bclib.world.features.DefaultFeature;

import java.util.ArrayList;
import java.util.List;

public class CleanupFeature extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		if (!NetherBiomes.useLegacyGeneration) return false;
		
		final int MAX_HEIGHT = featurePlaceContext.chunkGenerator().getGenDepth();
		final MutableBlockPos popPos = new MutableBlockPos();
		final BlockPos worldPos = featurePlaceContext.origin();
		final WorldGenLevel level = featurePlaceContext.level();
		
		final int sx = (worldPos.getX() >> 4) << 4;
		final int sz = (worldPos.getZ() >> 4) << 4;
		
		final List<BlockPos> pos = new ArrayList<BlockPos>();
		
		BlockPos up;
		BlockPos down;
		BlockPos north;
		BlockPos south;
		BlockPos east;
		BlockPos west;
		for (int y = 32; y < MAX_HEIGHT-18; y++) {
			popPos.setY(y);
			for (int x = 0; x < 16; x++) {
				popPos.setX(x | sx);
				for (int z = 0; z < 16; z++) {
					popPos.setZ(z | sz);
					if (canReplace(level, popPos)) {
						up = popPos.above();
						down = popPos.below();
						north = popPos.north();
						south = popPos.south();
						east = popPos.east();
						west = popPos.west();
						if (level.isEmptyBlock(north) && level.isEmptyBlock(south))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(east) && level.isEmptyBlock(west))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(up) && level.isEmptyBlock(down))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(popPos.north().east().below()) && level.isEmptyBlock(popPos.south().west().above()))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(popPos.south().east().below()) && level.isEmptyBlock(popPos.north().west().above()))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(popPos.north().west().below()) && level.isEmptyBlock(popPos.south().east().above()))
							pos.add(new BlockPos(popPos));
						else if (level.isEmptyBlock(popPos.south().west().below()) && level.isEmptyBlock(popPos.north().east().above()))
							pos.add(new BlockPos(popPos));
					}
				}
			}
		}
		
		for (BlockPos p : pos) {
			BlocksHelper.setWithoutUpdate(level, p, AIR);
			up = p.above();
			BlockState state = level.getBlockState(up);
			if (!state.getBlock().canSurvive(state, level, up))
				BlocksHelper.setWithoutUpdate(level, up, AIR);
		}
		
		return true;
	}
	
	private static boolean canReplace(WorldGenLevel world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isNetherGround(state) || state.getBlock() == Blocks.GRAVEL;
	}
}
