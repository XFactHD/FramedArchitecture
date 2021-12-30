package xfacthd.framedarchitecture.common.util;

import com.google.common.base.Preconditions;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.block.IFramedBlock;

public class FACreativeTab extends CreativeModeTab
{
    public FACreativeTab(String label) { super(label); }

    @Override
    public ItemStack makeIcon() { return new ItemStack(FAContent.blockFramedSmallPillarSocket.get()); }

    @Override
    public void fillItemList(NonNullList<ItemStack> items)
    {
        super.fillItemList(items);
        items.sort((s1, s2) ->
        {
            Item itemOne = s1.getItem();
            Item itemTwo = s2.getItem();

            Preconditions.checkArgument(
                    itemOne instanceof BlockItem bi && bi.getBlock() instanceof IFramedBlock,
                    String.format("Invalid item in FramedBlocks creative tab: %s", itemOne.getRegistryName())
            );
            Preconditions.checkArgument(
                    itemTwo instanceof BlockItem bi && bi.getBlock() instanceof IFramedBlock,
                    String.format("Invalid item in FramedBlocks creative tab: %s", itemOne.getRegistryName())
            );

            Block b1 = ((BlockItem) itemOne).getBlock();
            Block b2 = ((BlockItem) itemTwo).getBlock();
            return ((IFramedBlock)b1).getBlockType().compareTo(((IFramedBlock)b2).getBlockType());
        });
    }
}
