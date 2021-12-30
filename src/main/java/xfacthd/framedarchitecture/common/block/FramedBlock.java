package xfacthd.framedarchitecture.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.blockentity.FramedArchitectureBlockEntity;
import xfacthd.framedblocks.api.block.*;
import xfacthd.framedarchitecture.common.data.BlockType;

public class FramedBlock extends AbstractFramedBlock
{
    public FramedBlock(BlockType type) { this(type, IFramedBlock.createProperties()); }

    protected FramedBlock(BlockType type, Properties props) { super(type, props); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedArchitectureBlockEntity(pos, state);
    }

    @Override
    public BlockItem createItemBlock()
    {
        BlockItem item = new BlockItem(this, (new Item.Properties()).tab(FramedArchitecture.FRAMED_TAB));
        //noinspection ConstantConditions
        item.setRegistryName(getRegistryName());
        return item;
    }
}
