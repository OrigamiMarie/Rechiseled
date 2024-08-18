package com.supermartijn642.rechiseled.data;

import com.supermartijn642.rechiseled.api.ChiseledTextureProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;

/**
 * Created 24/01/2022 by SuperMartijn642
 */
public class RechiseledTextureProvider extends ChiseledTextureProvider {

    public RechiseledTextureProvider(FabricDataOutput generator){
        super("rechiseled", generator);
    }

    @Override
    protected void createTextures(){
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/acacia_planks"), "block/acacia_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/birch_planks"), "block/birch_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/crimson_planks"), "block/crimson_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/dark_oak_planks"), "block/dark_oak_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/jungle_planks"), "block/jungle_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/mangrove_planks"), "block/mangrove_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/spruce_planks"), "block/spruce_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/warped_planks"), "block/warped_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/bamboo_planks"), "block/bamboo_planks");
        this.createPlankTextures(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cherry_planks"), "block/cherry_planks");
    }
}
