package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MaplecraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.MAPLE_SYRUP_BOTTLE);
        simpleItem(ModItems.SUGAR_GLASS_SHARD);

        simpleItem(ModItems.VENISON);
        simpleItem(ModItems.COOKED_VENISON);

        simpleItem(ModItems.TWO_POINT_ANTLER, "antler");
        simpleItem(ModItems.FOUR_POINT_ANTLER, "antler");
        simpleItem(ModItems.SIX_POINT_ANTLER, "antler");
        simpleItem(ModItems.EIGHT_POINT_ANTLER, "antler");

        simpleTranslucentBlockItem(ModBlocks.MAPLE_DOOR);
        simpleBlockItem(ModBlocks.APPLE_DOOR);

        fenceItem(ModBlocks.MAPLE_FENCE, ModBlocks.MAPLE_PLANKS);
        buttonItem(ModBlocks.MAPLE_BUTTON, ModBlocks.MAPLE_PLANKS);

        fenceItem(ModBlocks.APPLE_FENCE, ModBlocks.APPLE_PLANKS);
        buttonItem(ModBlocks.APPLE_BUTTON, ModBlocks.APPLE_PLANKS);


        withExistingParent(ModItems.DEER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.WILLIWAW_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        saplingItem(ModBlocks.RED_MAPLE_SAPLING);
        saplingItem(ModBlocks.SUGAR_MAPLE_SAPLING);
        saplingItem(ModBlocks.APPLE_SAPLING);

        simpleItem(ModItems.SAP_BUCKET);
        simpleItem(ModItems.GREEN_APPLE);
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "item/" + item.getId().getPath()));

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item, String textureName){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "item/" + textureName));
    }

    private void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    private void fenceItem(RegistryObject<Block> block,RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private void buttonItem(RegistryObject<Block> block,RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> block){
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "item/" + block.getId().getPath()));
    }

    private ItemModelBuilder simpleTranslucentBlockItem(RegistryObject<Block> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "item/" + item.getId().getPath())).renderType("translucent");
    }
}
