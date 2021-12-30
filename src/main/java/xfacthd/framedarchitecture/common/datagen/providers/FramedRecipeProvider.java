package xfacthd.framedarchitecture.common.datagen.providers;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import xfacthd.framedarchitecture.FramedArchitecture;
import xfacthd.framedarchitecture.common.util.Utils;

import java.util.function.Consumer;

public class FramedRecipeProvider extends RecipeProvider
{
    private final CriterionTriggerInstance HAS_FRAMED_BLOCK = has(Utils.block("framedblocks:framed_cube"));

    public FramedRecipeProvider(DataGenerator gen) { super(gen); }

    @Override
    public String getName() { return super.getName() + ": " + FramedArchitecture.MODID; }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {

    }
}
