public class Book {
    private String title;
    private String category;
    private int price;
    private int stock;

    public Book(String title, String category, int price, int stock) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }

    public int getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }
}