package xfacthd.framedarchitecture.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.client.model.*;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.util.client.ClientUtils;

import java.util.Map;

@Mod.EventBusSubscriber(modid = FramedArchitecture.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FAClient
{
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event)
    {
        FAContent.getRegisteredBlocks()
                .stream()
                .map(RegistryObject::get)
                .filter(block -> block instanceof IFramedBlock)
                .forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, type ->
                        type == RenderType.solid() ||
                                type == RenderType.cutout() ||
                                type == RenderType.cutoutMipped() ||
                                type == RenderType.translucent()
                ));
    }

    @SubscribeEvent
    public static void onModelsLoaded(final ModelBakeEvent event)
    {
        Map<ResourceLocation, BakedModel> registry = event.getModelRegistry();

        ClientUtils.replaceModels(FAContent.blockFramedSmallPillarSocket, registry, FramedPillarSocketModel::small, FramedPillarSocketModel::small);
        ClientUtils.replaceModels(FAContent.blockFramedPillar, registry, FramedPillarModel::new);
        ClientUtils.replaceModels(FAContent.blockFramedPillarSocket, registry, FramedPillarSocketModel::medium, FramedPillarSocketModel::medium);
        ClientUtils.replaceModels(FAContent.blockFramedSmallFlutedPillar, registry, FramedFlutedPillarModel::small, FramedFlutedPillarModel::small);
        ClientUtils.replaceModels(FAContent.blockFramedSmallFlutedPillarSocket, registry, FramedFlutedPillarSocketModel::small, FramedFlutedPillarSocketModel::small);
        ClientUtils.replaceModels(FAContent.blockFramedFlutedPillar, registry, FramedFlutedPillarModel::medium, FramedFlutedPillarModel::medium);
        ClientUtils.replaceModels(FAContent.blockFramedFlutedPillarSocket, registry, FramedFlutedPillarSocketModel::medium, FramedFlutedPillarSocketModel::medium);
        ClientUtils.replaceModels(FAContent.blockFramedLargeFlutedPillar, registry, FramedFlutedPillarModel::large, FramedFlutedPillarModel::large);
        ClientUtils.replaceModels(FAContent.blockFramedGrate, registry, FramedGrateModel::normal, FramedGrateModel::normal);
        ClientUtils.replaceModels(FAContent.blockFramedBorderedGrate, registry, FramedGrateModel::bordered, FramedGrateModel::bordered);
    }
}
