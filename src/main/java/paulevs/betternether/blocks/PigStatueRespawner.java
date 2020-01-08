package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.registers.BlocksRegister;

public class PigStatueRespawner extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 20, 15);
	private static final DustParticleEffect EFFECT = new DustParticleEffect(1, 0, 0, 1.0F);
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	
	public PigStatueRespawner()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE)
				.nonOpaque()
				.lightLevel(15)
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
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
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
	{
		if (player.getMainHandStack().getItem() == Items.GLOWSTONE_DUST)
		{
			if (!player.isCreative())
				player.getMainHandStack().decrement(1);
			for (int i = 0; i < 50; i++)
				world.addParticle(EFFECT,
						pos.getX() + world.random.nextFloat(),
						pos.getY() + 1.45 + world.random.nextFloat() * 0.1,
						pos.getZ() + world.random.nextFloat(), 0, 0, 0);
			player.addChatMessage(new TranslatableText("message.spawn_set", new Object[0]), true);
			player.setPlayerSpawn(pos.offset(state.get(FACING)), false, false);
			player.playSound(SoundEvents.ITEM_TOTEM_USE, 0.7F, 1.0F);
			return ActionResult.SUCCESS;
		}
		else
			player.addChatMessage(new TranslatableText("message.spawn_help", new Object[0]), true);
		return ActionResult.FAIL;
	}
}
