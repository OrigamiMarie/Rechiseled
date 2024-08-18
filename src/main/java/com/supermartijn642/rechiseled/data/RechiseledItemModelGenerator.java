package com.supermartijn642.rechiseled.data;

import com.supermartijn642.core.generator.ModelGenerator;
import com.supermartijn642.core.generator.ResourceCache;
import com.supermartijn642.rechiseled.Rechiseled;
import net.minecraft.resources.ResourceLocation;

/**
 * Created 7/8/2021 by SuperMartijn642
 */
public class RechiseledItemModelGenerator extends ModelGenerator {

    public RechiseledItemModelGenerator(ResourceCache cache){
        super("rechiseled", cache);
    }

    @Override
    public void generate(){
        // Chisel
        this.itemHandheld(Rechiseled.chisel, ResourceLocation.fromNamespaceAndPath("rechiseled", "item/chisel"));
    }
}
