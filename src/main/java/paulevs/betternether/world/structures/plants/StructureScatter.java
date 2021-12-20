package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureScatter implements IStructure {
	private final Block plantBlock;
	private final Property<Integer> ageProp;
	private final int maxAge;

	public StructureScatter(Block plantBlock, Property<Integer> ageProperty, int maxAge) {
		this.plantBlock = plantBlock;
		this.ageProp = ageProperty;
		this.maxAge = maxAge;
	}

	public StructureScatter(Block plantBlock) {
		this.plantBlock = plantBlock;
		this.ageProp = null;
		this.maxAge = 0;
	}

	private boolean canPlaceAt(ServerLevelAccessor world, BlockPos pos) {
		return plantBlock.canSurvive(plantBlock.defaultBlockState(), world, pos);
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (world.isEmptyBlock(pos) && canPlaceAt(world, pos)) {
			BlockState state = plantBlock.defaultBlockState();
			int rndState = random.nextInt(2);
			for (int i = 0; i < 20; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				if (((x + z + rndState) & 1) == 0) {
					if (random.nextBoolean()) {
						x += random.nextBoolean() ? 1 : -1;
					}
					else {
						z += random.nextBoolean() ? 1 : -1;
					}
				}
				int y = pos.getY() + random.nextInt(8);
				for (int j = 0; j < 8; j++) {
					context.POS.set(x, y - j, z);
					if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
						if (ageProp != null)
							BlocksHelper.setWithoutUpdate(world, context.POS, state.setValue(ageProp, random.nextInt(maxAge)));
						else
							BlocksHelper.setWithoutUpdate(world, context.POS, state);
						break;
					}
				}
			}
		}
	}
}
