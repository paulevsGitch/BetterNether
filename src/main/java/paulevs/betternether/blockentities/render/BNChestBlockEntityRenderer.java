package paulevs.betternether.blockentities.render;

import java.util.HashMap;

import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.DoubleBlockProperties.PropertySource;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNChestBlockEntity;
import paulevs.betternether.blocks.BNChest;
import paulevs.betternether.registry.BlocksRegistry;

public class BNChestBlockEntityRenderer implements BlockEntityRenderer<BNChestBlockEntity> {
	private static final HashMap<Block, RenderLayer[]> LAYERS = Maps.newHashMap();
	private static RenderLayer[] defaultLayer;

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

	public BNChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayers.CHEST);
		this.singleChestBase = modelPart.getChild(BASE);
		this.singleChestLid = modelPart.getChild(LID);
		this.singleChestLatch = modelPart.getChild(LATCH);
		ModelPart modelPart2 = ctx.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_LEFT);
		this.doubleChestRightBase = modelPart2.getChild(BASE);
		this.doubleChestRightLid = modelPart2.getChild(LID);
		this.doubleChestRightLatch = modelPart2.getChild(LATCH);
		ModelPart modelPart3 = ctx.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_RIGHT);
		this.doubleChestLeftBase = modelPart3.getChild(BASE);
		this.doubleChestLeftLid = modelPart3.getChild(LID);
		this.doubleChestLeftLatch = modelPart3.getChild(LATCH);
	}

	public static TexturedModelData getSingleTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), ModelTransform.NONE);
		modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
		modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getRightDoubleTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), ModelTransform.NONE);
		modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
		modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getLeftDoubleTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), ModelTransform.NONE);
		modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
		modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void render(BNChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		World world = entity.getWorld();
		boolean bl = world != null;
		BlockState blockState = bl ? entity.getCachedState() : (BlockState)Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? (ChestType)blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (block instanceof AbstractChestBlock) {
			AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock)block;
			boolean bl2 = chestType != ChestType.SINGLE;
			matrices.push();
			float f = ((Direction)blockState.get(ChestBlock.FACING)).asRotation();
			matrices.translate(0.5D, 0.5D, 0.5D);
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
			matrices.translate(-0.5D, -0.5D, -0.5D);
			DoubleBlockProperties.PropertySource propertySource2;
			if (bl) {
				propertySource2 = abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
			} else {
				propertySource2 = DoubleBlockProperties.PropertyRetriever::getFallback;
			}

			float g = ((Float2FloatFunction)propertySource2.apply(ChestBlock.getAnimationProgressRetriever((ChestAnimationProgress)entity))).get(tickDelta);
			g = 1.0F - g;
			g = 1.0F - g * g * g;
			int i = ((Int2IntFunction)propertySource2.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);

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

			matrices.pop();
		}
	}

	private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
		lid.pitch = -(openFactor * 1.5707964F);
		latch.pitch = lid.pitch;
		lid.render(matrices, vertices, light, overlay);
		latch.render(matrices, vertices, light, overlay);
		base.render(matrices, vertices, light, overlay);
	}

	private void renderParts(MatrixStack matrices, VertexConsumer vertices, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, float pitch, int light, int overlay) {
		modelPart.pitch = -(pitch * 1.5707964F);
		modelPart2.pitch = modelPart.pitch;
		modelPart.render(matrices, vertices, light, overlay);
		modelPart2.render(matrices, vertices, light, overlay);
		modelPart3.render(matrices, vertices, light, overlay);
	}

	private static RenderLayer getChestTexture(ChestType type, RenderLayer[] layers) {
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

	public static VertexConsumer getConsumer(VertexConsumerProvider provider, Block block, ChestType chestType) {
		RenderLayer[] layers = LAYERS.getOrDefault(block, defaultLayer);
		return provider.getBuffer(getChestTexture(chestType, layers));
	}

	static {
		defaultLayer = new RenderLayer[] {
				RenderLayer.getEntitySolid(new Identifier("entity/chest/normal.png")),
				RenderLayer.getEntitySolid(new Identifier("entity/chest/normal_left.png")),
				RenderLayer.getEntitySolid(new Identifier("entity/chest/normal_right.png"))
		};
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new Identifier(BetterNether.MOD_ID, name));
			if (block instanceof BNChest) {
				LAYERS.put(block, new RenderLayer[] {
						RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + ".png")),
						RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_left.png")),
						RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_right.png"))
				});
			}
		});
	}
}
