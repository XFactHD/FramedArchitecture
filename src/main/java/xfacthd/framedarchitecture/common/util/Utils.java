package xfacthd.framedarchitecture.common.util;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class Utils
{
    public static Supplier<Block> memoizeBlock(String registryName)
    {
        return Suppliers.memoize(() -> block(registryName));
    }

    public static Block block(String registryName)
    {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(registryName)));
    }
}
