/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.redcrisisgaming.zipposetc.proxy;

import com.redcrisisgaming.zipposetc.init.ModItems;
import com.redcrisisgaming.zipposetc.reference.Reference;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by DaddyToTheJs on 1/1/2016.
 */
public class ClientProxy extends CommonProxy {

    public void preInit(){
        super.preInit();
        ModelBakery.addVariantName(ModItems.zippo, "zipposetc:zippo-closed", "zipposetc:zippo-lit", "zipposetc:zippo-open");
    }

    @Override
    public void load(){
        super.load();
        reg(ModItems.zippo, 0, "zippo-closed");
        reg(ModItems.zippo, 1, "zippo-open");
        reg(ModItems.zippo, 2, "zippo-lit");


    }

    public void postInit(){
        super.postInit();
    }

    public static void reg(Item item, int meta, String file) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + file, "inventory"));
    }

}
