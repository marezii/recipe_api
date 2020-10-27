package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Recipe> getAllRecipes(){

        return recipeService.getRecipes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe getRecipeById(@PathVariable String id){

        return recipeService.findById(new Long(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeCommand createRecipe(@RequestBody RecipeCommand command){
          return recipeService.saveRecipeCommand(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeCommand updateRecipe(@PathVariable Long id, @RequestBody RecipeCommand command){
        return recipeService.updateRecipeCommand(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRecipe(@PathVariable Long id){
        recipeService.deleteById(id);
    }

}
