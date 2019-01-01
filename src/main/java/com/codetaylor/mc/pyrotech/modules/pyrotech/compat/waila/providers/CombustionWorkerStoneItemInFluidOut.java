package com.codetaylor.mc.pyrotech.modules.pyrotech.compat.waila.providers;

import com.codetaylor.mc.athenaeum.util.StackHelper;
import com.codetaylor.mc.athenaeum.util.StringHelper;
import com.codetaylor.mc.pyrotech.library.util.Util;
import com.codetaylor.mc.pyrotech.modules.pyrotech.ModulePyrotech;
import com.codetaylor.mc.pyrotech.modules.pyrotech.compat.waila.WailaRegistrar;
import com.codetaylor.mc.pyrotech.modules.pyrotech.compat.waila.WailaUtil;
import com.codetaylor.mc.pyrotech.modules.pyrotech.recipe.StoneMachineRecipeItemInFluidOutBase;
import com.codetaylor.mc.pyrotech.modules.pyrotech.tile.TileStoneTop;
import com.codetaylor.mc.pyrotech.modules.pyrotech.tile.spi.TileCombustionWorkerStoneItemInFluidOutBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class CombustionWorkerStoneItemInFluidOut
    extends BodyProviderAdapter {

  @Nonnull
  @Override
  public List<String> getWailaBody(
      ItemStack itemStack,
      List<String> tooltip,
      IWailaDataAccessor accessor,
      IWailaConfigHandler config
  ) {

    if (!config.getConfig(WailaRegistrar.CONFIG_PROGRESS)) {
      return tooltip;
    }

    TileEntity tileEntity = accessor.getTileEntity();

    if (tileEntity instanceof TileCombustionWorkerStoneItemInFluidOutBase
        || tileEntity instanceof TileStoneTop) {

      TileCombustionWorkerStoneItemInFluidOutBase tile = null;

      if (tileEntity instanceof TileCombustionWorkerStoneItemInFluidOutBase) {
        tile = (TileCombustionWorkerStoneItemInFluidOutBase) tileEntity;

      } else {

        if (((TileStoneTop) tileEntity).isCustom()) {
          return tooltip;
        }

        World world = tileEntity.getWorld();
        TileEntity candidate = world.getTileEntity(tileEntity.getPos().down());

        if (candidate instanceof TileCombustionWorkerStoneItemInFluidOutBase) {
          tile = (TileCombustionWorkerStoneItemInFluidOutBase) candidate;
        }
      }

      if (tile == null) {
        return tooltip;
      }

      float progress = tile.workerGetProgress(0);

      ItemStackHandler stackHandler = tile.getInputStackHandler();
      FluidTank outputFluidTank = tile.getOutputFluidTank();
      ItemStackHandler fuelStackHandler = tile.getFuelStackHandler();

      ItemStack input = stackHandler.getStackInSlot(0);
      boolean hasOutput = outputFluidTank.getFluid() != null && outputFluidTank.getFluid().amount > 0;
      ItemStack fuel = fuelStackHandler.getStackInSlot(0);

      if (!input.isEmpty()) {

        // Display input item and recipe output.

        StringBuilder renderString = new StringBuilder();
        renderString.append(WailaUtil.getStackRenderString(input));

        if (!fuel.isEmpty()) {
          renderString.append(WailaUtil.getStackRenderString(fuel));
        }

        StoneMachineRecipeItemInFluidOutBase recipe = (StoneMachineRecipeItemInFluidOutBase) tile.getRecipe(input);

        if (recipe != null) {
          FluidStack output = recipe.getOutput();
          ItemStack filledBucket = FluidUtil.getFilledBucket(output);
          renderString.append(WailaUtil.getProgressRenderString((int) (100 * progress), 100));
          renderString.append(WailaUtil.getStackRenderString(filledBucket));
        }

        tooltip.add(renderString.toString());

      }

      if (hasOutput) {

        // Display output fluid.

        FluidStack fluid = outputFluidTank.getFluid();
        tooltip.add(Util.translateFormatted(
            "%s: %d / %d mB",
            fluid.getLocalizedName(),
            fluid.amount,
            outputFluidTank.getCapacity()
        ));
      }

      {
        if (tile.combustionGetBurnTimeRemaining() > 0) {
          ItemStack fuelStack = tile.getFuelStackHandler().getStackInSlot(0);
          tooltip.add(Util.translateFormatted(
              "gui." + ModulePyrotech.MOD_ID + ".waila.burn.time",
              StringHelper.ticksToHMS(tile.combustionGetBurnTimeRemaining() + fuelStack.getCount() * StackHelper.getItemBurnTime(fuelStack))
          ));
        }

        if (!fuel.isEmpty()) {
          tooltip.add(Util.translateFormatted(
              "gui." + ModulePyrotech.MOD_ID + ".waila.kiln.brick.fuel", // TODO: rename this
              fuel.getItem().getItemStackDisplayName(fuel) + " * " + fuel.getCount()
          ));
        }
      }

    }

    return tooltip;
  }

}