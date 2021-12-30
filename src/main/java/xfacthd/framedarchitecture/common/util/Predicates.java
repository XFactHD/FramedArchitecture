package xfacthd.framedarchitecture.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import xfacthd.framedblocks.api.util.*;

import java.util.function.Supplier;

public class Predicates
{
    public static final CtmPredicate SOCKET_CTM_PREDICATE = (state, dir) ->
    {
        boolean top = state.getValue(FramedProperties.TOP);
        return top ? dir == Direction.UP : dir == Direction.DOWN;
    };

    public static SideSkipPredicate pillarSkipPredicate(Supplier<Supplier<Block>> socket)
    {
        return (level, pos, state, adjState, side) ->
        {
            if (side.getAxis() != Direction.Axis.Y) { return false; }

            if (adjState.getBlock() == state.getBlock())
            {
                return SideSkipPredicate.compareState(level, pos, side);
            }
            else if (adjState.getBlock() == socket.get().get())
            {
                boolean top = adjState.getValue(FramedProperties.TOP);
                if ((side == Direction.UP) != top)
                {
                    return SideSkipPredicate.compareState(level, pos, side);
                }
            }
            return false;
        };
    }

    public static SideSkipPredicate socketSkipPredicate(Supplier<Supplier<Block>> pillar)
    {
        return (level, pos, state, adjState, side) ->
        {
            if (side.getAxis() != Direction.Axis.Y) { return false; }

            boolean top = state.getValue(FramedProperties.TOP);
            if ((side == Direction.UP) != top)
            {
                if (adjState.getBlock() == state.getBlock())
                {
                    boolean adjTop = adjState.getValue(FramedProperties.TOP);
                    return adjTop != top && SideSkipPredicate.compareState(level, pos, side);
                }
                else if (adjState.getBlock() == pillar.get())
                {
                    return SideSkipPredicate.compareState(level, pos, side);
                }
            }

            return false;
        };
    }
}
