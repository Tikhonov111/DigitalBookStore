package digital_book_store;

public class Request {
    private Integer requestId;
    private Integer bookId;

    public Request(int requestID, int bookID) {
        this.requestId = requestID;
        this.bookId = bookID;
    }

    DigitalBookStore shop = DigitalBookStore.getInstance();

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        StringBuilder stringB = new StringBuilder();
        stringB.append("Запрос номер:  " + requestId);
        stringB.append(", Книга в запросе: " + shop.getBookById(bookId));

        return stringB.toString();
    }

    public static class SequenceGenerator {
        volatile static int n = 1;
        public static synchronized int getId() {
            return n++;
        }
    }

}
