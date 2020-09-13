package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class BlockTerrain extends BlockBase
{
	public static final BlockSoundGroup TERRAIN_SOUND = new BlockSoundGroup(1.0F, 1.0F,
			SoundEvents.BLOCK_NETHERRACK_BREAK,
			SoundEvents.BLOCK_WART_BLOCK_STEP,
			SoundEvents.BLOCK_NETHERRACK_PLACE,
			SoundEvents.BLOCK_NETHERRACK_HIT,
			SoundEvents.BLOCK_NETHERRACK_FALL);
	
	public BlockTerrain()
	{
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).sounds(TERRAIN_SOUND).requiresTool());
		this.setDropItself(false);
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool.isEffectiveOn(state))
		{
			if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0)
				return Collections.singletonList(new ItemStack(this.asItem()));
			else 
				return Collections.singletonList(new ItemStack(Blocks.NETHERRACK));
		}
		else return super.getDroppedStacks(state, builder);
	}
}