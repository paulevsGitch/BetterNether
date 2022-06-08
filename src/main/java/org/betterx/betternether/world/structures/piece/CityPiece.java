package org.betterx.betternether.world.structures.piece;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherStructurePieces;
import org.betterx.betternether.world.structures.city.BuildingStructureProcessor;
import org.betterx.betternether.world.structures.city.StructureCityBuilding;
import org.betterx.betternether.world.structures.city.palette.CityPalette;
import org.betterx.betternether.world.structures.city.palette.Palettes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public class CityPiece extends CustomPiece {
    private final MutableBlockPos POS = new MutableBlockPos();
    private final StructureProcessor paletteProcessor;
    private final StructureCityBuilding building;
    private final CityPalette palette;
    private final BlockPos pos;

    public CityPiece(StructureCityBuilding building, BlockPos pos, int id, CityPalette palette) {
        super(NetherStructurePieces.NETHER_CITY_PIECE, id, building.getBoundingBox(pos));
        this.building = building;
        this.pos = pos.immutable();
        this.boundingBox = building.getBoundingBox(pos);
        this.palette = palette;
        this.paletteProcessor = new BuildingStructureProcessor(palette);
    }

    public CityPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(NetherStructurePieces.NETHER_CITY_PIECE, tag);
        this.building = new StructureCityBuilding(tag.getString("building"), tag.getInt("offset"))
                .getRotated(Rotation.values()[tag.getInt("rotation")]);
        this.building.setMirror(Mirror.values()[tag.getInt("mirror")]);
        this.pos = NbtUtils.readBlockPos(tag.getCompound("pos"));
        this.boundingBox = building.getBoundingBox(pos);
        this.palette = Palettes.getPalette(tag.getString("palette"));
        this.paletteProcessor = new BuildingStructureProcessor(palette);
    }

    @Override
    protected void addAdditionalSaveData(
            StructurePieceSerializationContext structurePieceSerializationContext,
            CompoundTag tag
    ) {
        tag.putString("building", building.getName());
        tag.putInt("rotation", building.getRotation().ordinal());
        tag.putInt("mirror", building.getMirror().ordinal());
        tag.putInt("offset", building.getYOffset());
        tag.put("pos", NbtUtils.writeBlockPos(pos));
        tag.putString("palette", palette.getName());
    }

    @Override
    public void postProcess(
            WorldGenLevel world,
            StructureManager arg,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BoundingBox blockBox,
            ChunkPos chunkPos,
            BlockPos blockPos
    ) {
        if (!this.boundingBox.intersects(blockBox))
            return;


        BoundingBox clamped = new BoundingBox(
                boundingBox.minX(),
                boundingBox.minY(),
                boundingBox.minZ(),
                boundingBox.maxX(),
                boundingBox.maxY(),
                boundingBox.maxZ()
        );
        //clamped.encompass(blockBox);
        int cminZ = Math.max(clamped.minZ(), blockBox.minZ());
        int cmaxZ = Math.min(clamped.maxZ(), blockBox.maxZ());

        int cminX = Math.max(clamped.minX(), blockBox.minX());
        int cmaxX = Math.min(clamped.maxX(), blockBox.maxX());

        int cminY = Math.max(clamped.minY(), blockBox.minY());
        int cmaxY = Math.min(clamped.maxY(), blockBox.maxY());
        clamped = new BoundingBox(cminX, cminY, cminZ, cmaxX, cmaxY, cmaxZ);

        building.placeInChunk(world, pos, clamped, paletteProcessor);

        ChunkAccess chunk = world.getChunk(chunkPos.x, chunkPos.z);

        BlockState state;
        for (int x = clamped.minX(); x <= clamped.maxX(); x++)
            for (int z = clamped.minZ(); z <= clamped.maxZ(); z++) {
                POS.set(x, clamped.minY(), z);
                state = world.getBlockState(POS);
                if (!state.isAir() && state.isCollisionShapeFullBlock(world, POS)) {
                    for (int y = clamped.minY() - 1; y > 4; y--) {
                        POS.setY(y);
                        BlocksHelper.setWithoutUpdate(world, POS, state);
                        if (BlocksHelper.isNetherGroundMagma(world.getBlockState(POS.below())))
                            break;
                    }
                }

                // POS.set(x - clamped.minX, clamped.minY - clamped.minZ, z);
                for (int y = clamped.minY(); y <= clamped.maxY(); y++) {
                    POS.setY(y);
                    chunk.markPosForPostprocessing(POS);
                }
            }
    }
}
