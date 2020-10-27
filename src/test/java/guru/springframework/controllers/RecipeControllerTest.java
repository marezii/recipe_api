package guru.springframework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void getAllRecipes() throws Exception {

        Set<Recipe> recipes = initializeRecipeSet();

        when(recipeService.getRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/recipes"))
                .andDo(print())
                .andExpect(status().isOk());

    }



    @Test
    public void getRecipeById() throws Exception {

        Recipe recipe = initializeRecipe(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipe.getId()))
                .andExpect(jsonPath("$.difficulty").value(recipe.getDifficulty()));

    }

    @Test
    public void createRecipe() throws Exception{

        RecipeCommand recipe = initializeRecipeCommand();

        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(recipe);

        mockMvc.perform(post("/recipes")
                .content(new ObjectMapper().writeValueAsString(recipe))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(recipe.getId()));

    }

    @Test
    public void updateRecipe() throws Exception{
        Long id = 1L;
        RecipeCommand recipe = initializeRecipeCommand();

        when(recipeService.updateRecipeCommand(anyLong(), any(RecipeCommand.class))).thenReturn(recipe);

        mockMvc.perform(put("/recipes/1")
                .content(new ObjectMapper().writeValueAsString(recipe))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isOk());
    }

    private Set<Recipe> initializeRecipeSet() {
        Set<Recipe> recipeSet = new HashSet();
        Recipe recipe1 = initializeRecipe(1L);
        Recipe recipe2 = initializeRecipe(2L);
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);
        return recipeSet;
    }

    private RecipeCommand initializeRecipeCommand() {
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(1L);
        return recipe;
    }

    private Recipe initializeRecipe(long l) {
        Recipe recipe = new Recipe();
        recipe.setId(l);
        return recipe;
    }
}