package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.noise.Dither;
import paulevs.betternether.noise.WorleyNoiseIDDistorted3D;
import paulevs.betternether.registers.BiomeRegister;

public class BNWorldGenerator
{
	//public static IStructureWorld[] globalStructuresLand;
	//public static IStructureWorld[] globalStructuresLava;
	//public static IStructureWorld[] globalStructuresCave;
	
	public static boolean hasCleaningPass = true;
	public static boolean hasEyeGen = true;
	public static boolean hasStalagnateGen = true;
	public static boolean hasLucisGen = true;
	public static boolean hasSmokerGen = true;
	public static boolean hasWartTreeGen = true;
	public static boolean hasEggPlantGen = true;
	public static boolean hasInkBushGen = true;
	public static boolean hasBlackAppleGen = true;
	public static boolean hasMagmaFlowerGen = true;
	public static boolean hasRedMushroomGen = true;
	public static boolean hasBrownMushroomGen = true;
	public static boolean hasOrangeMushroomGen = true;
	public static boolean hasRedMoldGen = true;
	public static boolean hasGrayMoldGen = true;
	public static boolean hasWartsGen = true;
	
	private static WorleyNoiseIDDistorted3D noise3d;
	private static WorleyNoiseIDDistorted3D subbiomesNoise;
	private static Dither dither;
	private static double biomeSizeXZ = 1F / Config.getFloat("world_generator", "biome_size_horizontal", 100F);
	private static double biomeSizeY = 1F / Config.getFloat("world_generator", "biome_size_vertical", 30F);
	private static double subBiomeSize = 0.1;
	//private static float plantDensity = 1;
	//private static float structueDensity = 1F / 64F;
	//private static float oreDensity = 1F / 1024F;
	
	public static boolean enablePlayerDamage;
	public static boolean enableMobDamage;
	
	//private static Random coordinateRandom;
	private static BlockState state_air = Blocks.AIR.getDefaultState();
	
	//private static CityStructureManager cityManager;
	//private static BlockPos pos;
	private static Mutable popPos = new Mutable();
	
	private static NetherBiome[][][] biomeArray = new NetherBiome[8][64][8];

	public static void init(IWorld world)
	{
		long seed = world.getSeed();
		noise3d = new WorleyNoiseIDDistorted3D(seed, BiomeRegister.getBiomeCount());
		subbiomesNoise = new WorleyNoiseIDDistorted3D(~seed, 256);
		dither = new Dither(seed);
		//coordinateRandom = new Random();
		/*if (ConfigLoader.hasCities())
		{
			cityManager = new CityStructureManager(seed);
			cityManager.load(world);
			cityManager.setDistance(ConfigLoader.getCityDistance());
		}*/
	}
	
	/*public static void save(World world)
	{
		if (cityManager != null)
			cityManager.save(world);
	}*/
	
	private static void makeBiomeArray(int sx, int sz)
	{
		int id;
		int wx, wy, wz;
		for (int x = 0; x < 8; x++)
		{
			wx = sx | (x << 1);
			for (int y = 0; y < 64; y++)
			{
				wy = (y << 1);
				for (int z = 0; z < 8; z++)
				{
					wz = sx | (z << 1);
					id = getBiome(wx, wy, wz);
					biomeArray[x][y][z] = BiomeRegister.getBiomeID(id);
					if (isEdge(id, wx, y, wz, biomeArray[x][y][z].getEdgeSize()))
						biomeArray[x][y][z] = biomeArray[x][y][z].getEdge();
					else
						biomeArray[x][y][z] = biomeArray[x][y][z].getSubBiome(wx, y, wz);
				}
			}
		}
	}
	
	private static NetherBiome getBiomeLocal(int x, int y, int z, Random random)
	{
		x = (x + random.nextInt(2)) >> 1;
		if (x > 7)
			x = 7;
		y = (y + random.nextInt(2)) >> 1;
		if (y > 63)
			y = 63;
		z = (z + random.nextInt(2)) >> 1;
		if (z > 7)
			z = 7;
		
		x = dither.ditherX(x, y, z);
		y = dither.ditherY(x, y, z);
		z = dither.ditherZ(x, y, z);
		
		if (x > 7)
			x = 7;
		if (y > 63)
			y = 63;
		if (z > 7)
			z = 7;
		
		return biomeArray[x][y][z];
	}

	public static void generate(IWorld world, int cx, int cz, Random random)
	{
		NetherBiome biome;
		int sx = (cx << 4) | 8;
		int sz = (cz << 4) | 8;

		//Structure Generator
		/*if (coordinateRandom.nextFloat() < structueDensity)
		{
			pos = new BlockPos(sx + coordinateRandom.nextInt(8), 32 + coordinateRandom.nextInt(120 - 32), sz + coordinateRandom.nextInt(8));
			while (world.getBlockState(pos).getBlock() != Blocks.AIR && pos.getY() > 32)
			{
				pos = pos.down();
			}
			pos = downRay(world, pos);
			if (pos != null)
			{
				boolean terrain = true;
				for (int y = 1; y < 8; y++)
				{
					if (world.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
					{
						terrain = false;
						break;
					}
				}
				if (terrain)
				{
					if (globalStructuresLava.length > 0 && world.getBlockState(pos).getMaterial() == Material.LAVA)
						globalStructuresLava[coordinateRandom.nextInt(globalStructuresLava.length)].generateLava(world, pos.up(), coordinateRandom);
					else if (globalStructuresLand.length > 0)
						globalStructuresLand[coordinateRandom.nextInt(globalStructuresLand.length)].generateSurface(world, pos.up(), coordinateRandom);
				}
				else if (globalStructuresCave.length > 0)
				{
					globalStructuresCave[coordinateRandom.nextInt(globalStructuresCave.length)].generateSubterrain(world, pos, coordinateRandom);
				}
			}
		}*/

		makeBiomeArray(sx, sz);

		// Total Populator
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				for (int y = 5; y < 126; y++)
				{
					popPos.set(wx, y, wz);
					if (BlocksHelper.isNetherGround(world.getBlockState(popPos)))
					{
						biome = getBiomeLocal(x, y, z, random);

						// Ground Generation
						if (world.getBlockState(popPos.up()).getBlock() == Blocks.AIR)
						{
							biome.genSurfColumn(world, popPos, random);
							biome.genFloorObjects(world, popPos.up(), random);
						}

						// Ceiling Generation
						else if (world.getBlockState(popPos.down()).getBlock() == Blocks.AIR)
						{
							biome.genCeilObjects(world, popPos.down(), random);
						}

						// Wall Generation
						else if (((x + y + z) & 1) == 0)
						{
							boolean bNorth = world.isAir(popPos.north());
							boolean bSouth = world.isAir(popPos.south());
							boolean bEast = world.isAir(popPos.east());
							boolean bWest = world.isAir(popPos.west());
							if (bNorth || bSouth || bEast || bWest)
							{
								BlockPos objPos = null;
								if (bNorth)
									objPos = popPos.north();
								else if (bSouth)
									objPos = popPos.south();
								else if (bEast)
									objPos = popPos.east();
								else
									objPos = popPos.west();
								
								boolean bDown = world.isAir(objPos.up());
								boolean bUp = world.isAir(objPos.down());
								
								if (bDown && bUp)
								{
									biome.genWallObjects(world, objPos, random);
									//if (y < 37 && world.getBlockState(popPos).getBlock() instanceof BlockNetherBrick && random.nextInt(512) == 0)
									//	wartCapGen.generate(world, popPos, random);
								}
							}
						}
					}
					//if (random.nextInt(1024) == 0)
					//	spawnOre(BlocksRegister.BLOCK_CINCINNASITE_ORE.getDefaultState(), world, popPos, random);
				}
			}
		}
	}
	
	private static boolean isEdge(int centerID, int x, int y, int z, int distance)
	{
		return distance > 0 && (centerID != getBiome(x + distance, y, z) ||
								centerID != getBiome(x - distance, y, z) ||
								centerID != getBiome(x, y + distance, z) ||
								centerID != getBiome(x, y - distance, z) ||
								centerID != getBiome(x, y, z + distance) ||
								centerID != getBiome(x, y, z - distance));
	}
	
	private static int getBiome(int x, int y, int z)
	{
		double px = (double) dither.ditherX(x, y, z) * biomeSizeXZ;
		double py = (double) dither.ditherY(x, y, z) * biomeSizeY;
		double pz = (double) dither.ditherZ(x, y, z) * biomeSizeXZ;
		return noise3d.GetValue(px, py, pz);
	}
	
	public static void smoothChunk(IWorld world, int cx, int cz)
	{
		if (hasCleaningPass)
		{
			int wx = (cx << 4) | 8;
			int wz = (cz << 4) | 8;
			List<BlockPos> pos = new ArrayList<BlockPos>();
			BlockPos up;
			BlockPos down;
			BlockPos north;
			BlockPos south;
			BlockPos east;
			BlockPos west;
			for (int y = 32; y < 110; y++)
			{
				for (int x = 0; x < 16; x++)
					for (int z = 0; z < 16; z++)
					{
						popPos.set(x + wx, y, z + wz);
						if (canReplace(world, popPos))
						{
							up = popPos.up();
							down = popPos.down();
							north = popPos.north();
							south = popPos.south();
							east = popPos.east();
							west = popPos.west();
							if (world.isAir(north) && world.isAir(south))
								pos.add(popPos);
							else if (world.isAir(east) && world.isAir(west))
								pos.add(popPos);
							else if (world.isAir(up) && world.isAir(down))
								pos.add(popPos);
							else if (world.isAir(popPos.north().east().down()) && world.isAir(popPos.south().west().up()))
								pos.add(popPos);
							else if (world.isAir(popPos.south().east().down()) && world.isAir(popPos.north().west().up()))
								pos.add(popPos);
							else if (world.isAir(popPos.north().west().down()) && world.isAir(popPos.south().east().up()))
								pos.add(popPos);
							else if (world.isAir(popPos.south().west().down()) && world.isAir(popPos.north().east().up()))
								pos.add(popPos);
						}
					}
			}
			for (BlockPos p : pos)
			{
				BlocksHelper.setWithoutUpdate(world, p, state_air);
			}
		}
		//if (cityManager != null)
		//	cityManager.generate(world, cx, cz);
	}
	
	private static boolean canReplace(IWorld world, BlockPos pos)
	{
		return BlocksHelper.isNetherGround(world.getBlockState(pos));
	}
	
	/*private static void spawnOre(IBlockState state, World world, BlockPos pos, Random random)
	{
		for (int i = 0; i < 6 + random.nextInt(11); i++)
		{
			BlockPos local = pos.add(random.nextInt(3), random.nextInt(3), random.nextInt(3));
			if (world.getBlockState(local).getBlock() == Blocks.NETHERRACK)
			{
				BlocksHelper.setWithoutUpdate(world, local, state);
			}
		}
	}*/
	
	/*public static void updateGenSettings()
	{
		biomeSizeXZ = 1.0 / (double) ConfigLoader.getBiomeSizeXZ();
		biomeSizeY = 1.0 / (double) ConfigLoader.getBiomeSizeY();
		subBiomeSize = biomeSizeXZ * 3;
		hasCleaningPass = ConfigLoader.hasCleaningPass();
		hasEyeGen = BlocksRegister.BLOCK_EYE_VINE != Blocks.AIR &&
				BlocksRegister.BLOCK_EYEBALL != Blocks.AIR &&
				BlocksRegister.BLOCK_EYEBALL_SMALL != Blocks.AIR;
		hasStalagnateGen = BlocksRegister.BLOCK_STALAGNATE_BOTTOM != Blocks.AIR &&
				BlocksRegister.BLOCK_STALAGNATE_MIDDLE != Blocks.AIR &&
				BlocksRegister.BLOCK_STALAGNATE_TOP != Blocks.AIR;
		hasLucisGen = BlocksRegister.BLOCK_LUCIS_MUSHROOM != Blocks.AIR;
		hasSmokerGen = BlocksRegister.BLOCK_SMOKER != Blocks.AIR;
		hasWartTreeGen = BlocksRegister.BLOCK_WART_SEED != Blocks.AIR;
		hasEggPlantGen = BlocksRegister.BLOCK_EGG_PLANT != Blocks.AIR;
		hasInkBushGen = BlocksRegister.BLOCK_INK_BUSH != Blocks.AIR;
		hasBlackAppleGen = BlocksRegister.BLOCK_BLACK_APPLE != Blocks.AIR;
		hasMagmaFlowerGen = BlocksRegister.BLOCK_MAGMA_FLOWER != Blocks.AIR;
		hasRedMushroomGen = BlocksRegister.BLOCK_RED_LARGE_MUSHROOM != Blocks.AIR;
		hasBrownMushroomGen = BlocksRegister.BLOCK_BROWN_LARGE_MUSHROOM != Blocks.AIR;
		hasOrangeMushroomGen = BlocksRegister.BLOCK_ORANGE_MUSHROOM != Blocks.AIR;
		hasRedMoldGen = BlocksRegister.BLOCK_RED_MOLD != Blocks.AIR;
		hasGrayMoldGen = BlocksRegister.BLOCK_GRAY_MOLD != Blocks.AIR;
		hasWartsGen = ConfigLoader.hasNetherWart();
		
		globalStructuresLand = new IStructureWorld[] {
				new StructureAltar(),
				new StructureBuilding("altar_01", -1),
				new StructureBuilding("altar_02", -4),
				new StructureBuilding("altar_03", -3),
				new StructureBuilding("altar_04", -3),
				new StructureBuilding("altar_05", -2),
				new StructureBuilding("altar_06", -2),
				new StructureBuilding("portal_01", -4),
				new StructureBuilding("portal_02", -3),
				new StructureBuilding("garden_01", -3),
				new StructureBuilding("garden_02", -2),
				new StructureBuilding("pillar_01", -1),
				new StructureBuilding("respawn_point_01", -3),
				new StructureBuilding("respawn_point_02", -2)
		};
		
		globalStructuresLava = new IStructureWorld[] { };
		
		globalStructuresCave = new IStructureWorld[] {
				new StructureBuilding("room_01", -5),
		};
	}*/
	
	/*private static BlockPos downRay(World world, BlockPos start)
	{
		int dist = 0;
		Block b;
		BlockPos p;
		for (int j = start.getY(); j > 31; j--)
		{
			p = new BlockPos(start.getX(), j, start.getZ());
			b = world.getBlockState(p).getBlock();
			if (b != Blocks.AIR && (b instanceof BlockNetherrack || b instanceof BlockSoulSand || world.getBlockState(p).getMaterial() == Material.LAVA))
			{
				return new BlockPos(start.getX(), j, start.getZ());
			}
		}
		return null;
	}*/
	
	/*public static void setPlantDensity(float density)
	{
		plantDensity = density;
	}*/
	
	/*public static void setStructureDensity(float density)
	{
		structueDensity = density;
	}*/
	
	public static int getSubBiome(int x, int y, int z, int count)
	{
		double px = (double) dither.ditherX(x, y, z) * subBiomeSize;
		double py = (double) dither.ditherY(x, y, z) * subBiomeSize;
		double pz = (double) dither.ditherZ(x, y, z) * subBiomeSize;
		return subbiomesNoise.GetValue(px, py, pz) % count;
	}
	
	public static NetherBiome getBiome(BlockPos pos)
	{
		int id = getBiome(pos.getX(), pos.getY(), pos.getZ());
		NetherBiome biome = BiomeRegister.getBiomeID(id);
		if (isEdge(id, pos.getX(), pos.getY(), pos.getZ(), biome.getEdgeSize()))
			biome = biome.getEdge();
		else
			biome = biome.getSubBiome(pos.getX(), pos.getY(), pos.getZ());
		return biome;
	}
	
	/*public static BlockPos getNearestCity(World world, int cx, int cz)
	{
		return cityManager.getNearestStructure(world, cx, cz);
	}*/

	/*public static void setOreDensity(float density)
	{
		oreDensity = density;
	}*/
}
