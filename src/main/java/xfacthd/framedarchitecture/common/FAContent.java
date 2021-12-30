package xfacthd.framedarchitecture.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.block.*;
import xfacthd.framedarchitecture.common.blockentity.FramedArchitectureBlockEntity;
import xfacthd.framedarchitecture.common.data.BlockType;
import xfacthd.framedblocks.api.block.FramedBlockEntity;
import xfacthd.framedblocks.api.block.IFramedBlock;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FramedArchitecture.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAContent
{
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FramedArchitecture.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BE_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FramedArchitecture.MODID);

    /** BLOCKS */
    public static final RegistryObject<Block> blockFramedSmallPillarSocket = registerBlock(FramedPillarSocketBlock::new, BlockType.FRAMED_SMALL_PILLAR_SOCKET);
    public static final RegistryObject<Block> blockFramedPillar = registerBlock(FramedBlock::new, BlockType.FRAMED_PILLAR);
    public static final RegistryObject<Block> blockFramedPillarSocket = registerBlock(FramedPillarSocketBlock::new, BlockType.FRAMED_PILLAR_SOCKET);
    public static final RegistryObject<Block> blockFramedSmallFlutedPillar = registerBlock(FramedBlock::new, BlockType.FRAMED_SMALL_FLUTED_PILLAR);
    public static final RegistryObject<Block> blockFramedSmallFlutedPillarSocket = registerBlock(FramedPillarSocketBlock::new, BlockType.FRAMED_SMALL_FLUTED_PILLAR_SOCKET);
    public static final RegistryObject<Block> blockFramedFlutedPillar = registerBlock(FramedBlock::new, BlockType.FRAMED_FLUTED_PILLAR);
    public static final RegistryObject<Block> blockFramedFlutedPillarSocket = registerBlock(FramedPillarSocketBlock::new, BlockType.FRAMED_FLUTED_PILLAR_SOCKET);
    public static final RegistryObject<Block> blockFramedLargeFlutedPillar = registerBlock(FramedBlock::new, BlockType.FRAMED_LARGE_FLUTED_PILLAR);
    public static final RegistryObject<Block> blockFramedGrate = registerBlock(FramedGrateBlock::new, BlockType.FRAMED_GRATE);
    public static final RegistryObject<Block> blockFramedBorderedGrate = registerBlock(FramedGrateBlock::new, BlockType.FRAMED_BORDERED_GRATE);

    /** TILE ENTITY TYPES */
    public static final RegistryObject<BlockEntityType<FramedBlockEntity>> blockEntityTypeFramedBlock = createBlockEntityType(
            FramedArchitectureBlockEntity::new,
            "framed_tile",
            getDefaultEntityBlocks()
    );

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static Collection<RegistryObject<Block>> getRegisteredBlocks() { return BLOCKS.getEntries(); }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .filter(block -> block instanceof IFramedBlock)
                .filter(block -> ((IFramedBlock)block).getBlockType().hasBlockItem())
                .map(block -> ((IFramedBlock)block).createItemBlock())
                .forEach(registry::register);
    }

    private static Supplier<Block[]> getDefaultEntityBlocks()
    {
        //noinspection SuspiciousToArrayCall
        return () -> BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .filter(block -> block instanceof IFramedBlock)
                .filter(block -> !((IFramedBlock)block).getBlockType().hasSpecialTile())
                .toArray(Block[]::new);
    }

    private static RegistryObject<Block> registerBlock(Function<BlockType, Block> blockFactory, BlockType type)
    {
        return registerBlock(() -> blockFactory.apply(type), type);
    }

    private static RegistryObject<Block> registerBlock(Supplier<Block> blockFactory, BlockType type)
    {
        return BLOCKS.register(type.getName(), blockFactory);
    }

    @SafeVarargs
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> createBlockEntityType(
            BlockEntityType.BlockEntitySupplier<T> factory, String name, RegistryObject<Block>... roBlocks
    )
    {
        return createBlockEntityType(factory, name, () -> Arrays.stream(roBlocks).map(RegistryObject::get).toArray(Block[]::new));
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> createBlockEntityType(
            BlockEntityType.BlockEntitySupplier<T> factory, String name, Supplier<Block[]> blocks
    )
    {
        return BE_TYPES.register(name, () ->
        {
            //noinspection ConstantConditions
            return BlockEntityType.Builder.of(factory, blocks.get()).build(null);
        });
    }
}
