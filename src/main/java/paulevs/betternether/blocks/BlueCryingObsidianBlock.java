package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherParticles;
import ru.bclib.interfaces.BlockModelProvider;

import java.util.Random;

public class BlueCryingObsidianBlock extends Block implements BlockModelProvider {
	
	public BlueCryingObsidianBlock() {
		super(FabricBlockSettings.copyOf(Blocks.CRYING_OBSIDIAN));
	}
	
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
		if (random.nextInt(5) == 0) {
			Direction direction = Direction.getRandom(random);
			if (direction != Direction.UP) {
				BlockPos blockPos2 = blockPos.relative(direction);
				BlockState blockState2 = level.getBlockState(blockPos2);
				if (!blockState.canOcclude() || !blockState2.isFaceSturdy(level, blockPos2, direction.getOpposite())) {
					double d = direction.getStepX() == 0 ? random.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
					double e = direction.getStepY() == 0 ? random.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
					double f = direction.getStepZ() == 0 ? random.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
					level.addParticle(NetherParticles.BLUE_DRIPPING_OBSIDIAN_TEAR, (double)blockPos.getX() + d, (double)blockPos.getY() + e, (double)blockPos.getZ() + f, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	@Override
	public BlockModel getItemModel(ResourceLocation resourceLocation) {
		return getBlockModel(resourceLocation, defaultBlockState());
	}
}
