package xfacthd.framedarchitecture.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.block.FramedBlockEntity;

public class FramedArchitectureBlockEntity extends FramedBlockEntity
{
    public FramedArchitectureBlockEntity(BlockPos pos, BlockState state)
    {
        super(FAContent.blockEntityTypeFramedBlock.get(), pos, state);
    }
}
