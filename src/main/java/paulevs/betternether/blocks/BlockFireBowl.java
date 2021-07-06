package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.ToIntFunction;

public class BlockFireBowl extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);
	public static final BooleanProperty FIRE = BlockProperties.FIRE;

	public BlockFireBowl(Block source) {
		super(FabricBlockSettings.copy(source).nonOpaque().luminance(getLuminance()));
		this.setDefaultState(getStateManager().getDefaultState().with(FIRE, false));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	protected static ToIntFunction<BlockState> getLuminance() {
		return (state) -> {
			return state.get(FIRE) ? 15 : 0;
		};
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FIRE);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (hit.getSide() == Direction.UP) {
			if (player.getMainHandStack().getItem() == Items.FLINT_AND_STEEL && !state.get(FIRE)) {
				world.setBlockState(pos, state.with(FIRE, true));
				if (!player.isCreative() && !world.isClient)
					player.getMainHandStack().damage(1, world.random, (ServerPlayerEntity) player);
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
				return ActionResult.SUCCESS;
			}
			else if (player.getMainHandStack().isEmpty() && state.get(FIRE)) {
				world.setBlockState(pos, state.with(FIRE, false));
				world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isFireImmune() && entity instanceof LivingEntity && world.getBlockState(pos).get(FIRE)) {
			entity.damage(DamageSource.HOT_FLOOR, 1.0F);
		}
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(FIRE)) {
			if (random.nextInt(24) == 0)
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
			if (random.nextInt(4) == 0)
				world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + random.nextDouble(), pos.getY() + 0.75, pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}