package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockNetherMycelium extends BlockBase {
	public static final BooleanProperty IS_BLUE = BooleanProperty.of("blue");

	public BlockNetherMycelium() {
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).materialColor(MapColor.GRAY).requiresTool().breakByTool(FabricToolTags.PICKAXES));
		this.setDefaultState(getStateManager().getDefaultState().with(IS_BLUE, false));
		this.setDropItself(false);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(IS_BLUE);
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		world.addParticle(ParticleTypes.MYCELIUM,
				pos.getX() + random.nextDouble(),
				pos.getY() + 1.1D,
				pos.getZ() + random.nextDouble(),
				0.0D, 0.0D, 0.0D);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool.isSuitableFor(state)) {
			if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0)
				return Collections.singletonList(new ItemStack(this.asItem()));
			else
				return Collections.singletonList(new ItemStack(Blocks.NETHERRACK));
		}
		else
			return super.getDroppedStacks(state, builder);
	}
}