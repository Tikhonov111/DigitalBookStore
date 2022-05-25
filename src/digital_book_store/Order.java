package digital_book_store;

import java.util.List;

public class Order {
    private Integer id;
    private OrderStatus status;
    private List<Book> books;
    private Integer orderPrice;

        public Order(OrderStatus status, List<Book> books) {
        this.id = SequenceGenerator.getId();
        this.status = status;
        this.books = books;

        }

    public static class SequenceGenerator {
        volatile static int n = 1;
        public static synchronized int getId() {
            return n++;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }
}
