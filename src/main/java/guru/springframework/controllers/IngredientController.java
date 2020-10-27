package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    //http://localhost:8080/ingredients/?recipeId=2
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ingredient:read')")
    public Set<IngredientCommand> listIngredients(@RequestParam(required = false) String recipeId){
        if(recipeId != null){

            RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
            return recipeCommand.getIngredients();
        } else return  ingredientService.findAllIngredients();
    }

    //http://localhost:8080/ingredients
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ingredient:write')")
    public IngredientCommand createIngredient(@RequestBody IngredientCommand ingredientCommand,
                                              @RequestParam(required = false) String recipeId){
        if(recipeId != null){
            return ingredientService.saveIngredientCommandToRecipe(ingredientCommand, Long.valueOf(recipeId));
        }

        return ingredientService.createIngredient(ingredientCommand);
    }

    //http://localhost:8080/ingredients/30?uomID=8
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ingredient:update')")
    public IngredientCommand addUOMtoIngredient(@PathVariable(value = "id") String ingredientId
                                                , @RequestParam String uomID){
        return ingredientService.saveUOMtoIngredient(Long.valueOf(ingredientId), Long.valueOf(uomID));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('ingredient:update')")
    public IngredientCommand updateIngredient(@PathVariable String id, @RequestBody IngredientCommand ingredientCommand){
        return ingredientService.updateIngredient(Long.valueOf(id), ingredientCommand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteIngredient(@PathVariable String id, @RequestParam(required = false) String recipeId) {
        if (recipeId != null) {
            ingredientService.deleteFromRecipe(Long.valueOf(recipeId), Long.valueOf(id));
        } else ingredientService.deleteIngredient(Long.valueOf(id));
    }
}

