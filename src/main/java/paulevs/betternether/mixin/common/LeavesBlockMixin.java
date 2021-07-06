package paulevs.betternether.mixin.common;

import java.util.Collections;
import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
	public LeavesBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && (FabricToolTags.SHEARS.contains(tool.getItem()) || EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0)) {
			return Collections.singletonList(new ItemStack(this.asItem()));
		}
		else {
			return super.getDroppedStacks(state, builder);
		}
	}
}