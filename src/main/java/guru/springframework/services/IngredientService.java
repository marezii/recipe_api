package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

import java.util.Set;


public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommandToRecipe(IngredientCommand command, Long recipeId);

    IngredientCommand createIngredient(IngredientCommand command);

    void deleteIngredient(Long ingredientId);

    Set<IngredientCommand> findAllIngredients();

    IngredientCommand saveUOMtoIngredient(Long ingredientId, Long uomId);

    IngredientCommand updateIngredient(Long id, IngredientCommand ingredientCommand);

    void deleteFromRecipe(Long recipeId, Long idToDelete);
}
