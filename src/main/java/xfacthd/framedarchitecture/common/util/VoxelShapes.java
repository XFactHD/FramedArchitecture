package xfacthd.framedarchitecture.common.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.api.util.VoxelShapeGenerator;

public class VoxelShapes
{
    public static final VoxelShape SMALL_PILLAR_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape PILLAR_SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    private static final VoxelShape SOCKET_TOP_SHAPE;
    private static final VoxelShape SOCKET_BOTTOM_SHAPE;
    private static final VoxelShape SOCKET_TOP_SHAPE_SMALL;
    private static final VoxelShape SOCKET_BOTTOM_SHAPE_SMALL;

    static
    {
        SOCKET_TOP_SHAPE = Shapes.or(
                Block.box(0, 12, 0, 16, 16, 16),
                Block.box(1,  8, 1, 15, 12, 15),
                Block.box(2,  0, 2, 14,  8, 14)
        ).optimize();

        SOCKET_BOTTOM_SHAPE = Shapes.or(
                Block.box(0, 0, 0, 16,  4, 16),
                Block.box(1, 4, 1, 15,  8, 15),
                Block.box(2, 8, 2, 14, 16, 14)
        ).optimize();

        SOCKET_TOP_SHAPE_SMALL = Shapes.or(
                Block.box(0, 12, 0, 16, 16, 16),
                Block.box(2,  8, 2, 14, 12, 14),
                Block.box(4,  0, 4, 12,  8, 12)
        ).optimize();

        SOCKET_BOTTOM_SHAPE_SMALL = Shapes.or(
                Block.box(0, 0, 0, 16,  4, 16),
                Block.box(2, 4, 2, 14,  8, 14),
                Block.box(4, 8, 4, 12, 16, 12)
        ).optimize();
    }

    public static VoxelShapeGenerator socketShapeGen(boolean small)
    {
        return states ->
        {
            ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

            VoxelShape topShape = small ? SOCKET_TOP_SHAPE_SMALL : SOCKET_TOP_SHAPE;
            VoxelShape bottomShape = small ? SOCKET_BOTTOM_SHAPE_SMALL : SOCKET_BOTTOM_SHAPE;

            states.forEach(state ->
            {
                boolean top = state.getValue(FramedProperties.TOP);
                builder.put(state, top ? topShape : bottomShape);
            });

            return builder.build();
        };
    }
}
