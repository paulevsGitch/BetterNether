package paulevs.betternether.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.api.tag.CommonBlockTags;

public class ObsidianBreaker extends Enchantment {
	public ObsidianBreaker() {
		super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.values());
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}
	
	public static float getDestroySpeedMultiplier(BlockState state, float baseSpeed, float level){
		if ((state.is(CommonBlockTags.NETHER_STONES)
			|| state.is(CommonBlockTags.NETHER_PORTAL_FRAME)
			|| state.is(Blocks.OBSIDIAN)
			|| state.is(Blocks.CRYING_OBSIDIAN)
			|| state.is(NetherBlocks.BLUE_CRYING_OBSIDIAN)
			|| state.is(NetherBlocks.WEEPING_OBSIDIAN)
			|| state.is(NetherBlocks.BLUE_WEEPING_OBSIDIAN))) {
			float speed = baseSpeed * level * 6;
			return speed;
		}
		return baseSpeed;
	}
}
