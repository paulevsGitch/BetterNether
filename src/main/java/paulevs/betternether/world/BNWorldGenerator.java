package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockNetherBrick;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import paulevs.betternether.biomes.BiomeRegister;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.config.ConfigLoader;
import paulevs.betternether.noise.Dither;
import paulevs.betternether.noise.WorleyNoiseIDDistorted3D;
import paulevs.betternether.structures.IStructureWorld;
import paulevs.betternether.structures.StructureAltar;
import paulevs.betternether.structures.StructureBuilding;
import paulevs.betternether.structures.city.CityStructureManager;
import paulevs.betternether.structures.plants.StructureBlackApple;
import paulevs.betternether.structures.plants.StructureEggPlant;
import paulevs.betternether.structures.plants.StructureEye;
import paulevs.betternether.structures.plants.StructureGrayMold;
import paulevs.betternether.structures.plants.StructureInkBush;
import paulevs.betternether.structures.plants.StructureLucis;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;
import paulevs.betternether.structures.plants.StructureOrangeMushroom;
import paulevs.betternether.structures.plants.StructureRedMold;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureStalagnate;
import paulevs.betternether.structures.plants.StructureWartCap;
import paulevs.betternether.structures.plants.StructureWartTree;

public class BNWorldGenerator
{
	public static StructureEye eyeGen = new StructureEye();
	public static StructureStalagnate stalagnateGen = new StructureStalagnate();
	public static StructureLucis lucisGen = new StructureLucis();
	public static StructureSmoker smokerGen = new StructureSmoker();
	public static StructureWartTree wartTreeGen = new StructureWartTree();
	public static StructureEggPlant eggPlantGen = new StructureEggPlant();
	public static StructureInkBush inkBushGen = new StructureInkBush();
	public static StructureBlackApple blackAppleGen = new StructureBlackApple();
	public static StructureMagmaFlower magmaFlowerGen = new StructureMagmaFlower();
	public static StructureMedRedMushroom redMushroomGen = new StructureMedRedMushroom();
	public static StructureMedBrownMushroom brownMushroomGen = new StructureMedBrownMushroom();
	public static StructureOrangeMushroom orangeMushroomGen = new StructureOrangeMushroom();
	public static StructureRedMold redMoldGen = new StructureRedMold();
	public static StructureGrayMold grayMoldGen = new StructureGrayMold();
	public static StructureWartCap wartCapGen = new StructureWartCap();
	
	public static IStructureWorld[] globalStructuresLand;
	public static IStructureWorld[] globalStructuresLava;
	public static IStructureWorld[] globalStructuresCave;
	
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
	private static double biomeSizeXZ;
	private static double biomeSizeY;
	private static double subBiomeSize;
	private static float plantDensity = 1;
	private static float structueDensity = 1F / 64F;
	
	public static boolean enablePlayerDamage;
	public static boolean enableMobDamage;
	
	private static Random coordinateRandom;
	private static IBlockState state_air = Blocks.AIR.getDefaultState();
	
	private static CityStructureManager cityManager;
	private static BlockPos pos;
	private static MutableBlockPos popPos = new MutableBlockPos();
	
	private static NetherBiome[][][] biomeArray = new NetherBiome[8][64][8];

	public static void init(World world)
	{
		long seed = world.getSeed();
		noise3d = new WorleyNoiseIDDistorted3D(seed, BiomeRegister.biomeCount);
		subbiomesNoise = new WorleyNoiseIDDistorted3D(~seed, 256);
		dither = new Dither(seed);
		coordinateRandom = new Random();
		if (ConfigLoader.hasCities())
		{
			cityManager = new CityStructureManager(seed);
			cityManager.load(world);
			cityManager.setDistance(ConfigLoader.getCityDistance());
		}
	}
	
	public static void save(World world)
	{
		if (cityManager != null)
			cityManager.save(world);
	}
	
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
		return biomeArray[x][y][z];
	}

	public static void generate(World world, int cx, int cz, Random random)
	{
		if (!world.isRemote)
		{
			
			NetherBiome biome;
			int sx = (cx << 4) | 8;
			int sz = (cz << 4) | 8;
			
			//Structure Generator
			if (coordinateRandom.nextFloat() < structueDensity)
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
			}
			
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
						//pos = new BlockPos(wx, y, wz);
						popPos.setPos(wx, y, wz);
						if (world.getBlockState(popPos).isFullBlock())
						{
							
							biome = getBiomeLocal(x, y, z, random);
							
							// Ground Generation
							if (world.getBlockState(popPos.up()).getBlock() == Blocks.AIR)
							{
								biome.genSurfColumn(world, popPos, random);
								if (random.nextFloat() <= plantDensity)
									biome.genFloorObjects(world, popPos, random);
							}
							
							// Ceiling Generation
							else if (world.getBlockState(popPos.down()).getBlock() == Blocks.AIR)
							{
								if (random.nextFloat() <= plantDensity)
									biome.genCeilObjects(world, popPos, random);
							}
							
							// Wall Generation
							else if (((x + y + z) & 1) == 0)
							{
								boolean bNorth = world.getBlockState(popPos.north()).getBlock() == Blocks.AIR;
								boolean bSouth = world.getBlockState(popPos.south()).getBlock() == Blocks.AIR;
								boolean bEast = world.getBlockState(popPos.east()).getBlock() == Blocks.AIR;
								boolean bWest = world.getBlockState(popPos.west()).getBlock() == Blocks.AIR;
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
									boolean bDown = world.getBlockState(objPos.up()).getBlock() == Blocks.AIR;
									boolean bUp = world.getBlockState(objPos.down()).getBlock() == Blocks.AIR;
									if (bDown && bUp)
									{
										if (random.nextFloat() <= plantDensity)
											biome.genWallObjects(world, popPos, objPos, random);
										if (y < 37 && world.getBlockState(popPos).getBlock() instanceof BlockNetherBrick && random.nextInt(512) == 0)
											wartCapGen.generate(world, popPos, random);
									}
								}
							}
						}
						if (BlocksRegister.BLOCK_CINCINNASITE_ORE != Blocks.AIR && random.nextInt(1024) == 0)
							spawnOre(BlocksRegister.BLOCK_CINCINNASITE_ORE.getDefaultState(), world, popPos, random);
					}
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
	
	public static void smoothChunk(World world, int cx, int cz)
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
						popPos.setPos(x + wx, y, z + wz);
						if (canReplace(world, popPos))
						{
							up = popPos.up();
							down = popPos.down();
							north = popPos.north();
							south = popPos.south();
							east = popPos.east();
							west = popPos.west();
							if (isAir(world, north) && isAir(world, south))
								pos.add(popPos);
							else if (isAir(world, east) && isAir(world, west))
								pos.add(popPos);
							else if (isAir(world, up) && isAir(world, down))
								pos.add(popPos);
							else if (isAir(world, popPos.north().east().down()) && isAir(world, popPos.south().west().up()))
								pos.add(popPos);
							else if (isAir(world, popPos.south().east().down()) && isAir(world, popPos.north().west().up()))
								pos.add(popPos);
							else if (isAir(world, popPos.north().west().down()) && isAir(world, popPos.south().east().up()))
								pos.add(popPos);
							else if (isAir(world, popPos.south().west().down()) && isAir(world, popPos.north().east().up()))
								pos.add(popPos);
						}
					}
			}
			for (BlockPos p : pos)
			{
				world.setBlockState(p, state_air);
			}
		}
		if (cityManager != null)
			cityManager.generate(world, cx, cz);
	}
	
	private static boolean isAir(World chunk, BlockPos pos)
	{
		return chunk.getBlockState(pos).getBlock() == Blocks.AIR;
	}
	
	private static boolean canReplace(World chunk, BlockPos pos)
	{
		return !isAir(chunk, pos) && (
				chunk.getBlockState(pos).getBlock() instanceof BlockNetherrack ||
				chunk.getBlockState(pos).getBlock() instanceof BlockSoulSand ||
				chunk.getBlockState(pos).getBlock() instanceof BlockGravel);
	}
	
	private static void spawnOre(IBlockState state, World world, BlockPos pos, Random random)
	{
		for (int i = 0; i < 6 + random.nextInt(11); i++)
		{
			BlockPos local = pos.add(random.nextInt(3), random.nextInt(3), random.nextInt(3));
			if (world.getBlockState(local).getBlock() == Blocks.NETHERRACK)
			{
				world.setBlockState(local, state);
			}
		}
	}
	
	public static void updateGenSettings()
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
	}
	
	private static BlockPos downRay(World world, BlockPos start)
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
	}
	
	public static void setPlantDensity(float density)
	{
		plantDensity = density;
	}
	
	public static void setStructureDensity(float density)
	{
		structueDensity = density;
	}
	
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
	
	public static BlockPos getNearestCity(World world, int cx, int cz)
	{
		return cityManager.getNearestStructure(world, cx, cz);
	}
}
