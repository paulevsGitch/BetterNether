package org.betterx.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import org.betterx.betternether.BetterNether;

import java.io.IOException;
import java.io.InputStream;

public class StructureNBT {
    protected ResourceLocation location;
    protected StructureTemplate structure;
    protected Mirror mirror = Mirror.NONE;
    protected Rotation rotation = Rotation.NONE;

    public StructureNBT(String structure) {
        location = new ResourceLocation(BetterNether.MOD_ID, structure);
        this.structure = readStructureFromJar(location);
    }

    protected StructureNBT(ResourceLocation location, StructureTemplate structure) {
        this.location = location;
        this.structure = structure;
    }

    public StructureNBT setRotation(Rotation rotation) {
        this.rotation = rotation;
        return this;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public StructureNBT setMirror(Mirror mirror) {
        this.mirror = mirror;
        return this;
    }

    public void randomRM(RandomSource random) {
        rotation = Rotation.values()[random.nextInt(4)];
        mirror = Mirror.values()[random.nextInt(3)];
    }

    public boolean generateCentered(ServerLevelAccessor world, BlockPos pos) {
        if (structure == null) {
            System.out.println("No structure: " + location.toString());
            return false;
        }

        MutableBlockPos blockpos2 = new MutableBlockPos().set(structure.getSize());
        if (this.mirror == Mirror.FRONT_BACK)
            blockpos2.setX(-blockpos2.getX());
        if (this.mirror == Mirror.LEFT_RIGHT)
            blockpos2.setZ(-blockpos2.getZ());
        blockpos2.set(blockpos2.rotate(rotation));
        StructurePlaceSettings data = new StructurePlaceSettings().setRotation(this.rotation).setMirror(this.mirror);
        BlockPos newPos = pos.offset(-blockpos2.getX() >> 1, 0, -blockpos2.getZ() >> 1);
        structure.placeInWorld(
                world,
                newPos,
                newPos,
                data,
                world.getRandom(),
                Block.UPDATE_CLIENTS
                              );
        return true;
    }

    private StructureTemplate readStructureFromJar(ResourceLocation resource) {
        String ns = resource.getNamespace();
        String nm = resource.getPath();

        try {
            InputStream inputstream = MinecraftServer.class.getResourceAsStream("/data/" + ns + "/structures/" + nm + ".nbt");
            return readStructureFromStream(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private StructureTemplate readStructureFromStream(InputStream stream) throws IOException {
        CompoundTag nbttagcompound = NbtIo.readCompressed(stream);

        StructureTemplate template = new StructureTemplate();
        template.load(nbttagcompound);

        return template;
    }

    public BlockPos getSize() {
        if (rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180)
            return new BlockPos(structure.getSize());
        else {
            Vec3i size = structure.getSize();
            int x = size.getX();
            int z = size.getZ();
            return new BlockPos(z, size.getY(), x);
        }
    }

    public String getName() {
        return location.getPath();
    }

    public BoundingBox getBoundingBox(BlockPos pos) {
        return structure.getBoundingBox(new StructurePlaceSettings().setRotation(this.rotation).setMirror(mirror), pos);
    }
}
