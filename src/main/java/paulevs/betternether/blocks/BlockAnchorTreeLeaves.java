package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockAnchorTreeLeaves extends BlockBaseNotFull {
	private Random random = new Random();


	public BlockAnchorTreeLeaves() {
		super(Materials
            .makeLeaves(MaterialColor.COLOR_GREEN)
            .dynamicShape()
        );
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);

	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return false;
	}



	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.XZ;
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool != null && (FabricToolTags.SHEARS.contains(tool.getItem()) || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0)) {
			return Lists.newArrayList(new ItemStack(this.asItem()));
		}
		else {
			return random.nextInt(5) == 0 ? Lists.newArrayList(new ItemStack(BlocksRegistry.ANCHOR_TREE_SAPLING)) : super.getDrops(state, builder);
		}
	}
}