package digital_book_store;

import java.time.LocalDate;
import java.util.*;

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
                new Book("Унесённые ветром", "Маргаретт Митчелл", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 2500, LocalDate.of(2011, 11, 18), LocalDate.parse("2016-08-09")),
                new Book("Царство страха", "Хантер С Томпсон", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1310, LocalDate.of(2017, 12, 28), LocalDate.parse("2021-08-09")),
                new Book("Война и мир", "Лев Толстой", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 5050, LocalDate.of(2015, 7, 22)),
                new Book("Старик и море", "Эрнест Хемингуэй", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 3423, LocalDate.of(1998, 1, 5), LocalDate.parse("2021-10-30")),
                new Book("Капитанская дочка", "Александр Пушкин", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 2830, LocalDate.of(2019, 3, 18)),
                new Book("Капитал", "Карл Маркс", Book.SequenceGenerator.getId(), BookStatus.NOT_AVAILABLE, 10000, LocalDate.of(2002, 5, 5)),
                new Book("Обломов", "Иван Гончаров", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 2350, LocalDate.of(2012, 1, 1), LocalDate.parse("2018-08-09")),
                new Book("Вино из одуванчиков", "Рэй Брэдбери", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1100, LocalDate.of(2011, 7, 18), LocalDate.parse("2021-11-12")),
                new Book("Три сестры", "Антон Чехов", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1550, LocalDate.of(2013, 10, 28), LocalDate.of(2021, 12, 1)),
                new Book("Чайка по имени Джонатан Ливингстон", "Ричард Бах", Book.SequenceGenerator.getId(), BookStatus.AVAILABLE, 1525, LocalDate.of(1992, 3, 28), LocalDate.parse("2013-05-05"))));
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

    public Book getBookByName(String name) {
        for (Book book : this.books) {
            if (book.getName().equalsIgnoreCase(name)){
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

    public List<Book> getUnsoldForSixMonths() {         // залежавшиеся книги

        List<Book> list = new ArrayList<>();
        for (Book book : getAviableBook()) {
            if (book.getDeliviryDate().plusMonths(6).isBefore(LocalDate.now())) {
                list.add(book);
            }
        }
        return list;
    }

    public void getTotalAmountInperiod(LocalDate begin, LocalDate end) {        //  сумма вырочки в определ нный период
        int totalAmount = 0;
        for (Order order : orders) {
            if ((order.getExecutionDate().isAfter(begin) && order.getExecutionDate().isBefore(end) && order.getStatus() == OrderStatus.COMPLETED)) {
                totalAmount += order.getOrderPrice();
            }
        }
        System.out.println(totalAmount);
    }

    public void sortedBooksNames(List<Book> books) {
        Collections.sort(books, new NameComparator());
    }

    public void sortedBookByPublicationDate(List<Book> books) {
        Collections.sort(books, new PublicationDateComparator());
    }

    public void sortedBookByPrice(List<Book> books) {
        Collections.sort(books, new BookPriceComparator());
    }

    public void sortedBookByAviableStatus(List<Book> books) {
        Collections.sort(books, new BookStatusComparator());
    }


    public void sortedOrderByPrice(List<Order> orders) {
        Collections.sort(orders, new OrderPriceComparator());
    }

    public void sortedOrderByExecutionDate(List<Order> orders) {
        Collections.sort(orders, new executionDateComparator());
    }

    public void sortedByOrderStatus(List<Order> orders) {
        Collections.sort(orders, new OrderStatusComparator());
    }

    class NameComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            return book1.getName().compareTo(book2.getName());
        }
    }

    class PublicationDateComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            if (book1.getPublicationDate().isAfter(book2.getPublicationDate())) {
                return 1;
            } else if (book1.getPublicationDate().equals(book2.getPublicationDate())) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    class BookPriceComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            if (book1.getPrice() == book2.getPrice()) {
                return 0;
            } else if (book1.getPrice() < book2.getPrice()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    class BookStatusComparator implements Comparator<Book> {
        @Override
        public int compare(Book book1, Book book2) {
            return book1.getAvailableStatus().compareTo(book2.getAvailableStatus());
        }
    }

    class OrderPriceComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            if (o1.getOrderPrice() == o2.getOrderPrice()) {
                return 0;
            } else if (o1.getOrderPrice() < o2.getOrderPrice()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    class OrderStatusComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.getStatus().compareTo(o2.getStatus());
        }
    }

    class executionDateComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            if (o1.getExecutionDate().isAfter(o2.getExecutionDate())) {
                return 1;
            } else if (o1.getExecutionDate().equals(o2.getExecutionDate())) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
