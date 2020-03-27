package paulevs.betternether.world.structures.piece;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.CavesChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.noise.OpenSimplexNoise;

public class CavePiece extends CustomPiece
{
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(927649);
	private static final Mutable POS = new Mutable();
	
	private BlockPos center;
	private int radius;
	private int radSqr;
	private int minY;
	private int maxY;
	
	public CavePiece(BlockPos center, int radius, Random random)
	{
		super(StructureTypes.CAVE, random.nextInt());
		this.center = center;
		this.radius = radius;
		this.radSqr = radius * radius;
		makeBoundingBox();
	}
	
	protected CavePiece(StructureManager manager, CompoundTag tag)
	{
		super(StructureTypes.CAVE, tag);
		this.center = NbtHelper.toBlockPos(tag.getCompound("center"));
		this.radius = tag.getInt("radius");
		this.radSqr = radius * radius;
		makeBoundingBox();
	}

	@Override
	protected void toNbt(CompoundTag tag)
	{
		tag.put("center", NbtHelper.fromBlockPos(center));
		tag.putInt("radius", radius);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, BlockPos blockPos)
	{
		BlockState bottom = LAVA;
		if (!(chunkGenerator instanceof CavesChunkGenerator))
		{
			bottom = Blocks.NETHERRACK.getDefaultState();
		}
		for (int x = blockBox.minX; x <= blockBox.maxX; x++)
		{
			int px = x - center.getX();
			for (int z = blockBox.minZ; z <= blockBox.maxZ; z++)
			{
				int pz = z - center.getZ();
				for (int y = minY; y <= maxY; y++)
				{
					int py = (y - center.getY()) << 1;
					if (px * px + py * py + pz * pz <= radSqr + NOISE.eval(x * 0.1, y * 0.1, z * 0.1) * 800)
					{
						POS.set(x, y, z);
						if (y > 31)
						{
							world.setBlockState(POS, AIR, 0);
						}
						else
							world.setBlockState(POS, bottom, 0);
					}
				}
			}
		}
		return true;
	}
	
	private void makeBoundingBox()
	{
		int x1 = center.getX() - radius;
		int x2 = center.getX() + radius;
		minY = Math.max(22, center.getY() - radius);
		maxY = Math.min(96, center.getY() + radius);
		int z1 = center.getZ() - radius;
		int z2 = center.getZ() + radius;
		this.boundingBox = new BlockBox(x1, minY, z1, x2, maxY, z2);
	}
}
