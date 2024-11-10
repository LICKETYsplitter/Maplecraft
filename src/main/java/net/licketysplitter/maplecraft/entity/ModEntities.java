package net.licketysplitter.maplecraft.entity;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.entity.custom.DeerEntity;
import net.licketysplitter.maplecraft.entity.custom.Williwaw;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MaplecraftMod.MOD_ID);

    public static final RegistryObject<EntityType<DeerEntity>> DEER =
            ENTITY_TYPE.register("deer", () -> EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE)
                    .sized(1,1).build("deer"));

    public static final RegistryObject<EntityType<Williwaw>> WILLIWAW =
            ENTITY_TYPE.register("williwaw", () -> EntityType.Builder.of(Williwaw::new, MobCategory.MONSTER)
                    .sized(1,1).build("williwaw"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPE.register(eventBus);
    }
}
