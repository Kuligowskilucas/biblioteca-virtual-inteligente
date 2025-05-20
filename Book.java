import java.util.LinkedList;
import java.util.Queue;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int year;
    private String genre;
    private boolean isAvailable;
    private Queue<String> waitingQueue;

    public Book(String title, String author, int year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.isAvailable = true;
        this.waitingQueue = new LinkedList<>();
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public Queue<String> getWaitingQueue() { return waitingQueue; }

    // Setters e outros métodos
    public void checkOut() { isAvailable = false; }
    public void returnBook() { isAvailable = true; }
    public void addToWaitlist(String userName) { waitingQueue.add(userName); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && 
               Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

    @Override
    public String toString() {
        return "Título: " + title + " | Autor: " + author + 
               " | Ano: " + year + " | Gênero: " + genre + 
               " | Status: " + (isAvailable ? "Disponível" : "Emprestado");
    }
}