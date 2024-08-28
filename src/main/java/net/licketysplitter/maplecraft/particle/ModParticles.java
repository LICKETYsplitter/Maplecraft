package net.licketysplitter.maplecraft.particle;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MaplecraftMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> RED_MAPLE_PARTICLES =
            PARTICLE_TYPES.register("red_maple_particles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUGAR_MAPLE_PARTICLES =
            PARTICLE_TYPES.register("sugar_maple_particles", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
