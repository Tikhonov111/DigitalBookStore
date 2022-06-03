package digital_book_store;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Integer id;
    private OrderStatus status;
    private List<Book> books;
    private Integer orderPrice;

    private LocalDate executionDate;
    private String consumer;

        public Order(OrderStatus status, List<Book> books) {
        this.id = SequenceGenerator.getId();
        this.status = status;
        this.books = books;
        this.orderPrice = countOrderPrice();
        }

        public Order(String consumer, OrderStatus status, List<Book> books, LocalDate executionDate) {
        this.consumer = consumer;
        this.id = SequenceGenerator.getId();
        this.status = status;
        this.books = books;
        this.executionDate = executionDate;
        this.orderPrice = countOrderPrice();
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

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public int countOrderPrice() {
        int price = 0;
        for (Book book : books) {
            price += book.getPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id заказа:  " + id);

        if(consumer != null) {
            stringBuilder.append(", имя покупателя " + consumer);
        }
        stringBuilder.append(",  статус заказа: " + status);
        stringBuilder.append(", id книги в заказе: ");
        for( Book book : books) {
            stringBuilder.append( "Название: " + book.getName() + ", цена:  " + book.getPrice() + "; ");
        }

        if(executionDate != null )
            stringBuilder.append("Время исполнения заказа " + executionDate);

        stringBuilder.append(" Цена заказа " + orderPrice);
        return stringBuilder.toString();
    }
}
