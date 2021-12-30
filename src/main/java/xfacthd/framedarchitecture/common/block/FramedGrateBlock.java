package xfacthd.framedarchitecture.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedarchitecture.common.data.*;
import xfacthd.framedblocks.api.util.Utils;

public class FramedGrateBlock extends FramedBlock
{
    public FramedGrateBlock(BlockType type) { super(type); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AXIS, PropertyHolder.GRATE_POSITION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = defaultBlockState();
        Vec3 loc = Utils.fraction(context.getClickLocation());

        switch (context.getClickedFace())
        {
            case UP, DOWN ->
            {
                Direction.Axis axis = context.getHorizontalDirection().getAxis();
                state = state.setValue(BlockStateProperties.AXIS, axis);

                double coord = axis == Direction.Axis.X ? loc.x : loc.z;
                GratePosition pos = GratePosition.NEGATIVE;
                if (coord > (2D/3D))
                {
                    pos = GratePosition.POSITIVE;
                }
                else if (coord > (1D/3D))
                {
                    pos = GratePosition.CENTER;
                }
                state = state.setValue(PropertyHolder.GRATE_POSITION, pos);
            }
            case NORTH, EAST, SOUTH, WEST ->
            {
                state = state.setValue(BlockStateProperties.AXIS, Direction.Axis.Y);

                GratePosition pos = GratePosition.NEGATIVE;
                if (loc.y > (2D/3D))
                {
                    pos = GratePosition.POSITIVE;
                }
                else if (loc.y > (1D/3D))
                {
                    pos = GratePosition.CENTER;
                }
                state = state.setValue(PropertyHolder.GRATE_POSITION, pos);
            }
        }

        return state;
    }

    public static ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();

        for (BlockState state : states)
        {
            int axisMin = 0;
            int axisMax = 2;

            GratePosition pos = state.getValue(PropertyHolder.GRATE_POSITION);
            if (pos == GratePosition.CENTER)
            {
                axisMin += 7;
                axisMax += 7;
            }
            else if (pos == GratePosition.POSITIVE)
            {
                axisMin += 14;
                axisMax += 14;
            }

            VoxelShape shape = switch (state.getValue(BlockStateProperties.AXIS))
            {
                case X -> box(axisMin, 0, 0, axisMax, 16, 16);
                case Y -> box(0, axisMin, 0, 16, axisMax, 16);
                case Z -> box(0, 0, axisMin, 16, 16, axisMax);
            };
            builder.put(state, shape);
        }

        return builder.build();
    }
}
