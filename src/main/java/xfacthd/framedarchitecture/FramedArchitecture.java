package xfacthd.framedarchitecture;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedarchitecture.common.util.FACreativeTab;

@Mod(FramedArchitecture.MODID)
@Mod.EventBusSubscriber(modid = FramedArchitecture.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FramedArchitecture
{
    public static final String MODID = "framedarchitecture";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeModeTab FRAMED_TAB = new FACreativeTab("framed_architecture");

    public FramedArchitecture()
	{
        FAContent.init();
    }
}
