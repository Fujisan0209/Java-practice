
import java.util.ArrayList;
import java.util.List;

final class Recipe {
    private final String name;
    private final List<String> ingredients;

    public Recipe(String name, List<String> ingredients) {
        if(name == null) throw new IllegalArgumentException("name must not be null: " + name);
        if(name.isEmpty()) throw new IllegalArgumentException("name must not be empty: " + name);
        if(ingredients == null) throw new IllegalArgumentException("ingredients must not be null: " + ingredients);
        if(ingredients.isEmpty()) throw new IllegalArgumentException("ingredients must not be empty: " + ingredients);

        this.name = name;
        List<String> tmp = new ArrayList<>(ingredients);
        this.ingredients = tmp;
    }

    public String getName() { return name; }

    public List<String> getIngredientsLeaky() { return ingredients; }

    public List<String> getIngredients() { return List.copyOf(ingredients); }

    public Recipe withExtra(String ingredient) {
        List<String> current = new ArrayList<>(this.ingredients);
        current.add(ingredient);
        return new Recipe(this.name, current);
    }

    @Override
    public String toString() {
        return this.name + this.ingredients;
    }
}

public class RecipeLab {
    public static void main(String[] args) {
        System.out.println("--- 区間A ---");
        List<String> base = new ArrayList<>(List.of("米", "肉", "ルー"));
        Recipe r = new Recipe("カレー", base);
        System.out.println("期待値: カレー, 実際の表示: " + r.getName());
        System.out.println("期待値: [米, 肉, ルー], 実際の表示: " + r.getIngredients());
        System.out.println("期待値: カレー[米, 肉, ルー], 実際の表示: " + r);
        System.out.println("期待値: カレー[米, 肉, ルー, 水], 実際の表示: " + r.withExtra("水"));
        System.out.println("期待値: カレー[米, 肉, ルー], 実際の表示: " + r);
        System.out.println("--- 区間B ---");
        base.add("激辛スパイス");
        System.out.println(r.getIngredients().size());
        // 3
        r.getIngredientsLeaky().add("砂糖");
        System.out.println(r.getIngredients().size());
        // 4
        Recipe r2 = r.withExtra("水");
        System.out.println(r.getIngredients().size());
        // 4
        System.out.println(r2.getIngredients().size());
        // 5
        System.out.println(base == r.getIngredientsLeaky());
        // false
        System.out.println("--- 区間C ---");
        // 3行 → 7行
        try {
            new Recipe(null, base);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Recipe("カレー", new ArrayList<>());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            r.getIngredients().add("塩");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }       
    }
}