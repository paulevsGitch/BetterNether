package org.betterx.betternether.world.structures.city;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.world.structures.NetherStructureNBT;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

import java.util.List;

public class StructureCityBuilding extends NetherStructureNBT {
    protected static final BlockState AIR = Blocks.AIR.defaultBlockState();

    private BoundingBox2D bb;
    public BlockPos[] ends;
    private Direction[] dirs;
    private BlockPos rotationOffset;
    private int offsetY;

    public StructureCityBuilding(String structure) {
        super(BetterNether.makeID(structure));
        init();
    }

    public StructureCityBuilding(String structure, int offsetY) {
        super(BetterNether.makeID(structure));
        this.offsetY = offsetY;
        init();
    }

    protected StructureCityBuilding(ResourceLocation location, StructureTemplate structure) {
        super(location);
        this.structure = structure;
        init();
    }

    private void init() {
        Vec3i size = structure.getSize();
        bb = new BoundingBox2D(0, 0, size.getX(), size.getZ());
        List<StructureBlockInfo> map = structure.filterBlocks(
                BlockPos.ZERO,
                new StructurePlaceSettings(),
                Blocks.STRUCTURE_BLOCK,
                false
        );
        ends = new BlockPos[map.size()];
        dirs = new Direction[map.size()];
        int i = 0;
        BlockPos center = new BlockPos(size.getX() >> 1, size.getY(), size.getZ() >> 1);
        for (StructureBlockInfo info : map) {
            ends[i] = info.pos;
            dirs[i++] = getDir(info.pos.offset(-center.getX(), 0, -center.getZ()));
        }
        rotationOffset = new BlockPos(0, 0, 0);
        rotation = Rotation.NONE;
    }

    private Direction getDir(BlockPos pos) {
        int ax = Math.abs(pos.getX());
        int az = Math.abs(pos.getZ());
        int mx = Math.max(ax, az);
        if (mx == ax) {
            if (pos.getX() > 0)
                return Direction.EAST;
            else
                return Direction.WEST;
        } else {
            if (pos.getZ() > 0)
                return Direction.SOUTH;
            else
                return Direction.NORTH;
        }
    }

    public BoundingBox2D getBoungingBox() {
        return bb;
    }

    protected Rotation mirrorRotation(Rotation r) {
        switch (r) {
            case CLOCKWISE_90:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return r;
        }
    }

    public void placeInChunk(
            ServerLevelAccessor world,
            BlockPos pos,
            BoundingBox boundingBox,
            StructureProcessor paletteProcessor
    ) {
        BlockPos p = pos.offset(rotationOffset);
        structure.placeInWorld(world, p, p, new StructurePlaceSettings()
                        .setRotation(rotation)
                        .setMirror(mirror)
                        .setBoundingBox(boundingBox)
                        .addProcessor(paletteProcessor),
                world.getRandom(), Block.UPDATE_CLIENTS
        );
    }

    public BlockPos[] getEnds() {
        return ends;
    }

    public int getEndsCount() {
        return ends.length;
    }

    public BlockPos getOffsettedPos(int index) {
        return ends[index].relative(dirs[index]);
    }

    public BlockPos getPos(int index) {
        return ends[index];
    }

    public StructureCityBuilding getRotated(Rotation rotation) {
        StructureCityBuilding building = this.clone();
        building.rotation = rotation;
        building.rotationOffset = new BlockPos(building.structure.getSize()).rotate(rotation);
        int x = building.rotationOffset.getX();
        int z = building.rotationOffset.getZ();
        if (x < 0)
            x = -x - 1;
        else
            x = 0;
        if (z < 0)
            z = -z - 1;
        else
            z = 0;
        building.rotationOffset = new BlockPos(x, 0, z);
        for (int i = 0; i < building.dirs.length; i++) {
            building.dirs[i] = rotated(building.dirs[i], rotation);
            building.ends[i] = building.ends[i].rotate(rotation).offset(building.rotationOffset);
        }
        building.bb.rotate(rotation);
        building.offsetY = this.offsetY;
        return building;
    }

    public StructureCityBuilding getRandomRotated(RandomSource random) {
        return getRotated(Rotation.values()[random.nextInt(4)]);
    }

    public StructureCityBuilding clone() {
        return new StructureCityBuilding(location, structure);
    }

    private Direction rotated(Direction dir, Rotation rotation) {
        Direction f;
        switch (rotation) {
            case CLOCKWISE_90:
                f = dir.getClockWise();
                break;
            case CLOCKWISE_180:
                f = dir.getOpposite();
                break;
            case COUNTERCLOCKWISE_90:
                f = dir.getCounterClockWise();
                break;
            default:
                f = dir;
                break;
        }
        return f;
    }

    public int getYOffset() {
        return offsetY;
    }

    public Rotation getRotation() {
        return rotation;
    }

    /*
     * private static StructureProcessor makeProcessorReplace() { return new
     * RuleStructureProcessor( ImmutableList.of( new StructureProcessorRule( new
     * BlockMatchRuleTest(Blocks.STRUCTURE_BLOCK), AlwaysTrueRuleTest.INSTANCE,
     * Blocks.AIR.getDefaultState() ) ) ); }
     */

    public BoundingBox getBoundingBox(BlockPos pos) {
        return structure.getBoundingBox(
                new StructurePlaceSettings().setRotation(this.rotation).setMirror(mirror),
                pos.offset(rotationOffset)
        );
    }

    @Override
    public StructureCityBuilding setRotation(Rotation rotation) {
        this.rotation = rotation;
        rotationOffset = new BlockPos(structure.getSize()).rotate(rotation);
        return this;
    }
}
