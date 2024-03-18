package com.peeko32213.orenamental.datagen;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.peeko32213.orenamental.Orenamental;
import com.peeko32213.orenamental.blocks.*;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.peeko32213.orenamental.Orenamental.prefix;
import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class OBlockStateGenerator extends BlockStateProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Set<Block> blockSet = new HashSet<>();
    public OBlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Orenamental.MODID, exFileHelper);
    }

    static final Map<BlockFamily.Variant, BiConsumer<OBlockStateGenerator, Block>> SHAPE_CONSUMERS =
            ImmutableMap.<BlockFamily.Variant, BiConsumer<OBlockStateGenerator, Block>>builder()
                    .put(BlockFamily.Variant.BUTTON, OBlockStateGenerator::generateButton)
                    .put(BlockFamily.Variant.DOOR, OBlockStateGenerator::generateDoor)
                    .put(BlockFamily.Variant.CHISELED, OBlockStateGenerator::generateChiseled)
                    .put(BlockFamily.Variant.CRACKED, OBlockStateGenerator::generateCracked)
                    .put(BlockFamily.Variant.CUSTOM_FENCE, OBlockStateGenerator::generateFence)
                    .put(BlockFamily.Variant.FENCE, OBlockStateGenerator::generateFence)
                    .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, OBlockStateGenerator::generateFenceGate)
                    .put(BlockFamily.Variant.FENCE_GATE, OBlockStateGenerator::generateFenceGate)
                    .put(BlockFamily.Variant.SIGN, OBlockStateGenerator::generateSign)
                    .put(BlockFamily.Variant.SLAB, OBlockStateGenerator::generateSlab)
                    .put(BlockFamily.Variant.STAIRS, OBlockStateGenerator::generateStair)
                    .put(BlockFamily.Variant.PRESSURE_PLATE, OBlockStateGenerator::generatePressurePlate)
                    .put(BlockFamily.Variant.TRAPDOOR, OBlockStateGenerator::generateTrapDoor)
                    .put(BlockFamily.Variant.WALL, OBlockStateGenerator::generateWall)
                    .build();


    @Override
    protected void registerStatesAndModels() {

        OBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(blockFamily -> {
            blockWithItem(blockFamily.getBaseBlock());
            for(Map.Entry<BlockFamily.Variant, Block> family : blockFamily.getVariants().entrySet()){
                Block block = family.getValue();
                Block main = blockFamily.getBaseBlock();
                BlockFamily.Variant variant = family.getKey();
                BiConsumer<OBlockStateGenerator, Block> biconsumer = OBlockStateGenerator.SHAPE_CONSUMERS.get(variant);
                if (biconsumer != null) {
                    biconsumer.accept(this, block);
                }

            }
        });

        for(RegistryObject<Block> block : OBlocks.BLOCKS.getEntries()) {
            if(blockSet.contains(block.get())) continue;
            Block block1 = block.get();
            if(block1 instanceof StripDyeBlock) {
                if(block1 instanceof StrippableDyeBlock) {
                    blockWithItem(block.get(), "aluminium");
                }

                if(block1 instanceof StrippableDoorBlock) {
                    generateDoor(block.get(), "aluminium");
                }

                if(block1 instanceof StrippableTrapDoorBlock) {
                    generateTrapDoor(block.get(), "aluminium");
                }

                //if(block1 instanceof StrippableRotatedPillarBlock) {
                //    generateTrapDoor(block.get(), "aluminium");
                //}
            }

        }

    }

    private void buildLamp(Block block) {
        String baseName = name(block);
        ResourceLocation textureOn = prefix("block/" + baseName+"_on");
        ResourceLocation textureOff = prefix("block/" + baseName+"_off");
        ModelFile on = models().cubeAll(baseName + "_on", textureOn);
        ModelFile off = models().cubeAll(baseName + "_off", textureOff);
        simpleBlockItem(block, off);
        buildLamp(block, on, off);
    }

    private void buildLamp(Block block, ModelFile on, ModelFile off){
        getVariantBuilder(block).forAllStatesExcept((state -> {
            boolean isLit =  state.getValue(BlockStateProperties.LIT);
            return ConfiguredModel.builder().modelFile(isLit ? on : off).build();
        }));
    }

    public void generatePressurePlate(Block block){
        pressurePlateBlock((PressurePlateBlock) block, prefix("block/" + key(block).getPath()));
    }
    
    public void generateWall(Block block){
        wallBlockWithRenderType((WallBlock) block, prefix("block/" + key(block).getPath().replace("_wall", "")), "cutout");
        generatedWall(name(block), ResourceLocation.tryParse(blockTexture(block).toString().replace("_wall", "")));
    }
    public void generateTrapDoor(Block block){
        trapdoorBlockWithRenderType((TrapDoorBlock) block, prefix("block/" + key(block).getPath().replace("_trapdoor", "")), true, "cutout");
    }
    public void generateTrapDoor(Block block, String loc){
        String p = key(block).getPath().split("_")[0];
        trapdoorBlockWithRenderType((TrapDoorBlock) block, prefix("block/" + loc  +"/"+ p + "/" + key(block).getPath()), true, "cutout");
    }

    public void generateStair(Block block){
        stairsBlockWithRenderType((StairBlock) block, prefix("block/" + key(block).getPath().replace("_stairs", "")), "cutout");
    }
    
    public void generateSlab(Block block){
        //blockWithItemSlab(block);
        slabBlock((SlabBlock) block, prefix("block/" + key(block).getPath().replace("_slab", "")), prefix("block/" + key(block).getPath().replace("_slab", "")), prefix("block/" + key(block).getPath().replace("_slab", "")), prefix("block/" + key(block).getPath().replace("_slab", "")));
    }
    
    public void generateSign(Block signBlock){
        LOGGER.error("Sign gen is not yet implemented!");
        //signBlock((StandingSignBlock) signBlock, (WallSignBlock) wallSignBlock, prefix("block/" + key(signBlock).getPath()));
    }
    
    public void generateFenceGate(Block block){
        fenceGateBlockWithRenderType((FenceGateBlock) block, prefix("block/" + key(block).getPath()), "cutout");
    }
    
    public void generateFence(Block block){
        fenceBlockWithRenderType((FenceBlock) block, prefix("block/" + key(block).getPath()), "cutout");
    }
    
    public void generateCracked(Block block){
        blockWithItem(block);
    }

    public void generateChiseled(Block block){
        blockWithItem(block);
    }
    public void generateDoor(Block doorBlock){
        doorBlockWithRenderType((StrippableDoorBlock) doorBlock, prefix("block/" + key(doorBlock).getPath()+ "_top"), prefix("block/" + key(doorBlock).getPath()+ "_bottom"), "cutout");
    }
    public void generateDoor(Block doorBlock, String loc){
        String p = key(doorBlock).getPath().split("_")[0];
        doorBlockWithRenderType((StrippableDoorBlock) doorBlock, prefix("block/" + loc  +"/"+  p + "/" +  key(doorBlock).getPath()+ "_top"), prefix("block/" + loc +"/"+  p + "/" + key(doorBlock).getPath()+ "_bottom"), "cutout");
    }
    
    public void generateButton(Block block){
        buttonBlock((ButtonBlock) block, prefix("block/" + key(block).getPath()));
    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockWithItem(Block blockRegistryObject, ModelFile file) {
        simpleBlockWithItem(blockRegistryObject,file);
    }

    private void blockWithItem(Block blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject, cubeAll(blockRegistryObject));
    }

    private void blockWithItem(Block blockRegistryObject, String loc) {
        simpleBlockWithItem(blockRegistryObject, cubeAll(blockRegistryObject, loc));
    }

    public ModelFile cubeAll(Block block, String loc) {
        return models().cubeAll(name(block), blockTexture(block, loc));
    }

    public ResourceLocation blockTexture(Block block, String loc) {
        ResourceLocation name = key(block);
        String p = key(block).getPath().split("_")[0];
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc + "/" + p + "/"+ name.getPath());
    }

    private void blockWithItemSlab(Block blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject, stripSlab(blockRegistryObject));
    }

    private ModelFile stripSlab(Block block){
        return models().cubeAll(name(block), ResourceLocation.tryParse(blockTexture(block).toString().replace("_slab", "")));
    }

    private void createWallFan(RegistryObject<Block> b, String renderType){
        ModelFile file = new ConfiguredModel(wallCoral(name(b.get()), blockTexture(b.get()), renderType)).model;
        getVariantBuilder(b.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(file)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
        simpleBlockItem(b.get(), file);
    }
    private void createTintedCross(RegistryObject<Block> b, String renderType){
        getVariantBuilder(b.get()).partialState().setModels(new ConfiguredModel(tintedCross(name(b.get()), blockTexture(b.get()), renderType)));
    }
    public ModelFile tintedCross(String name, ResourceLocation cross, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/tinted_cross", "cross", cross, renderType);
    }

    private void createCross(RegistryObject<Block> b, String renderType){
        ConfiguredModel cFfile = new ConfiguredModel(cross(name(b.get()), blockTexture(b.get()), renderType));
        ModelFile file = cFfile.model;
        getVariantBuilder(b.get()).partialState().setModels(cFfile);
        simpleBlockItem(b.get(), file);
    }
    public ModelFile cross(String name, ResourceLocation cross, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/cross", "cross", cross, renderType);
    }

    private ModelFile singleTexture(String name, String parent, String textureKey, ResourceLocation texture, String renderType) {
        return singleTexture(name, mcLoc(parent), textureKey, texture, renderType);
    }

    public ModelFile wallCoral(String name, ResourceLocation fan, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/coral_wall_fan", "fan", fan, renderType);
    }

    private void createCoralFan(RegistryObject<Block> b, String renderType){
        getVariantBuilder(b.get()).partialState().setModels(new ConfiguredModel(coralFan(name(b.get()), blockTexture(b.get()), renderType)));
        singleTex(b.get());
    }

    public ModelFile coralFan(String name, ResourceLocation fan, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/coral_fan", "fan", fan, renderType);
    }

    public ModelFile singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture, String renderType) {
        return models().withExistingParent(name, parent)
                .texture(textureKey, texture).renderType(renderType);
    }

    private BlockModelBuilder generatedWall(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("block/" + name, "block/wall_inventory");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private BlockModelBuilder generatedSlab(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("block/" + name, "block/slab");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private void blockWithTop(RegistryObject<Block> blockRegistryObject) {
        horizontalBlock(blockRegistryObject.get(), prefix("block/" + key(blockRegistryObject.get()).getPath()),prefix("block/" + key(blockRegistryObject.get()).getPath()),prefix("block/" + key(blockRegistryObject.get()).getPath() + "_top"));
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }


   private BlockModelBuilder singleTex(Block block) {
       return generated(name(block), new ResourceLocation(Orenamental.MODID,"block/" + name(block)));
   }
    private BlockModelBuilder generated(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("item/" + name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}