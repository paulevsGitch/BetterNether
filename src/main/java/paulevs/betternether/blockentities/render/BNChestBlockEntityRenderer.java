package paulevs.betternether.blockentities.render;

import java.util.HashMap;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNChestBlockEntity;
import paulevs.betternether.blocks.BNChest;
import paulevs.betternether.registry.BlocksRegistry;

public class BNChestBlockEntityRenderer implements BlockEntityRenderer<BNChestBlockEntity> {
	private static final HashMap<Block, RenderType[]> LAYERS = Maps.newHashMap();
	private static RenderType[] defaultLayer;

	private static final int ID_NORMAL = 0;
	private static final int ID_LEFT = 1;
	private static final int ID_RIGHT = 2;

	private static final String BASE = "bottom";
	private static final String LID = "lid";
	private static final String LATCH = "lock";
	private final ModelPart singleChestLid;
	private final ModelPart singleChestBase;
	private final ModelPart singleChestLatch;
	private final ModelPart doubleChestRightLid;
	private final ModelPart doubleChestRightBase;
	private final ModelPart doubleChestRightLatch;
	private final ModelPart doubleChestLeftLid;
	private final ModelPart doubleChestLeftBase;
	private final ModelPart doubleChestLeftLatch;

	public BNChestBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		ModelPart modelPart = ctx.bakeLayer(ModelLayers.CHEST);
		this.singleChestBase = modelPart.getChild(BASE);
		this.singleChestLid = modelPart.getChild(LID);
		this.singleChestLatch = modelPart.getChild(LATCH);
		ModelPart modelPart2 = ctx.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
		this.doubleChestRightBase = modelPart2.getChild(BASE);
		this.doubleChestRightLid = modelPart2.getChild(LID);
		this.doubleChestRightLatch = modelPart2.getChild(LATCH);
		ModelPart modelPart3 = ctx.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
		this.doubleChestLeftBase = modelPart3.getChild(BASE);
		this.doubleChestLeftLid = modelPart3.getChild(LID);
		this.doubleChestLeftLatch = modelPart3.getChild(LATCH);
	}

	public static LayerDefinition getSingleTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
		modelPartData.addOrReplaceChild(LATCH, CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public static LayerDefinition getRightDoubleTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
		modelPartData.addOrReplaceChild(LATCH, CubeListBuilder.create().texOffs(0, 0).addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public static LayerDefinition getLeftDoubleTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
		modelPartData.addOrReplaceChild(LATCH, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public void render(BNChestBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		Level world = entity.getLevel();
		boolean bl = world != null;
		BlockState blockState = bl ? entity.getBlockState() : (BlockState)Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? (ChestType)blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (block instanceof AbstractChestBlock) {
			AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock)block;
			boolean bl2 = chestType != ChestType.SINGLE;
			matrices.pushPose();
			float f = ((Direction)blockState.getValue(ChestBlock.FACING)).toYRot();
			matrices.translate(0.5D, 0.5D, 0.5D);
			matrices.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrices.translate(-0.5D, -0.5D, -0.5D);
			DoubleBlockCombiner.NeighborCombineResult propertySource2;
			if (bl) {
				propertySource2 = abstractChestBlock.combine(blockState, world, entity.getBlockPos(), true);
			} else {
				propertySource2 = DoubleBlockCombiner.Combiner::acceptNone;
			}

			float g = ((Float2FloatFunction)propertySource2.apply(ChestBlock.opennessCombiner((LidBlockEntity)entity))).get(tickDelta);
			g = 1.0F - g;
			g = 1.0F - g * g * g;
			int i = ((Int2IntFunction)propertySource2.apply(new BrightnessCombiner())).applyAsInt(light);

			VertexConsumer vertexConsumer = getConsumer(vertexConsumers, block, chestType);
			if (bl2) {
				if (chestType == ChestType.LEFT) {
					this.render(matrices, vertexConsumer, this.doubleChestRightLid, this.doubleChestRightLatch, this.doubleChestRightBase, g, i, overlay);
				} else {
					this.render(matrices, vertexConsumer, this.doubleChestLeftLid, this.doubleChestLeftLatch, this.doubleChestLeftBase, g, i, overlay);
				}
			} else {
				this.render(matrices, vertexConsumer, this.singleChestLid, this.singleChestLatch, this.singleChestBase, g, i, overlay);
			}

			matrices.popPose();
		}
	}

	private void render(PoseStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
		lid.xRot = -(openFactor * 1.5707964F);
		latch.xRot = lid.xRot;
		lid.render(matrices, vertices, light, overlay);
		latch.render(matrices, vertices, light, overlay);
		base.render(matrices, vertices, light, overlay);
	}

	private void renderParts(PoseStack matrices, VertexConsumer vertices, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, float pitch, int light, int overlay) {
		modelPart.xRot = -(pitch * 1.5707964F);
		modelPart2.xRot = modelPart.xRot;
		modelPart.render(matrices, vertices, light, overlay);
		modelPart2.render(matrices, vertices, light, overlay);
		modelPart3.render(matrices, vertices, light, overlay);
	}

	private static RenderType getChestTexture(ChestType type, RenderType[] layers) {
		switch (type) {
			case LEFT:
				return layers[ID_LEFT];
			case RIGHT:
				return layers[ID_RIGHT];
			case SINGLE:
			default:
				return layers[ID_NORMAL];
		}
	}

	public static VertexConsumer getConsumer(MultiBufferSource provider, Block block, ChestType chestType) {
		RenderType[] layers = LAYERS.getOrDefault(block, defaultLayer);
		return provider.getBuffer(getChestTexture(chestType, layers));
	}

	static {
		defaultLayer = new RenderType[] {
				RenderType.entitySolid(new ResourceLocation("entity/chest/normal.png")),
				RenderType.entitySolid(new ResourceLocation("entity/chest/normal_left.png")),
				RenderType.entitySolid(new ResourceLocation("entity/chest/normal_right.png"))
		};
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BNChest) {
				LAYERS.put(block, new RenderType[] {
						RenderType.entitySolid(new ResourceLocation(BetterNether.MOD_ID, "textures/entity/chest/" + name + ".png")),
						RenderType.entitySolid(new ResourceLocation(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_left.png")),
						RenderType.entitySolid(new ResourceLocation(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_right.png"))
				});
			}
		});
	}
}
