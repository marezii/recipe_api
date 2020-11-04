package guru.springframework.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import guru.springframework.domain.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RecipeDeserializer extends StdDeserializer<Recipe> {

    public RecipeDeserializer() {
        this(null);
    }

    public RecipeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Recipe deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        int cookTime = (Integer) ((IntNode) node.get("cookTime")).numberValue();
        int servings = (Integer) ((IntNode) node.get("servings")).numberValue();
        int prepTime = (Integer) node.get("prepTime").numberValue();
        String source = node.get("source").asText();
        String description = node.get("description").asText();
        String url = node.get("url").asText();
        String directions = node.get("directions").asText();

        String difficultyString = node.get("difficulty").asText();
        Difficulty difficulty = Difficulty.valueOf(difficultyString);

        Notes notes = new Notes();
        if (!node.get("notes").isNull()) {
            Long notesId = node.get("notes").get("id").asLong();
            String recipeNotes = node.get("notes").get("recipeNotes").asText();
            notes.setId(notesId);
            notes.setRecipeNotes(recipeNotes);
        }

        Set<Ingredient> ingredients = new HashSet();
        Set<UnitOfMeasure> uoms = new HashSet();

        JsonNode arrayNode = node.get("ingredients").get(1);

        if (arrayNode.isArray()) {
            for (JsonNode jsonNode : arrayNode) {
                Long ingredientId = jsonNode.get("id").asLong();
                String ingredientDescription = jsonNode.get("description").asText();
                Double amount = jsonNode.get("amount").get(1).asDouble();

                Long uomId = jsonNode.get("uom").get("id").asLong();
                String uomDescription = jsonNode.get("uom").get("description").asText();

                UnitOfMeasure uom = new UnitOfMeasure();
                uom.setId(uomId);
                uom.setDescription(uomDescription);
                uoms.add(uom);

                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                ingredient.setDescription(ingredientDescription);
                ingredient.setAmount(BigDecimal.valueOf(amount));
                ingredient.setUom(uom);
                ingredients.add(ingredient);
            }
        }

        JsonNode categories = node.get("categories").get(1);
        Set<Category> categorySet = new HashSet<>();
        List<Long> categoryIds = new ArrayList<>(categories.size());
        categories.forEach(category -> {
            categoryIds.add(category.asLong());
        });

        categoryIds.forEach(categoryId -> {
            Category category = new Category();
            category.setId(categoryId);
            categorySet.add(category);
        });

        Recipe recipe = new Recipe();
        recipe.setId(Long.valueOf(id));
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setSource(source);
        recipe.setPrepTime(prepTime);
        recipe.setDescription(description);
        recipe.setUrl(url);
        if (notes != null) {
            recipe.setNotes(notes);
        }
        recipe.setDirections(directions);
        recipe.setIngredients(ingredients);
        recipe.setDifficulty(difficulty);
        recipe.setCategories(categorySet);

        return recipe;
    }
}
