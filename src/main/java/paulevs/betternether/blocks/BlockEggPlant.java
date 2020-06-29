package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.EntityRegistry;

public class BlockEggPlant extends BlockCommonPlant
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
	public static final BooleanProperty DESTRUCTED = BooleanProperty.of("destructed");
	
	private boolean enableModDamage = true;
	private boolean enablePlayerDamage = true;
	
	public BlockEggPlant()
	{
		super(MaterialColor.WHITE_TERRACOTTA);
		enableModDamage = Config.getBoolean("egg_plant", "mob_damage", true);
		enablePlayerDamage = Config.getBoolean("egg_plant", "player_damage", true);
		this.setDefaultState(getStateManager().getDefaultState().with(DESTRUCTED, false));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		super.appendProperties(stateManager);
		stateManager.add(DESTRUCTED);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (!state.get(DESTRUCTED))
			world.addParticle(
					ParticleTypes.ENTITY_EFFECT,
					pos.getX() + random.nextDouble(),
					pos.getY() + 0.4,
					pos.getZ() + random.nextDouble(),
					0.46, 0.28, 0.55);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if (!state.get(DESTRUCTED))
		{
			if (enableModDamage && entity instanceof LivingEntity && !((LivingEntity) entity).hasStatusEffect(StatusEffects.POISON))
			{
				if (!EntityRegistry.isNetherEntity(entity))
					((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3));
			}
			else if (enablePlayerDamage && entity instanceof PlayerEntity && !((PlayerEntity) entity).hasStatusEffect(StatusEffects.POISON))
				((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 3));
			
			double px = pos.getX() + 0.5;
			double py = pos.getY() + 0.125;
			double pz = pos.getZ() + 0.5;
			if (world.isClient)
			{
				world.playSound(px, py, pz, BlockSoundGroup.WART_BLOCK.getBreakSound(), SoundCategory.BLOCKS, 1, 1, false);
				BlockStateParticleEffect effect = new BlockStateParticleEffect(ParticleTypes.BLOCK, state);
				Random random = world.random;
				for (int i = 0; i < 24; i++)
					world.addParticle(effect,
							px + random.nextGaussian() * 0.2,
							py + random.nextGaussian() * 0.2,
							pz + random.nextGaussian() * 0.2,
							random.nextGaussian(),
							random.nextGaussian(),
							random.nextGaussian());
			}
			
			world.setBlockState(pos, state.with(DESTRUCTED, true));
		}
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (builder.get(LootContextParameters.TOOL).getItem() instanceof ShearsItem)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}
	
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		if (state.get(DESTRUCTED))
			world.setBlockState(pos, this.getDefaultState());
	}
}
