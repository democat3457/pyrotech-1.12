package com.codetaylor.mc.pyrotech.modules.pyrotech.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemCrudeShovel
    extends ItemSpade {

  public static final String NAME = "crude_shovel";

  public ItemCrudeShovel() {

    super(ToolMaterial.STONE);
    this.setMaxDamage(ToolMaterial.STONE.getMaxUses() / 4);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {

    return super.getDestroySpeed(stack, state) * 0.5f;
  }

  @Override
  public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {

    return 0;
  }
}
