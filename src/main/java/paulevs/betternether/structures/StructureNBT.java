package paulevs.betternether.structures;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BetterNether;

public class StructureNBT
{
	protected Identifier location;
	protected Structure structure;
	protected BlockMirror mirror = BlockMirror.NONE;
	protected BlockRotation rotation = BlockRotation.NONE;

	public StructureNBT(String structure)
	{
		location = new Identifier(BetterNether.MOD_ID, structure);
		this.structure = readStructureFromJar(location);
	}

	protected StructureNBT(Identifier location, Structure structure)
	{
		this.location = location;
		this.structure = structure;
	}
	
	public StructureNBT setRotation(BlockRotation rotation)
	{
		this.rotation = rotation;
		return this;
	}
	
	public BlockMirror getMirror()
	{
		return mirror;
	}
	
	public StructureNBT setMirror(BlockMirror mirror)
	{
		this.mirror = mirror;
		return this;
	}
	
	public void randomRM(Random random)
	{
		rotation = BlockRotation.values()[random.nextInt(4)];
		mirror = BlockMirror.values()[random.nextInt(3)];
	}
	
	public boolean generateCentered(IWorld world, BlockPos pos)
	{
		if(structure == null)
		{
			System.out.println("No structure: " + location.toString());
			return false;
		}
		
		Mutable blockpos2 = new Mutable(structure.getSize().rotate(rotation));//.rotate(mirror.getRotation(direction));
		if (this.mirror == BlockMirror.FRONT_BACK)
			blockpos2.setX(-blockpos2.getX());
		if (this.mirror == BlockMirror.LEFT_RIGHT)
			blockpos2.setZ(-blockpos2.getZ());
		StructurePlacementData data = new StructurePlacementData().setRotation(this.rotation).setMirrored(this.mirror);//.setRotation(this.rotation).setIgnoreEntities(true);
		structure.place(world, pos.add(-blockpos2.getX() >> 1, 0, -blockpos2.getZ() >> 1), data);
		return true;
	}
	
	private Structure readStructureFromJar(Identifier resource)
    {
        String ns = resource.getNamespace();
        String nm = resource.getPath();

        try
        {
        	InputStream inputstream = MinecraftServer.class.getResourceAsStream("/data/" + ns + "/structures/" + nm + ".nbt");
            return readStructureFromStream(inputstream);
        }
        catch (IOException e)
        {
			e.printStackTrace();
		}
        
        return null;
    }
	
	private Structure readStructureFromStream(InputStream stream) throws IOException
    {
		CompoundTag nbttagcompound = NbtIo.readCompressed(stream);

        Structure template = new Structure();
        template.fromTag(nbttagcompound);
        
        return template;
    }
	
	public BlockPos getSize()
	{
		if (rotation == BlockRotation.NONE || rotation == BlockRotation.CLOCKWISE_180)
			return structure.getSize();
		else
		{
			BlockPos size = structure.getSize();
			int x = size.getX();
			int z = size.getZ();
			return new BlockPos(z, size.getY(), x);
		}
	}
	
	public String getName()
	{
		return location.getPath();
	}

	public BlockBox getBoundingBox(BlockPos pos)
	{
		return structure.calculateBoundingBox(new StructurePlacementData().setRotation(this.rotation).setMirrored(mirror), pos);
	}
}
