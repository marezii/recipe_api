package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        //given
        Long recipeId = 1L;
        Long ingredientId = 1L;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);

        Set<Ingredient> ingredients = new HashSet();
        ingredients.add(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        recipeOptional.get().setId(recipeId);
        recipeOptional.get().setIngredients(ingredients);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        //then
        assertNotNull(command);
        assertEquals(ingredientCommand, command);
        verify(recipeRepository, times(1)).findById(anyLong());

    }

    @Test
    public void createIngredient() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        when(ingredientCommandToIngredient.convert(any())).thenReturn(ingredient);

        when(ingredientRepository.save(any())).thenReturn(ingredient);

        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);

        //when
        IngredientCommand createdIngredient = ingredientService.createIngredient(ingredientCommand);

        //then
        assertNotNull(createdIngredient);
        assertEquals(createdIngredient, ingredientCommand);
        verify(ingredientRepository, times(1)).save(any());
    }

    @Test
    public void findAllIngredients() {
        //given
        List<Ingredient> ingredients = new ArrayList();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredients.add(ingredient);

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        IngredientCommand mappedIngredient = new IngredientCommand();
        mappedIngredient.setId(1L);

        when(ingredientToIngredientCommand.convert(any())).thenReturn(mappedIngredient);

        //when
        Set<IngredientCommand> ingredientCommandSet = ingredientService.findAllIngredients();

        //then
        assertNotNull(ingredientCommandSet);
        assertEquals(ingredientCommandSet.stream().findFirst().get().getId(),
                ingredients.stream().findFirst().get().getId());
    }

    @Test
    public void updateIngredient() {
        //given
        Long id = 1L;
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(id);
        Ingredient convertedIngredient = new Ingredient();

        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class))).thenReturn(convertedIngredient);

        Ingredient savedIngrediend = new Ingredient();

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(savedIngrediend);

        IngredientCommand ingredientCommandReturn = new IngredientCommand();
        ingredientCommandReturn.setId(id);

        when(ingredientToIngredientCommand.convert(any(Ingredient.class))).thenReturn(ingredientCommandReturn);

        //when
        ingredientService.updateIngredient(id, ingredientCommand);

        //then
        Assert.assertNotNull(savedIngrediend);
        Assert.assertEquals(id, ingredientCommandReturn.getId());


    }
}