package digital_book_store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DigitalBookStore {
    private static DigitalBookStore digit = null;
    private List<Book> books = initializeBooks();
    private List<Order> orders = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private DigitalBookStore store;

    private DigitalBookStore() {
    }

    public static DigitalBookStore getInstance() {
        if (digit == null) {
            digit = new DigitalBookStore();
        }
        return digit;
    }

    private List<Book> initializeBooks() {
        return new ArrayList<>(Arrays.asList(
                new Book("Унесённые ветром", "Маргаретт Митчелл", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 2500),
                new Book("Царство страха", "Хантер С Томпсон", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1310),
                new Book("Война и мир", "Лев Толстой", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 5050),
                new Book("Старик и море", "Эрнест Хемингуэй", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 3423),
                new Book("Капитанская дочка", "Александр Пушкин", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 5000),
                new Book("Капитал", "Карл Маркс", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 10000),
                new Book("Обломов", "Иван Гончаров", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 2350),
                new Book("Вино из одуванчиков", "Рэй Брэдбери", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1100),
                new Book("Три сестры", "Антон Чехов", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1550),
                new Book("Чайка по имени Джонатан Ливингстон", "Ричард Бах", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1525)));
    }

    public static DigitalBookStore getDigit() {
        return digit;
    }

    public static void setDigit(DigitalBookStore digit) {
        DigitalBookStore.digit = digit;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public DigitalBookStore getStore() {
        return store;
    }

    public void setStore(DigitalBookStore store) {
        this.store = store;
    }
    public List<Book> getAviableBook() {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAvailableStatus() == BookStatus.AVAILABLE) {
                result.add(book);
            }
        }
        return result;
    }

    public Book getBookById(int id) {
        for (Book book : this.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public Order getOrderById(int id) {
        for (Order order : this.orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public void addBook(Integer bookId) {
        Book book = getBookById(bookId);
        book.setAvailableStatus(BookStatus.AVAILABLE);

        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getBookId() == bookId) {
                requests.remove(requests.get(i));
                break;
            }
        }
    }

    public void removeBook(Integer idBook) {
        Book book = getBookById(idBook);
        book.setAvailableStatus(BookStatus.NOT_AVAILABLE);
    }

    public void createRequest(Integer bookID) {
        Request request = new Request(Request.SequenceGenerator.getId(), bookID);
        requests.add(request);

    }

    public Order createOrder(List<Book> books) {

        Order order = new Order(OrderStatus.NEW, books);

        for (int i = 0; i < order.getBooks().size(); i++) {
            Book book = order.getBooks().get(i);
            if (book.getAvailableStatus() != BookStatus.AVAILABLE) {
                createRequest(book.getId());
            }
        }
        order.setStatus(OrderStatus.NEW);
        orders.add(order);
        return order;
    }

    public void closeOrder(Integer id) {
        Order order = getOrderById(id);
        for (int i = 0; i < order.getBooks().size(); i++) {
            Book book = order.getBooks().get(i);
            order.setStatus(OrderStatus.COMPLETED);
        }
    }

    public void changeOrderStatus(Integer id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
    }

    public void canceledOrder(Integer id) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.CANCELED);

    }
}
