package net.licketysplitter.maplecraft.util;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MaplecraftMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAPLECRAFT = CREATIVE_MODE_TABS.register("maplecraft",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE.get()))
                    .title(Component.translatable("creativetab.maplecraft"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SUGAR_GLASS_SHARD.get());
                        output.accept(ModBlocks.SUGAR_GLASS.get());

                        output.accept(ModItems.TWO_POINT_ANTLER.get());
                        output.accept(ModItems.FOUR_POINT_ANTLER.get());
                        output.accept(ModItems.SIX_POINT_ANTLER.get());
                        output.accept(ModItems.EIGHT_POINT_ANTLER.get());

                        output.accept(ModBlocks.SUGAR_MAPLE_LEAVES.get());
                        output.accept(ModBlocks.RED_MAPLE_LEAVES.get());
                        output.accept(ModBlocks.APPLE_LEAVES.get());
                        output.accept(ModBlocks.FLOWERING_APPLE_LEAVES.get());

                        output.accept(ModBlocks.SUGAR_MAPLE_SAPLING.get());
                        output.accept(ModBlocks.RED_MAPLE_SAPLING.get());
                        output.accept(ModBlocks.APPLE_SAPLING.get());

                        output.accept(ModBlocks.MAPLE_SYRUP_BLOCK.get());

                        output.accept(ModBlocks.PILE_OF_LEAVES.get());
                        output.accept(ModBlocks.POISON_IVY.get());
                        output.accept(ModBlocks.ASTER.get());
                        output.accept(ModBlocks.CATTAIL.get());

                        output.accept(ModBlocks.SINKING_MUD.get());

                        output.accept(ModBlocks.MAPLE_SYRUP_BLOCK.get());

                        output.accept(ModItems.MAPLE_SYRUP_BOTTLE.get());
                        output.accept(ModItems.VENISON.get());
                        output.accept(ModItems.COOKED_VENISON.get());
                        output.accept(ModItems.GREEN_APPLE.get());

                        output.accept(ModBlocks.MAPLE_LOG.get());
                        output.accept(ModBlocks.MAPLE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_MAPLE_LOG.get());
                        output.accept(ModBlocks.STRIPPED_MAPLE_WOOD.get());

                        output.accept(ModBlocks.MAPLE_PLANKS.get());
                        output.accept(ModBlocks.MAPLE_STAIRS.get());
                        output.accept(ModBlocks.MAPLE_SLAB.get());
                        output.accept(ModBlocks.MAPLE_FENCE.get());
                        output.accept(ModBlocks.MAPLE_FENCE_GATE.get());
                        output.accept(ModBlocks.MAPLE_DOOR.get());
                        output.accept(ModBlocks.MAPLE_TRAPDOOR.get());
                        output.accept(ModBlocks.MAPLE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.MAPLE_BUTTON.get());

                        output.accept(ModBlocks.APPLE_LOG.get());
                        output.accept(ModBlocks.APPLE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_APPLE_LOG.get());
                        output.accept(ModBlocks.STRIPPED_APPLE_WOOD.get());

                        output.accept(ModBlocks.APPLE_PLANKS.get());
                        output.accept(ModBlocks.APPLE_STAIRS.get());
                        output.accept(ModBlocks.APPLE_SLAB.get());
                        output.accept(ModBlocks.APPLE_FENCE.get());
                        output.accept(ModBlocks.APPLE_FENCE_GATE.get());
                        output.accept(ModBlocks.APPLE_DOOR.get());
                        output.accept(ModBlocks.APPLE_TRAPDOOR.get());
                        output.accept(ModBlocks.APPLE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.APPLE_BUTTON.get());

                        output.accept(ModItems.SAP_BUCKET.get());

                        output.accept(ModBlocks.EVAPORATOR.get());

                        output.accept(ModItems.DEER_SPAWN_EGG.get());
                        output.accept(ModItems.WILLIWAW_SPAWN_EGG.get());
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
