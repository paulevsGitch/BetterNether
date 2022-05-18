package org.betterx.betternether.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.google.common.collect.Lists;
import org.betterx.bclib.interfaces.tools.AddMineableAxe;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import java.util.List;

public class BlockGiantLucis extends HugeMushroomBlock implements AddMineableAxe {
    public BlockGiantLucis() {
        super(FabricBlockSettings.of(Materials.NETHER_GRASS)
                                 .mapColor(MaterialColor.COLOR_YELLOW)
                                 .requiresTool()
                                 .luminance(15)
                                 .sounds(SoundType.WOOD)
                                 .hardness(1F)
                                 .noOcclusion());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0)
            return Lists.newArrayList(new ItemStack(this.asItem()));
        return Lists.newArrayList(new ItemStack(NetherBlocks.LUCIS_SPORE, MHelper.randRange(0, 1, MHelper.RANDOM)),
                                  new ItemStack(NetherItems.GLOWSTONE_PILE, MHelper.randRange(0, 2, MHelper.RANDOM)));
    }
}
