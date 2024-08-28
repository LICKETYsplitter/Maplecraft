package net.licketysplitter.maplecraft;

import com.mojang.logging.LogUtils;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.entity.client.DeerRenderer;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.particle.ModParticles;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
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
import org.slf4j.Logger;

@Mod(MaplecraftMod.MOD_ID)
public class MaplecraftMod {
    public static final String MOD_ID = "maplecraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MaplecraftMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
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
        }
    }
}
