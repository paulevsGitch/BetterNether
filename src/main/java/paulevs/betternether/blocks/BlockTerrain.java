package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import ru.bclib.api.TagAPI;
import ru.bclib.interfaces.TagProvider;

public class BlockTerrain extends BlockBase implements TagProvider {
	public static final SoundType TERRAIN_SOUND = new SoundType(1.0F, 1.0F,
			SoundEvents.NETHERRACK_BREAK,
			SoundEvents.WART_BLOCK_STEP,
			SoundEvents.NETHERRACK_PLACE,
			SoundEvents.NETHERRACK_HIT,
			SoundEvents.NETHERRACK_FALL);

	public BlockTerrain() {
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).sounds(TERRAIN_SOUND).requiresTool());
		this.setDropItself(false);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool.isCorrectToolForDrops(state)) {
			if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0)
				return Collections.singletonList(new ItemStack(this.asItem()));
			else
				return Collections.singletonList(new ItemStack(Blocks.NETHERRACK));
		}
		else
			return super.getDrops(state, builder);
	}

	@Override
	public void addTags(List<TagAPI.TagLocation<Block>> blockTags, List<TagAPI.TagLocation<Item>> itemTags) {
		blockTags.add(TagAPI.NAMED_COMMON_BLOCK_NETHERRACK);
		blockTags.add(TagAPI.NAMED_COMMON_BLOCK_NETHER_STONES);
	}
}