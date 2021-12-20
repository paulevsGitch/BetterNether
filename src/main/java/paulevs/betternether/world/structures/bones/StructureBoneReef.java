package paulevs.betternether.world.structures.bones;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;
import paulevs.betternether.world.structures.StructureNBT;

public class StructureBoneReef implements IStructure {
	private static final StructureNBT[] BONES = new StructureNBT[] {
			new StructureNBT("bone_01"),
			new StructureNBT("bone_02"),
			new StructureNBT("bone_03")
	};

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (BlocksHelper.isNetherGround(world.getBlockState(pos.below())) && world.isEmptyBlock(pos.above(2)) && world.isEmptyBlock(pos.above(4))) {
			StructureNBT bone = BONES[random.nextInt(BONES.length)];
			bone.randomRM(random);
			bone.generateCentered(world, pos.below(random.nextInt(4)));
		}
	}
}
