package guru.springframework.security;

public enum ApplicationUserPermission {

    RECIPE_READ("recipe:read"),
    UOM_READ("uom:read"),
    INGREDIENT_READ("ingredient:read"),
    RECIPE_WRITE("recipe:write"),
    UOM_WRITE("uom:write"),
    INGREDIENT_WRITE("ingredient:write"),
    RECIPE_UPDATE("recipe:update"),
    UOM_UPDATE("uom:update"),
    INGREDIENT_UPDATE("ingredient:update");

    private final String permission;

    public String getPermission(){
        return permission;
    }

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

}
