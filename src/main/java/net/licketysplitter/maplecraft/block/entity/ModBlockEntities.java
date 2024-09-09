package net.licketysplitter.maplecraft.block.entity;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.block.entity.custom.EvaporatorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MaplecraftMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<EvaporatorBlockEntity>> EVAPORATOR_BE =
            BLOCK_ENTITIES.register("evaporator_be", () ->
                    BlockEntityType.Builder.of(EvaporatorBlockEntity::new,
                            ModBlocks.EVAPORATOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
