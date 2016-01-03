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

package com.redcrisisgaming.zipposetc.items;

import com.redcrisisgaming.zipposetc.reference.Reference;
import com.redcrisisgaming.zipposetc.utility.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.redcrisisgaming.zipposetc.reference.Textures;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DaddyToTheJs on 1/1/2016.
 */
public class ItemZippo extends Item implements IZItems {

    private Random random = new Random();

    boolean isOpen;
    boolean isLit;

    private long id;

    public ItemZippo(){
        super();
        setMaxStackSize(1);
        setUnlocalizedName(Reference.ZIPPO_UNLOCALIZED_NAME);
        this.isLit = false;
        this.isOpen = false;
        this.id = this.random.nextLong();
    }

    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn){
        int rnNum;

        if (!worldIn.isRemote) {
            if (playerIn.isSneaking() && stack.getTagCompound().getBoolean("open")) {
                worldIn.playSoundEffect((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D, "zipposetc:zippo-close", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                stack.getTagCompound().setBoolean("lit", false);
                stack.getTagCompound().setBoolean("open", false);
                stack.getTagCompound().setInteger("status", 0);
            } else if (playerIn.isSneaking() && !stack.getTagCompound().getBoolean("open")) {
                worldIn.playSoundEffect((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D, "zipposetc:zippo-open", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                stack.getTagCompound().setBoolean("open", true);
                stack.getTagCompound().setInteger("status", 1);
            } else if (!stack.getTagCompound().getBoolean("lit") && stack.getTagCompound().getBoolean("open")) {
                worldIn.playSoundEffect((double) playerIn.getPosition().getX() + 0.5D, (double) playerIn.getPosition().getY() + 0.5D, (double) playerIn.getPosition().getZ() + 0.5D, "zipposetc:zippo-lighting", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                rnNum = random.nextInt(4);

                if (rnNum == 1) {
                    stack.getTagCompound().setBoolean("lit", true);
                    stack.getTagCompound().setInteger("status", 2);
                    playerIn.inventoryContainer.detectAndSendChanges();
                }
            }

        }
        return stack;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        pos = pos.offset(side);

        if (!playerIn.canPlayerEdit(pos, side, stack))
        {
            return false;
        }
        else
        {
            if (worldIn.isAirBlock(pos) && stack.getTagCompound().getBoolean("lit"))
            {
                worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
            }

            return true;
        }
    }

    @Override
    public String getUnlocalizedName(){
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
     if (itemStack.getTagCompound() != null){
        if (itemStack.getTagCompound().getInteger("status") == 0) {
            return String.format("item.%s%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()), "-closed");
        } else if (itemStack.getTagCompound().getInteger("status") == 1) {
            return String.format("item.%s%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()), "-open");
        } else if (itemStack.getTagCompound().getInteger("status") == 2) {
            return String.format("item.%s%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()), "-lit");
        }else {
            return String.format("item.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }
    }else {
            return String.format("item.%s%s", Textures.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }
    }

    @Override
    public String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
    }

    public int getMetadata(ItemStack itemStack){
        if(itemStack.getTagCompound() != null){
            return itemStack.getTagCompound().getInteger("status");
        } else {
            return 0;
        }
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int metadata, boolean bool) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound()); // = new NBTTagCompound(); //or itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setLong("id", this.id);
            itemstack.getTagCompound().setBoolean("lit", false);
            itemstack.getTagCompound().setBoolean("open", false);
            itemstack.getTagCompound().setInteger("status", 0);
            itemstack.getTagCompound().setDouble("fuellevel", 1000);
            List<String> infotag = new ArrayList<String>();
            infotag.add(String.format("ID: %d", id));
            itemstack.getItem().addInformation(itemstack, (EntityPlayer) entity, infotag, true);

        }
        if (!world.isRemote) {
            if (itemstack.getItem().equals(this)) {
                if (itemstack.getTagCompound().getBoolean("lit") && world.getTotalWorldTime() % 90 == 0) {
                    if (itemstack.getTagCompound().getDouble("fuellevel") - 15 < 0) {
                        itemstack.getTagCompound().setDouble("fuellevel", 0);
                    }else {
                        itemstack.getTagCompound().setDouble("fuellevel", itemstack.getTagCompound().getDouble("fuellevel") - 15);
                    }
                }
                if (itemstack.getTagCompound().getDouble("fuellevel") == 0){
                    itemstack.getTagCompound().setBoolean("lit", false);
                    itemstack.getTagCompound().setInteger("status", 1);
                }
            }
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack){
        if(itemStack.getTagCompound() != null) {
            if (itemStack.getTagCompound().getDouble("fuellevel") > 0 && itemStack.getItem().equals(this)) {
                return 1 - (itemStack.getTagCompound().getDouble("fuellevel") / (double) 1000);
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }


    /*public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack){
        return false;
    }


    public boolean hasContainerItem(ItemStack itemStack){
        return true;
    }

    public ItemStack getContainerItem(ItemStack itemStack){
        ItemStack stack = itemStack.copy();

        stack.setTagCompound(itemStack.getTagCompound());
        stack.getTagCompound().setDouble("fuellevel", stack.getTagCompound().getDouble("fuellevel")+250);

        return stack;
    }*/
}

