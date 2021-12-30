package xfacthd.framedarchitecture.common.datagen.providers;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.FAContent;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class FramedLootTableProvider extends LootTableProvider
{
    public FramedLootTableProvider(DataGenerator gen) { super(gen); }

    @Override
    public String getName() { return super.getName() + ": " + FramedArchitecture.MODID; }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext tracker) { /*NOOP*/ }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        return Collections.singletonList(Pair.of(BlockLootTable::new, LootContextParamSets.BLOCK));
    }

    private static class BlockLootTable extends BlockLoot
    {
        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            return FAContent.getRegisteredBlocks()
                    .stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

        @Override
        protected void addTables()
        {
            FAContent.getRegisteredBlocks()
                    .stream()
                    .map(RegistryObject::get)
                    .forEach(this::dropSelf);
        }
    }
}
