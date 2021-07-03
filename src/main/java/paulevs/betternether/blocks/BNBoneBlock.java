package paulevs.betternether.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.BlocksHelper;

import java.util.Collections;
import java.util.List;

public class BNBoneBlock extends BlockBase {
	public BNBoneBlock() {
		super(BlocksHelper.copySettingsOf(Blocks.BONE_BLOCK));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this.asItem()));
	}
}
