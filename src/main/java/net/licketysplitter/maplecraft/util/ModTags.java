package net.licketysplitter.maplecraft.util;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MAPLE_LOGS = createTag("maple_logs");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MAPLE_LOGS = createTag("maple_logs");
        public static final TagKey<Item> ANTLERS = createTag("antlers");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
        }

    }

    public static class Entities {
        public static final TagKey<EntityType<?>> CAN_RUSTLE_LEAVES =createTag("can_rustle_leaves");

        private static TagKey<EntityType<?>> createTag(String name){
            return EntityTypeTags.create(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
        }
    }
}
