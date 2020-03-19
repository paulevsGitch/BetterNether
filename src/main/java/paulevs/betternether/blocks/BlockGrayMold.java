package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockGrayMold extends BlockMold 
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
	
	public BlockGrayMold()
	{
		super(MaterialColor.GRAY);
	}
	
	@Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if (random.nextInt(3) == 0)
        {
            world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble() * 0.5, pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		Vec3d vec3d = state.method_26226(view, pos);
		return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
	}
}