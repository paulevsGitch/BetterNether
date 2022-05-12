package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockFeatherFern extends BlockCommonPlant {
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);

	public BlockFeatherFern() {
		super(MaterialColor.COLOR_LIGHT_BLUE, p->p.offsetType(Block.OffsetType.XZ));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		Vec3 vec3d = state.getOffset(view, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}
}
