package xfacthd.framedarchitecture.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedarchitecture.common.data.BlockType;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;

public class FramedPillarSocketBlock extends FramedBlock
{
    public FramedPillarSocketBlock(BlockType type)
    {
        super(type);
        registerDefaultState(defaultBlockState().setValue(FramedProperties.TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.TOP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = defaultBlockState();

        if (context.getClickedFace() == Direction.DOWN)
        {
            state = state.setValue(FramedProperties.TOP, true);
        }
        else if (context.getClickedFace() != Direction.UP)
        {
            Vec3 loc = Utils.fraction(context.getClickLocation());
            state = state.setValue(FramedProperties.TOP, loc.y > .5D);
        }

        return state;
    }
}
