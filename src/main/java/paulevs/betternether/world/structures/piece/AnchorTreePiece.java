package paulevs.betternether.world.structures.piece;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;

public class AnchorTreePiece extends CustomPiece
{
	private static final Mutable POS = new Mutable();
	
	private BlockPos top;
	private BlockPos bottom;
	
	public AnchorTreePiece(BlockPos center, int radius, Random random)
	{
		super(StructureTypes.ANCHOR_TREE, random.nextInt());
		makeBoundingBox();
	}
	
	protected AnchorTreePiece(StructureManager manager, CompoundTag tag)
	{
		super(StructureTypes.ANCHOR_TREE, tag);
		top = NbtHelper.toBlockPos(tag.getCompound("top"));
		bottom = NbtHelper.toBlockPos(tag.getCompound("bottom"));
		makeBoundingBox();
	}

	@Override
	protected void toNbt(CompoundTag tag)
	{
		tag.put("top", NbtHelper.fromBlockPos(top));
		tag.put("bottom", NbtHelper.fromBlockPos(bottom));
	}

	@Override
	public boolean generate(StructureWorldAccess world, StructureAccessor arg, ChunkGenerator chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, BlockPos blockPos)
	{
		grow(world, top, bottom, random);
		return true;
	}
	
	private void makeBoundingBox()
	{
		int x1 = bottom.getX() - 32;
		int x2 = bottom.getX() + 32;
		int minY = Math.max(5, bottom.getY() - 20);
		int maxY = Math.min(125, top.getY() + 20);
		int z1 = bottom.getZ() - 32;
		int z2 = bottom.getZ() + 32;
		this.boundingBox = new BlockBox(x1, minY, z1, x2, maxY, z2);
	}
	
	private void grow(ServerWorldAccess world, BlockPos up, BlockPos down, Random random)
	{
		BlockPos trunkTop = lerp(down, up, 0.75);
		BlockPos trunkBottom = lerp(down, up, 0.25);
		
		System.out.println(trunkBottom + " " + trunkTop);
		
		int count = (trunkTop.getY() - trunkBottom.getY()) / 7;
		if (count < 2) count = 2;
		List<BlockPos> trunkLine = line(trunkBottom, trunkTop, count, random, 3);
		
		System.out.println(trunkLine);
		
		for (int i = 0; i < trunkLine.size() - 1; i++)
		{
			BlockPos a = trunkLine.get(i);
			BlockPos b = trunkLine.get(i + 1);
			double max = b.getY() - a.getY();
			for (int y = a.getY(); y <= b.getY(); y++)
				placeCylinder(world, lerp(a, b, (y - a.getY()) / max), 2);
		}
	}
	
	private BlockPos lerp(BlockPos start, BlockPos end, double mix)
	{
		double x = MathHelper.lerp(mix, start.getX(), end.getX());
		double y = MathHelper.lerp(mix, start.getY(), end.getY());
		double z = MathHelper.lerp(mix, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}
	
	private List<BlockPos> line(BlockPos start, BlockPos end, int count, Random random, double range)
	{
		List<BlockPos> result = Lists.newArrayList();
		double max = count - 1;
		for (int i = 0; i < count; i++)
		{
			double delta = (double) i / max;
			double x = MathHelper.lerp(delta, start.getX(), end.getX()) + random.nextGaussian() * range;
			double y = MathHelper.lerp(delta, start.getY(), end.getY());
			double z = MathHelper.lerp(delta, start.getZ(), end.getZ()) + random.nextGaussian() * range;
			result.add(new BlockPos(x, y, z));
		}
		return result;
	}
	
	private void placeCylinder(ServerWorldAccess world, BlockPos pos, double radius)
	{
		int x1 = MHelper.floor(pos.getX() - radius);
		int z1 = MHelper.floor(pos.getZ() - radius);
		int x2 = MHelper.floor(pos.getX() + radius + 1);
		int z2 = MHelper.floor(pos.getZ() + radius + 1);
		radius *= radius;
		
		for (int x = x1; x <= x2; x++)
		{
			int px2 = x - pos.getX();
			px2 *= px2;
			for (int z = z1; z <= z2; z++)
			{
				int pz2 = z - pos.getZ();
				pz2 *= pz2;
				if (px2 + pz2 <= radius)
					BlocksHelper.setWithoutUpdate(world, POS.set(x, pos.getY(), z), Blocks.DIAMOND_BLOCK.getDefaultState());
			}
		}
	}
}
