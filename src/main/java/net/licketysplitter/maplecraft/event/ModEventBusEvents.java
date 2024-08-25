package net.licketysplitter.maplecraft.event;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.entity.client.DeerModel;
import net.licketysplitter.maplecraft.entity.client.ModModelLayers;
import net.licketysplitter.maplecraft.entity.custom.DeerEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaplecraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.DEER, DeerModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DEER.get(), DeerEntity.createAttributes().build());
    }
}
