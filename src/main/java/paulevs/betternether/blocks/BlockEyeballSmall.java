package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockEyeballSmall extends BlockEyeBase
{
	protected static final VoxelShape SHAPE = Block.createCuboidShape(4, 8, 4, 12, 16, 12);

	public BlockEyeballSmall()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.BROWN)
				.sounds(BlockSoundGroup.SLIME)
				.hardness(0.5F)
				.resistance(0.5F)
				.nonOpaque()
				.build());
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
	
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(5) == 0)
		{
			double x = pos.getX() + random.nextDouble() * 0.5 + 0.25;
			double y = pos.getY() + random.nextDouble() * 0.1 + 0.5;
			double z = pos.getZ() + random.nextDouble() * 0.5 + 0.25;
			world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
		}
	}

	public boolean canSuffocate(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}

	public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}
}
