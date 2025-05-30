package net.licketysplitter.maplecraft.effect;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MaplecraftMod.MOD_ID);

    public static final RegistryObject<MobEffect> ITCHY_EFFECT = MOB_EFFECTS.register("itchy",
            () -> new ItchyEffect(MobEffectCategory.HARMFUL, 0x703704)
                    .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.OXYGEN_BONUS, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
