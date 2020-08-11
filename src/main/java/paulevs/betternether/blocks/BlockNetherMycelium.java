package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetherMycelium extends BlockBase
{
public static final BooleanProperty IS_BLUE = BooleanProperty.of("blue");
	
	public BlockNetherMycelium()
	{
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).materialColor(MaterialColor.GRAY).requiresTool());
		this.setDefaultState(getStateManager().getDefaultState().with(IS_BLUE, false));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(IS_BLUE);
	}
	
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		super.randomDisplayTick(state, world, pos, random);
		world.addParticle(ParticleTypes.MYCELIUM,
				pos.getX() + random.nextDouble(),
				pos.getY() + 1.1D,
				pos.getZ() + random.nextDouble(),
				0.0D, 0.0D, 0.0D);
	}
}