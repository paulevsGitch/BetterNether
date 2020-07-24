package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.MathHelper;
import paulevs.betternether.MHelper;

public class BlockOre extends OreBlock
{
	private final Item dropItem;
	private final int minCount;
	private final int maxCount;
	
	public BlockOre(Item drop, int minCount, int maxCount)
	{
		super(FabricBlockSettings.of(Material.STONE)
				.requiresTool()
				.hardness(3F)
				.resistance(5F)
				.requiresTool()
				.sounds(BlockSoundGroup.NETHERRACK));
		this.dropItem = drop;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool.isEffectiveOn(state))
		{
			int fortune = EnchantmentHelper.getLevel(Enchantments.FORTUNE, tool);
			int min = MathHelper.clamp(minCount + fortune, 0, maxCount);
			if (min == maxCount)
				return Lists.newArrayList(new ItemStack(dropItem, maxCount));
			int count = MHelper.randRange(min, maxCount, MHelper.RANDOM);
			return Lists.newArrayList(new ItemStack(dropItem, count));
		}
		return Lists.newArrayList();
	}
}
