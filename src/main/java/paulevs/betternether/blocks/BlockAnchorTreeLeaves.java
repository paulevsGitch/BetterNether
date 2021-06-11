package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockAnchorTreeLeaves extends BlockBaseNotFull {
	private Random random = new Random();


	public BlockAnchorTreeLeaves() {
		super(Materials
            .makeLeaves(MapColor.GREEN)
            .dynamicBounds()
        );
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);

	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return false;
	}



	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && FabricToolTags.SHEARS.contains(tool.getItem()) || EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0) {
			return Lists.newArrayList(new ItemStack(this.asItem()));
		}
		else {
			return random.nextInt(5) == 0 ? Lists.newArrayList(new ItemStack(BlocksRegistry.ANCHOR_TREE_SAPLING)) : super.getDroppedStacks(state, builder);
		}
	}
}