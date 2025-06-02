package net.licketysplitter.maplecraft;

import com.mojang.logging.LogUtils;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.block.entity.ModBlockEntities;
import net.licketysplitter.maplecraft.block.entity.custom.EvaporatorBlockEntity;
import net.licketysplitter.maplecraft.effect.ModEffects;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.entity.client.DeerRenderer;
import net.licketysplitter.maplecraft.entity.client.williwaw.WilliwawRenderer;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.particle.ModParticles;
import net.licketysplitter.maplecraft.screen.EvaporatorScreen;
import net.licketysplitter.maplecraft.screen.ModMenuTypes;
import net.licketysplitter.maplecraft.util.ModCreativeModeTabs;
import net.licketysplitter.maplecraft.worldgen.biome.ModBiomeColors;
import net.licketysplitter.maplecraft.worldgen.biome.ModFeature;
import net.licketysplitter.maplecraft.worldgen.biome.ModTerrablender;
import net.licketysplitter.maplecraft.worldgen.biome.surface.ModSurfaceRules;
import net.licketysplitter.maplecraft.worldgen.tree.ModTreePlacements;
import net.licketysplitter.maplecraft.worldgen.tree.ModTrunkPlacerTypes;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

import java.awt.*;

@Mod(MaplecraftMod.MOD_ID)
public class MaplecraftMod {
    public static final String MOD_ID = "maplecraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MaplecraftMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModParticles.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModTerrablender.registerBiomes();
        ModFeature.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);
        ModEffects.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->{
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SUGAR_GLASS_SHARD);

            event.accept(ModItems.TWO_POINT_ANTLER);
            event.accept(ModItems.FOUR_POINT_ANTLER);
            event.accept(ModItems.SIX_POINT_ANTLER);
            event.accept(ModItems.EIGHT_POINT_ANTLER);
        }
        if(event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS){
            event.accept(ModBlocks.SUGAR_GLASS);
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.SUGAR_MAPLE_LEAVES);
            event.accept(ModBlocks.RED_MAPLE_LEAVES);

            event.accept(ModBlocks.SUGAR_MAPLE_SAPLING);
            event.accept(ModBlocks.RED_MAPLE_SAPLING);

            event.accept(ModBlocks.MAPLE_SYRUP_BLOCK);

            event.accept(ModBlocks.PILE_OF_LEAVES);
            event.accept(ModBlocks.POISON_IVY);
            event.accept(ModBlocks.ASTER);
            event.accept(ModBlocks.CATTAIL);
        }
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(ModBlocks.MAPLE_SYRUP_BLOCK);
        }
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.MAPLE_SYRUP_BOTTLE);
            event.accept(ModItems.VENISON);
            event.accept(ModItems.COOKED_VENISON);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.MAPLE_LOG);
            event.accept(ModBlocks.MAPLE_WOOD);
            event.accept(ModBlocks.STRIPPED_MAPLE_LOG);
            event.accept(ModBlocks.STRIPPED_MAPLE_WOOD);

            event.accept(ModBlocks.MAPLE_PLANKS);
            event.accept(ModBlocks.MAPLE_STAIRS);
            event.accept(ModBlocks.MAPLE_SLAB);
            event.accept(ModBlocks.MAPLE_FENCE);
            event.accept(ModBlocks.MAPLE_FENCE_GATE);
            event.accept(ModBlocks.MAPLE_DOOR);
            event.accept(ModBlocks.MAPLE_TRAPDOOR);
            event.accept(ModBlocks.MAPLE_PRESSURE_PLATE);
            event.accept(ModBlocks.MAPLE_BUTTON);
        }
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
            event.accept(ModItems.DEER_SPAWN_EGG);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.SAP_BUCKET);
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModBlocks.EVAPORATOR);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.DEER.get(), DeerRenderer::new);
            EntityRenderers.register(ModEntities.WILLIWAW.get(), WilliwawRenderer::new);
            MenuScreens.register(ModMenuTypes.EVAPORATOR_MENU.get(), EvaporatorScreen::new);
        }

        @SubscribeEvent
        public static void registerColorResolver(RegisterColorHandlersEvent.ColorResolvers event){
            event.register(ModBiomeColors.BIRCH_COLOR_RESOLVER);
            event.register(ModBiomeColors.EVERGREEN_COLOR_RESOLVER);
        }

        @SubscribeEvent
        public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event){
            event.register(new BlockColor() {
                @Override
                public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
                    if (pLevel != null && pPos != null) {
                        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

                        if (blockEntity instanceof EvaporatorBlockEntity) {
                            int white = 255;
                            int red = 113;
                            int green = 57;
                            int blue = 9;
                            float progress = ((EvaporatorBlockEntity) blockEntity).getAccumulatedProgress();

                            red = white - (int)((white - red) * progress);
                            green = white - (int)((white - green) * progress);
                            blue = white - (int)((white - blue) * progress);

                            Color returnColor = new Color(red, green, blue);
                            return returnColor.hashCode();
                        }
                    }
                    return 0xFFFFFF;
                }
            }, ModBlocks.EVAPORATOR.get());

            /*
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.getDefaultColor(), ModBlocks.PILE_OF_LEAVES.get());

             */
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? ModBiomeColors.getBirchColor(pLevel, pPos) : FoliageColor.getBirchColor(), Blocks.BIRCH_LEAVES);

            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? ModBiomeColors.getEvergreenColor(pLevel, pPos) : FoliageColor.getEvergreenColor(), Blocks.SPRUCE_LEAVES);

            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.getDefaultColor(), ModBlocks.APPLE_LEAVES.get());
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.getDefaultColor(), ModBlocks.FLOWERING_APPLE_LEAVES.get());
        }

        @SubscribeEvent
        public static void registerColoredItems(RegisterColorHandlersEvent.Item event){
            //event.register((pStack, pTintIndex) -> FoliageColor.getDefaultColor(), ModBlocks.PILE_OF_LEAVES.get());
            event.register((pStack, pTintIndex) -> FoliageColor.getDefaultColor(), ModBlocks.APPLE_LEAVES.get());
            event.register((pStack, pTintIndex) -> FoliageColor.getDefaultColor(), ModBlocks.FLOWERING_APPLE_LEAVES.get());
        }

        @SubscribeEvent
        public static void registerNamedRenderTypes(RegisterNamedRenderTypesEvent event){
            event.register("evaporator", RenderType.cutout(), RenderType.entityTranslucent(ResourceLocation.withDefaultNamespace("textures/blocks/water_still.png")));
        }
    }
}
