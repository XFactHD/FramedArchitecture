package xfacthd.framedarchitecture.common.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedarchitecture.common.block.FramedGrateBlock;
import xfacthd.framedarchitecture.common.util.*;
import xfacthd.framedarchitecture.common.util.Utils;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.api.util.*;

import java.util.Locale;

public enum BlockType implements IBlockType
{
    FRAMED_SMALL_PILLAR_SOCKET          (false, false,  true,  true, Predicates.SOCKET_CTM_PREDICATE, Predicates.socketSkipPredicate(() -> Utils.memoizeBlock("framedblocks:framed_pillar")), VoxelShapes.socketShapeGen(true)),
    FRAMED_PILLAR                       (false, false,  true,  true, CtmPredicate.FALSE, Predicates.pillarSkipPredicate(() -> FAContent.blockFramedPillarSocket), VoxelShapes.PILLAR_SHAPE),
    FRAMED_PILLAR_SOCKET                (false, false,  true,  true, Predicates.SOCKET_CTM_PREDICATE, Predicates.socketSkipPredicate(() -> FAContent.blockFramedPillar), VoxelShapes.socketShapeGen(false)),
    FRAMED_SMALL_FLUTED_PILLAR          (false, false,  true,  true, CtmPredicate.FALSE, Predicates.pillarSkipPredicate(() -> FAContent.blockFramedSmallFlutedPillarSocket), VoxelShapes.SMALL_PILLAR_SHAPE),
    FRAMED_SMALL_FLUTED_PILLAR_SOCKET   (false, false,  true,  true, Predicates.SOCKET_CTM_PREDICATE, Predicates.socketSkipPredicate(() -> FAContent.blockFramedSmallFlutedPillar), VoxelShapes.socketShapeGen(true)),
    FRAMED_FLUTED_PILLAR                (false, false,  true,  true, CtmPredicate.FALSE, Predicates.pillarSkipPredicate(() -> FAContent.blockFramedFlutedPillarSocket), VoxelShapes.PILLAR_SHAPE),
    FRAMED_FLUTED_PILLAR_SOCKET         (false, false,  true,  true, Predicates.SOCKET_CTM_PREDICATE, Predicates.socketSkipPredicate(() -> FAContent.blockFramedFlutedPillar), VoxelShapes.socketShapeGen(false)),
    FRAMED_LARGE_FLUTED_PILLAR          (false, false,  true,  true, CtmPredicate.FALSE, Predicates.pillarSkipPredicate(() -> () -> null), Shapes.block()),
    FRAMED_GRATE                        (false, false,  true,  true, CtmPredicate.FALSE, SideSkipPredicate.FALSE, FramedGrateBlock::generateShapes),
    FRAMED_BORDERED_GRATE               (false, false,  true,  true, CtmPredicate.FALSE, FramedGrateBlock.SKIP_PREDICATE, FramedGrateBlock::generateShapes);

    private final String name = toString().toLowerCase(Locale.ROOT);
    private final boolean specialHitbox;
    private final boolean specialTile;
    private final boolean waterloggable;
    private final boolean blockItem;
    private final CtmPredicate ctmPredicate;
    private final SideSkipPredicate skipPredicate;
    private final VoxelShapeGenerator shapeGen;

    BlockType(boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem)
    {
        this(specialHitbox, specialTile, waterloggable, blockItem, CtmPredicate.FALSE, SideSkipPredicate.FALSE, VoxelShapeGenerator.EMTPTY);
    }

    BlockType(boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, CtmPredicate ctmPredicate, SideSkipPredicate skipPredicate)
    {
        this(specialHitbox, specialTile, waterloggable, blockItem, ctmPredicate, skipPredicate, VoxelShapeGenerator.EMTPTY);
    }

    BlockType(boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, CtmPredicate ctmPredicate, SideSkipPredicate skipPredicate, VoxelShape shape)
    {
        this(specialHitbox, specialTile, waterloggable, blockItem, ctmPredicate, skipPredicate, VoxelShapeGenerator.singleShape(shape));
    }

    BlockType(boolean specialHitbox, boolean specialTile, boolean waterloggable, boolean blockItem, CtmPredicate ctmPredicate, SideSkipPredicate skipPredicate, VoxelShapeGenerator shapeGen)
    {
        this.specialHitbox = specialHitbox;
        this.specialTile = specialTile;
        this.waterloggable = waterloggable;
        this.blockItem = blockItem;
        this.ctmPredicate = ctmPredicate;
        this.skipPredicate = skipPredicate;
        this.shapeGen = shapeGen;
    }

    @Override
    public boolean hasSpecialHitbox() { return specialHitbox; }

    @Override
    public CtmPredicate getCtmPredicate() { return ctmPredicate; }

    @Override
    public SideSkipPredicate getSideSkipPredicate() { return skipPredicate; }

    @Override
    public ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        return shapeGen.generate(states);
    }

    @Override
    public boolean hasSpecialTile() { return specialTile; }

    @Override
    public boolean hasBlockItem() { return blockItem; }

    @Override
    public boolean supportsWaterLogging() { return waterloggable; }

    @Override
    public String getName() { return name; }

    @Override
    public int compareTo(IBlockType other)
    {
        if (!(other instanceof BlockType type))
        {
            return 0;
        }
        return compareTo(type);
    }
}
