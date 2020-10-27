package guru.springframework.services;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {
        //given
        Set<Recipe> recipes = new HashSet();

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipes);

        //when
        Set<Recipe> recipesReturned = recipeService.getRecipes();

        //then
        assertNotNull(recipesReturned);
        assertEquals(recipes, recipesReturned);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        Recipe returnedRecipe = recipeService.findById(1L);

        //then
        assertEquals(returnedRecipe, recipeOptional.get());
    }

    @Test
    public void findCommandById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        //when
        RecipeCommand commandById = recipeService.findCommandById(1L);

        //then
        assertNotNull("Null recipe returned", commandById);
        assertEquals(recipeCommand.getId(), recipeOptional.get().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveRecipe() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeCommandToRecipe.convert(any())).thenReturn(recipe);

        when(recipeRepository.save(any())).thenReturn(recipe);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        //when
        RecipeCommand returnedCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertNotNull(returnedCommand);
        assertEquals(recipeCommand, returnedCommand);
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void updateRecipe() {
        //given
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        Recipe recipe = new Recipe();

        when(recipeCommandToRecipe.convert(any())).thenReturn(recipe);

        Recipe returnedRecipe = new Recipe();
        returnedRecipe.setId(1L);
        when(recipeRepository.save(any())).thenReturn(returnedRecipe);

        RecipeCommand commandToReturn = new RecipeCommand();
        commandToReturn.setId(recipeId);
        when(recipeToRecipeCommand.convert(any())).thenReturn(commandToReturn);

        //when
        RecipeCommand command = recipeService.updateRecipeCommand(recipeId, recipeCommand);

        //then
        assertNotNull("Recipe not updated", command);
        assertEquals("Wrong recipe updated", commandToReturn, command);
        verify(recipeRepository,times(1)).save(any());
    }

    @Test
    public void deleteRecipe() {
        //given
        Long idToDelete = 1L;

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());

    }
}