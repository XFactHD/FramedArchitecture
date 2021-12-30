package xfacthd.framedarchitecture.common.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.FAContent;

public class FramedBlockStateProvider extends BlockStateProvider
{
    private final ResourceLocation TEXTURE;

    public FramedBlockStateProvider(DataGenerator gen, ExistingFileHelper fileHelper)
    {
        super(gen, FramedArchitecture.MODID, fileHelper);
        TEXTURE = new ResourceLocation("framedblocks", "block/framed_block");
    }

    @Override
    protected void registerStatesAndModels()
    {
        ModelFile cube = models().getExistingFile(new ResourceLocation("framedblocks", "framed_cube"));

        simpleBlockWithItem(FAContent.blockFramedSmallPillarSocket, cube);
        simpleBlock(FAContent.blockFramedPillar.get(), cube);
        simpleBlockWithItem(FAContent.blockFramedPillarSocket, cube);
        simpleBlockWithItem(FAContent.blockFramedSmallFlutedPillar, cube);
        simpleBlockWithItem(FAContent.blockFramedSmallFlutedPillarSocket, cube);
        simpleBlockWithItem(FAContent.blockFramedFlutedPillar, cube);
        simpleBlockWithItem(FAContent.blockFramedFlutedPillarSocket, cube);
        simpleBlockWithItem(FAContent.blockFramedLargeFlutedPillar, cube);
        simpleBlockWithItem(FAContent.blockFramedGrate, cube);
        simpleBlockWithItem(FAContent.blockFramedBorderedGrate, cube);
    }



    private void simpleBlockWithItem(RegistryObject<Block> block, ModelFile model)
    {
        simpleBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
}
