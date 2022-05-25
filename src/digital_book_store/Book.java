package digital_book_store;



public class Book {
    private String name;
    private String author;
    private Integer id;
    private BookStatus availableStatus;
    private Integer price;

    public Book(String name, String author, int idBook, BookStatus availableStatus, Integer price) {
        this.name = name;
        this.author = author;
        this.id = idBook;
        this.availableStatus = availableStatus;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookStatus getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(BookStatus availableStatus) {
        this.availableStatus = availableStatus;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public static class SequenceGenerator {
        volatile static int n = 1;
        public static synchronized int getId() {
            return n++;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Имя: " + this.name);
        stringBuilder.append(", автор: " + (this.author));
        stringBuilder.append(", id : " + this.id);

        if (this.getAvailableStatus() == BookStatus.AVAILABLE) {
            stringBuilder.append(",  имеется на складе, ");
        }

        if (this.getAvailableStatus() == BookStatus.NOT_AVAILABLE) {
            stringBuilder.append(", отсутсвует на складе, ");
        }
        return stringBuilder.toString();
    }
}
