package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BCLBlockProperties;
import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.bclib.interfaces.tools.AddMineableShears;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.google.common.collect.Lists;

import java.util.List;

public class BlockWhisperingGourdVine extends BlockBaseNotFull implements BonemealableBlock, AddMineableShears {
    private static final VoxelShape SELECTION = box(2, 0, 2, 14, 16, 14);
    public static final EnumProperty<BlockProperties.TripleShape> SHAPE = BCLBlockProperties.TRIPLE_SHAPE;

    public BlockWhisperingGourdVine() {
        super(FabricBlockSettings.of(Materials.NETHER_PLANT)
                                 .mapColor(MaterialColor.COLOR_RED)
                                 .requiresTool()
                                 .sounds(SoundType.CROP)
                                 .noCollission()
                                 .instabreak()
                                 .noOcclusion()
                                 .randomTicks()
                                 .instabreak());
        this.setRenderLayer(BNRenderLayer.CUTOUT);
        this.setDropItself(false);
        this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, BlockProperties.TripleShape.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return SELECTION;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.above());
        return upState.getBlock() == this || upState.isFaceSturdy(world, pos, Direction.DOWN);
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
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (!canSurvive(state, world, pos))
            return Blocks.AIR.defaultBlockState();
        else if (world.getBlockState(pos.below()).getBlock() != this)
            return state.setValue(SHAPE, BlockProperties.TripleShape.BOTTOM);
        else if (state.getValue(SHAPE) == BlockProperties.TripleShape.BOTTOM)
            return state.setValue(SHAPE, BlockProperties.TripleShape.TOP);
        else
            return state;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.above(3)).getBlock() != this && world.getBlockState(pos.below())
                                                                            .getBlock() != this;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.above(3)).getBlock() != this && world.getBlockState(pos.below())
                                                                            .getMaterial()
                                                                            .isReplaceable();
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, BlockProperties.TripleShape.TOP));
        BlocksHelper.setWithUpdate(world, pos.below(), defaultBlockState());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);
        if (tool != null && (BaseShearsItem.isShear(tool) || EnchantmentHelper.getItemEnchantmentLevel(
                Enchantments.SILK_TOUCH,
                tool
        ) > 0))
            return Lists.newArrayList(new ItemStack(this.asItem()));
        else if (state.getValue(SHAPE) == BlockProperties.TripleShape.BOTTOM || MHelper.RANDOM.nextBoolean())
            return Lists.newArrayList(new ItemStack(this.asItem()));
        else
            return Lists.newArrayList();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(SHAPE) == BlockProperties.TripleShape.BOTTOM;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        if (random.nextInt(8) == 0) {
            if (isBonemealSuccess(world, random, pos, state)) {
                if (world.getBlockState(pos.above()).getBlock() != this) {
                    performBonemeal(world, random, pos, state);
                } else {
                    BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, BlockProperties.TripleShape.MIDDLE));
                    BlocksHelper.setWithUpdate(world, pos.below(), defaultBlockState());
                }
            }
        }
    }


    public InteractionResult use(
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        ItemStack tool = player.getItemInHand(hand);
        if (BaseShearsItem.isShear(tool) && state.getValue(SHAPE) == BlockProperties.TripleShape.MIDDLE) {
            if (!world.isClientSide) {
                BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, BlockProperties.TripleShape.BOTTOM));
                world.addFreshEntity(new ItemEntity(
                        world,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        new ItemStack(
                                NetherBlocks.WHISPERING_GOURD)
                ));
                if (world.random.nextBoolean()) {
                    world.addFreshEntity(new ItemEntity(
                            world,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            new ItemStack(NetherBlocks.WHISPERING_GOURD)
                    ));
                }
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, hand, hit);
        }
    }
}
