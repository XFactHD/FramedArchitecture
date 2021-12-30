package xfacthd.framedarchitecture.common.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.FAContent;

public class FramedLanguageProvider extends LanguageProvider
{
    public FramedLanguageProvider(DataGenerator gen) { super(gen, FramedArchitecture.MODID, "en_us"); }

    @Override
    protected void addTranslations()
    {
        addBlock(FAContent.blockFramedSmallPillarSocket, "Framed Small Pillar Socket");
        addBlock(FAContent.blockFramedPillar, "Framed Pillar");
        addBlock(FAContent.blockFramedPillarSocket, "Framed Pillar Socket");
        addBlock(FAContent.blockFramedSmallFlutedPillar, "Framed Small Fluted Pillar");
        addBlock(FAContent.blockFramedSmallFlutedPillarSocket, "Framed Small Fluted Pillar Socket");
        addBlock(FAContent.blockFramedFlutedPillar, "Framed Fluted Pillar");
        addBlock(FAContent.blockFramedFlutedPillarSocket, "Framed Fluted Pillar Socket");
        addBlock(FAContent.blockFramedLargeFlutedPillar, "Framed Large Fluted Pillar");
        addBlock(FAContent.blockFramedGrate, "Framed Grate");
        addBlock(FAContent.blockFramedBorderedGrate, "Framed Bordered Grate");

        add(FramedArchitecture.FRAMED_TAB.getDisplayName().getString(), "FramedArchitecture");
    }
}
