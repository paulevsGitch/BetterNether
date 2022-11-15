package org.betterx.betternether.enchantments;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.items.NetherArmor;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubyFire extends Enchantment {
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    public RubyFire() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, ARMOR_SLOTS);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        if (itemStack == null) return false;
        final Item i = itemStack.getItem();

        if (i instanceof TieredItem t) {
            return t.getTier() == BNToolMaterial.NETHER_RUBY;
        }

        return i instanceof NetherArmor;
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

    @Override
    public void doPostHurt(LivingEntity player, Entity entity, int i) {
        final RandomSource random = player.getRandom();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(
                NetherEnchantments.RUBY_FIRE,
                player
        );
        if (shouldHit(i, random)) {
            if (entity != null) {
                entity.hurt(DamageSource.indirectMagic(entity, entity), getDamage(i, random));
                entity.setRemainingFireTicks(100 + 50 * random.nextInt(3));
                if (entity instanceof LivingEntity living) {
                    living.knockback(
                            1 + MHelper.nextFloat(random, 2.0f),
                            player.getX() - living.getX(),
                            player.getZ() - living.getZ()
                    );
                }
            }

            if (entry != null) {
                entry.getValue()
                     .hurtAndBreak(1, player, livingEntity -> livingEntity.broadcastBreakEvent(entry.getKey()));
            }
        }
    }

    private static boolean shouldHit(int i, RandomSource random) {
        if (i <= 0) return false;
        return random.nextFloat() < 0.20f * i;
    }

    private static int getDamage(int i, RandomSource random) {
        if (i > 10) return i - 10;
        return 2 + random.nextInt(5);
    }


    private static final Map<Item, BlastingRecipe> FIRE_CONVERSIONS = new HashMap<>();
    public static final ThreadLocal<List<ItemStack>> convertedDrops = ThreadLocal.withInitial(ArrayList::new);

    public static boolean getDrops(
            BlockState brokenBlock,
            ServerLevel level,
            BlockPos blockPos,
            Player player,
            ItemStack breakingItem
    ) {
        final int fireLevel = EnchantmentHelper.getItemEnchantmentLevel(NetherEnchantments.RUBY_FIRE, breakingItem);
        if (fireLevel > 0) {
            if (FIRE_CONVERSIONS.isEmpty()) buildConversionTable(level);

            boolean didConvert = false;
            int xpDrop = 0;
            try {
                final List<ItemStack> drops = Block.getDrops(brokenBlock, level, blockPos, null, player, breakingItem);
                convertedDrops.get().clear();

                for (ItemStack stack : drops) {
                    BlastingRecipe result = FIRE_CONVERSIONS.get(stack.getItem());
                    if (result != null) {
                        didConvert = true;
                        final ItemStack resultStack = result.getResultItem();
                        xpDrop += result.getExperience();
                        convertedDrops.get()
                                      .add(new ItemStack(
                                              resultStack.getItem(),
                                              resultStack.getCount() * stack.getCount()
                                      ));
                    } else {
                        convertedDrops.get().add(stack);
                    }
                }
            } catch (Exception e) {
                BetterNether.LOGGER.error("Unable to get Drops for " + breakingItem, e);
            }

            if (didConvert) {
                if (xpDrop > 0) {
                    popExperience(level, blockPos, xpDrop);
                }
                convertedDrops.get().forEach(itemStack -> Block.popResource(level, blockPos, itemStack));
                brokenBlock.spawnAfterBreak(level, blockPos, breakingItem, true);
                convertedDrops.get().clear();
                return true;
            }
            convertedDrops.get().clear();
        }

        return false;
    }

    private static void popExperience(ServerLevel level, BlockPos blockPos, int amount) {
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            ExperienceOrb.award(level, Vec3.atCenterOf(blockPos), amount);
        }
    }

    private static void buildConversionTable(ServerLevel level) {
        final List<BlastingRecipe> recipes = level.getRecipeManager()
                                                  .getAllRecipesFor(RecipeType.BLASTING);
        for (BlastingRecipe r : recipes) {
            for (Ingredient ingredient : r.getIngredients()) {
                for (ItemStack stack : ingredient.getItems()) {
                    if (stack.getItem() instanceof BlockItem blitem) {
                        if (blitem.getBlock().defaultBlockState().is(CommonBlockTags.IS_OBSIDIAN)) {
                            continue;
                        }
                    }
                    FIRE_CONVERSIONS.put(stack.getItem(), r);
                }
            }
        }
    }
}
