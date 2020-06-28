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
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNChestBlockEntity;
import paulevs.betternether.blocks.BNChest;
import paulevs.betternether.registry.BlocksRegistry;

public class BNChestBlockEntityRenderer extends BlockEntityRenderer<BNChestBlockEntity>
{
	private static final HashMap<String, RenderLayer[]> LAYERS = Maps.newHashMap();
	
	private static final int ID_NORMAL = 0;
	private static final int ID_LEFT = 1;
	private static final int ID_RIGHT = 2;
	
	private final ModelPart partA;
	private final ModelPart partC;
	private final ModelPart partB;
	private final ModelPart partRightA;
	private final ModelPart partRightC;
	private final ModelPart partRightB;
	private final ModelPart partLeftA;
	private final ModelPart partLeftC;
	private final ModelPart partLeftB;

	public BNChestBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher)
	{
		super(blockEntityRenderDispatcher);

		this.partC = new ModelPart(64, 64, 0, 19);
		this.partC.addCuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.partA = new ModelPart(64, 64, 0, 0);
		this.partA.addCuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.partA.pivotY = 9.0F;
		this.partA.pivotZ = 1.0F;
		this.partB = new ModelPart(64, 64, 0, 0);
		this.partB.addCuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.partB.pivotY = 8.0F;
		this.partRightC = new ModelPart(64, 64, 0, 19);
		this.partRightC.addCuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.partRightA = new ModelPart(64, 64, 0, 0);
		this.partRightA.addCuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.partRightA.pivotY = 9.0F;
		this.partRightA.pivotZ = 1.0F;
		this.partRightB = new ModelPart(64, 64, 0, 0);
		this.partRightB.addCuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.partRightB.pivotY = 8.0F;
		this.partLeftC = new ModelPart(64, 64, 0, 19);
		this.partLeftC.addCuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.partLeftA = new ModelPart(64, 64, 0, 0);
		this.partLeftA.addCuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.partLeftA.pivotY = 9.0F;
		this.partLeftA.pivotZ = 1.0F;
		this.partLeftB = new ModelPart(64, 64, 0, 0);
		this.partLeftB.addCuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.partLeftB.pivotY = 8.0F;
	}
	
	public void render(BNChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		World world = entity.getWorld();
		boolean worldExists = world != null;
		BlockState blockState = worldExists ? entity.getCachedState() : (BlockState) Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? (ChestType) blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (block instanceof AbstractChestBlock)
		{
			AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>) block;
			boolean isDouble = chestType != ChestType.SINGLE;
			float f = ((Direction) blockState.get(ChestBlock.FACING)).asRotation();
			PropertySource<? extends ChestBlockEntity> propertySource;
			
			matrices.push();
			matrices.translate(0.5D, 0.5D, 0.5D);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-f));
			matrices.translate(-0.5D, -0.5D, -0.5D);
			
			if (worldExists)
			{
				propertySource = abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
			}
			else
			{
				propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
			}

			float pitch = ((Float2FloatFunction) propertySource.apply(ChestBlock.getAnimationProgressRetriever((ChestAnimationProgress) entity))).get(tickDelta);
			pitch = 1.0F - pitch;
			pitch = 1.0F - pitch * pitch * pitch;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			int blockLight = ((Int2IntFunction) propertySource.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);
			
			RenderLayer[] layers = LAYERS.get(entity.getMaterial());
			RenderLayer layer = getChestTexture(chestType, layers);
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
			
			if (isDouble)
			{
				if (chestType == ChestType.LEFT)
				{
					renderParts(matrices, vertexConsumer, this.partLeftA, this.partLeftB, this.partLeftC, pitch, blockLight, overlay);
				}
				else
				{
					renderParts(matrices, vertexConsumer, this.partRightA, this.partRightB, this.partRightC, pitch, blockLight, overlay);
				}
			}
			else
			{
				renderParts(matrices, vertexConsumer, this.partA, this.partB, this.partC, pitch, blockLight, overlay);
			}

			matrices.pop();
		}
	}
	
	private void renderParts(MatrixStack matrices, VertexConsumer vertices, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, float pitch, int light, int overlay)
	{
		modelPart.pitch = -(pitch * 1.5707964F);
		modelPart2.pitch = modelPart.pitch;
		modelPart.render(matrices, vertices, light, overlay);
		modelPart2.render(matrices, vertices, light, overlay);
		modelPart3.render(matrices, vertices, light, overlay);
	}
	
	private static RenderLayer getChestTexture(ChestType type, RenderLayer[] layers)
	{
		switch(type)
		{
		case LEFT:
			return layers[ID_LEFT];
		case RIGHT:
			return layers[ID_RIGHT];
		case SINGLE:
		default:
			return layers[ID_NORMAL];
		}
	}
	
	static
	{
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new Identifier(BetterNether.MOD_ID, name));
			if (block instanceof BNChest)
			{
				LAYERS.put(name, new RenderLayer[] {
					RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + ".png")),
					RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_left.png")),
					RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/chest/" + name + "_right.png"))
				});
			}
		});
		LAYERS.put("normal", new RenderLayer[] {
			RenderLayer.getEntitySolid(new Identifier("entity/chest/normal.png")),
			RenderLayer.getEntitySolid(new Identifier("entity/chest/normal_left.png")),
			RenderLayer.getEntitySolid(new Identifier("entity/chest/normal_right.png"))
		});
	}
}
