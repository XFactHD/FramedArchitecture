package xfacthd.framedarchitecture.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.datagen.providers.*;

@Mod.EventBusSubscriber(modid = FramedArchitecture.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneratorHandler
{
    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        gen.addProvider(new FramedBlockStateProvider(gen, fileHelper));
        gen.addProvider(new FramedLootTableProvider(gen));
        gen.addProvider(new FramedRecipeProvider(gen));
        gen.addProvider(new FramedLanguageProvider(gen));
    }
}