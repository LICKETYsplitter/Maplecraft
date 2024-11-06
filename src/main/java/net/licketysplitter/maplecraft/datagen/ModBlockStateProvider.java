package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MaplecraftMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        translucentBlocks(ModBlocks.MAPLE_SYRUP_BLOCK);
        translucentBlocks(ModBlocks.SUGAR_GLASS);

        logBlock((RotatedPillarBlock) ModBlocks.MAPLE_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.MAPLE_WOOD.get(), blockTexture(ModBlocks.MAPLE_LOG.get()),
                blockTexture(ModBlocks.MAPLE_LOG.get()));

        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG.get(), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "block/stripped_maple_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD.get(), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()));

        blockItem(ModBlocks.MAPLE_LOG);
        blockItem(ModBlocks.MAPLE_WOOD);
        blockItem(ModBlocks.STRIPPED_MAPLE_LOG);
        blockItem(ModBlocks.STRIPPED_MAPLE_WOOD);

        blockWithItem(ModBlocks.MAPLE_PLANKS);

        leavesBlocks(ModBlocks.SUGAR_MAPLE_LEAVES);
        leavesBlocks(ModBlocks.RED_MAPLE_LEAVES);

        saplingBlock(ModBlocks.RED_MAPLE_SAPLING);
        saplingBlock(ModBlocks.SUGAR_MAPLE_SAPLING);


        stairsBlock(((StairBlock) ModBlocks.MAPLE_STAIRS.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.MAPLE_SLAB.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        buttonBlock(((ButtonBlock) ModBlocks.MAPLE_BUTTON.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.MAPLE_PRESSURE_PLATE.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.MAPLE_FENCE.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.MAPLE_FENCE_GATE.get()), blockTexture(ModBlocks.MAPLE_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.MAPLE_DOOR.get()), modLoc("block/maple_door_bottom"), modLoc("block/maple_door_top"), "translucent");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.MAPLE_TRAPDOOR.get()), modLoc("block/maple_trapdoor"), true, "translucent");

        blockItem(ModBlocks.MAPLE_STAIRS);
        blockItem(ModBlocks.MAPLE_SLAB);
        blockItem(ModBlocks.MAPLE_PRESSURE_PLATE);
        blockItem(ModBlocks.MAPLE_FENCE_GATE);
        blockItem(ModBlocks.MAPLE_TRAPDOOR, "_bottom");

        logBlock((RotatedPillarBlock) ModBlocks.APPLE_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.APPLE_WOOD.get(), blockTexture(ModBlocks.APPLE_LOG.get()),
                blockTexture(ModBlocks.APPLE_LOG.get()));

        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_APPLE_LOG.get(), blockTexture(ModBlocks.STRIPPED_APPLE_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "block/stripped_apple_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_APPLE_WOOD.get(), blockTexture(ModBlocks.STRIPPED_APPLE_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_APPLE_LOG.get()));

        blockItem(ModBlocks.APPLE_LOG);
        blockItem(ModBlocks.APPLE_WOOD);
        blockItem(ModBlocks.STRIPPED_APPLE_LOG);
        blockItem(ModBlocks.STRIPPED_APPLE_WOOD);
        blockWithItem(ModBlocks.APPLE_PLANKS);
        stairsBlock(((StairBlock) ModBlocks.APPLE_STAIRS.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.APPLE_SLAB.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        buttonBlock(((ButtonBlock) ModBlocks.APPLE_BUTTON.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.APPLE_PRESSURE_PLATE.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        fenceBlock(((FenceBlock) ModBlocks.APPLE_FENCE.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.APPLE_FENCE_GATE.get()), blockTexture(ModBlocks.APPLE_PLANKS.get()));
        doorBlockWithRenderType(((DoorBlock) ModBlocks.APPLE_DOOR.get()), modLoc("block/apple_door_bottom"), modLoc("block/apple_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.APPLE_TRAPDOOR.get()), modLoc("block/apple_trapdoor"), true, "cutout");
        blockItem(ModBlocks.APPLE_STAIRS);
        blockItem(ModBlocks.APPLE_SLAB);
        blockItem(ModBlocks.APPLE_PRESSURE_PLATE);
        blockItem(ModBlocks.APPLE_FENCE_GATE);
        blockItem(ModBlocks.APPLE_TRAPDOOR, "_bottom");
        //leavesBlocks(ModBlocks.APPLE_LEAVES);
        saplingBlock(ModBlocks.APPLE_SAPLING);

    }

    private void leavesBlocks(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void translucentBlocks(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/cube_all"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("translucent"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(MaplecraftMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(MaplecraftMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
}
