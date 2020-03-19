package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;

public class BlockStatueRespawner extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
	private static final DustParticleEffect EFFECT = new DustParticleEffect(1, 0, 0, 1.0F);
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty TOP = BooleanProperty.of("top");
	
	public BlockStatueRespawner()
	{
		super(FabricBlockSettings.copy(BlocksRegister.CINCINNASITE_BLOCK)
				.nonOpaque()
				.lightLevel(15)
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TOP, false));
		this.setDropItself(false);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING, TOP);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		/*if (player.getMainHandStack().getItem() == Items.GLOWSTONE_DUST)
		{
			float y = state.get(TOP) ? 0.4F : 1.4F;
			if (!player.isCreative())
				player.getMainHandStack().decrement(1);
			for (int i = 0; i < 50; i++)
				world.addParticle(EFFECT,
						pos.getX() + world.random.nextFloat(),
						pos.getY() + y + world.random.nextFloat() * 0.2,
						pos.getZ() + world.random.nextFloat(), 0, 0, 0);
			player.addMessage(new TranslatableText("message.spawn_set", new Object[0]), true);
			player.setPlayerSpawn(pos.offset(state.get(FACING)), true, false);
			if (!world.isClient)
			{
				IDimensionable dimensionable = (IDimensionable) player;
				dimensionable.setSpawnDimension(player.dimension);
				dimensionable.setUsedStatue(true);
			}
			player.playSound(SoundEvents.ITEM_TOTEM_USE, 0.7F, 1.0F);
			return ActionResult.SUCCESS;
		}
		else
			player.addMessage(new TranslatableText("message.spawn_help", new Object[0]), true);*/
		return ActionResult.FAIL;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		if (state.get(TOP))
			return true;
		BlockState up = world.getBlockState(pos.up());
		return up.isAir() || (up.getBlock() == this && up.get(TOP));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
	{
		if (!world.isClient())
			BlocksHelper.setWithoutUpdate((ServerWorld) world, pos.up(), state.with(TOP, true));
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		if (state.get(TOP))
		{
			return world.getBlockState(pos.down()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
		else
		{
			return world.getBlockState(pos.up()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}
}
