package paulevs.betternether.blocks;

import java.util.List;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.registers.EntityRegister;

public class BNChair extends BlockBaseNotFull
{
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	private float height;
	
	public BNChair(Block block, int height)
	{
		super(FabricBlockSettings.copy(block).nonOpaque().build());
		this.height = (float) (height - 3F) / 16F;
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if (world.isClient)
		{
			return ActionResult.SUCCESS;
		}
		else
		{
			if (player.hasVehicle() || player.isSpectator())
				return ActionResult.FAIL;
			
			double px = pos.getX() + 0.5;
			double py = pos.getY() + height;
			double pz = pos.getZ() + 0.5;
			
			List<EntityChair> active = world.getEntities(EntityChair.class, new Box(pos), new Predicate<EntityChair>()
			{
				@Override
				public boolean test(EntityChair entity)
				{
					return entity.hasPlayerRider();
				}
			});
			if (!active.isEmpty())
				return ActionResult.FAIL;
			
			float yaw = state.get(FACING).getOpposite().asRotation();
			
			EntityChair entity = new EntityChair(EntityRegister.CHAIR, world);
			entity.setNoGravity(true);
			entity.setSilent(true);
			entity.setInvisible(true);
			entity.setPos(px, py, pz);
			entity.setYaw(yaw);
			player.startRiding(entity);
			entity.getPassengerList().add(player);
			world.spawnEntity(entity);
			player.setYaw(yaw);
			player.setHeadYaw(yaw);
			return ActionResult.SUCCESS;
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
