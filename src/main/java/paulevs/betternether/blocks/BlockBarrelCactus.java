package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockBarrelCactus extends BlockCactusBase implements Fertilizable
{
	private static final VoxelShape SELECTION = Block.createCuboidShape(0, 0, 0, 16, 14, 16);
	private static final VoxelShape COLLISION = Block.createCuboidShape(1, 0, 1, 15, 13, 15);
	
	public BlockBarrelCactus()
	{
		super(FabricBlockSettings.of(Material.CACTUS)
				.materialColor(MaterialColor.BLUE_TERRACOTTA)
				.sounds(BlockSoundGroup.WOOL)
				.nonOpaque()
				.hardness(0.4F));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		Vec3d vec3d = state.getOffsetPos(view, pos);
		return SELECTION.offset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		Vec3d vec3d = state.getOffsetPos(view, pos);
		return COLLISION.offset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public Block.OffsetType getOffsetType()
	{
		return Block.OffsetType.XYZ;
	}
	
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		dropStack(world, pos, new ItemStack(this.asItem()));
	}
}
