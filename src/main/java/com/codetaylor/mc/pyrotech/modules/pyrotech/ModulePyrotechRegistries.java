package com.codetaylor.mc.pyrotech.modules.pyrotech;

import com.codetaylor.mc.pyrotech.modules.pyrotech.recipe.DryingRackRecipe;
import com.codetaylor.mc.pyrotech.modules.pyrotech.recipe.KilnBrickRecipe;
import com.codetaylor.mc.pyrotech.modules.pyrotech.recipe.KilnPitRecipe;
import com.codetaylor.mc.pyrotech.modules.pyrotech.recipe.PitBurnRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ModulePyrotechRegistries {

  public static final IForgeRegistryModifiable<KilnPitRecipe> KILN_PIT_RECIPE;
  public static final IForgeRegistryModifiable<KilnBrickRecipe> KILN_BRICK_RECIPE;
  public static final IForgeRegistryModifiable<PitBurnRecipe> BURN_RECIPE;
  public static final IForgeRegistryModifiable<DryingRackRecipe> DRYING_RACK_RECIPE;

  public static final List<Predicate<IBlockState>> REFRACTORY_BLOCK_LIST;
  public static final List<Predicate<IBlockState>> COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST;

  static {
    KILN_PIT_RECIPE = (IForgeRegistryModifiable<KilnPitRecipe>) GameRegistry.findRegistry(KilnPitRecipe.class);
    KILN_BRICK_RECIPE = (IForgeRegistryModifiable<KilnBrickRecipe>) GameRegistry.findRegistry(KilnBrickRecipe.class);
    BURN_RECIPE = (IForgeRegistryModifiable<PitBurnRecipe>) GameRegistry.findRegistry(PitBurnRecipe.class);
    DRYING_RACK_RECIPE = (IForgeRegistryModifiable<DryingRackRecipe>) GameRegistry.findRegistry(DryingRackRecipe.class);

    REFRACTORY_BLOCK_LIST = new ArrayList<>();
    COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
  }

  private ModulePyrotechRegistries() {
    //
  }

}
