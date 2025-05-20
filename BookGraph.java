import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BookGraph {
    private Map<Book, Set<Book>> adjacencyList;
    private Map<String, Set<Book>> authorIndex;
    private Map<String, Set<Book>> genreIndex;

    public BookGraph(LinkedList<Book> books) {
        adjacencyList = new HashMap<>();
        authorIndex = new HashMap<>();
        genreIndex = new HashMap<>();
    
        Book[] defaultBooks = {
            new Book("1984", "George Orwell", 1949, "Distopia"),
            new Book("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, "Fantasia"),
            new Book("Orgulho e Preconceito", "Jane Austen", 1813, "Romance"),
            new Book("Dom Quixote", "Miguel de Cervantes", 1605, "Clássico"),
            new Book("Cem Anos de Solidão", "Gabriel García Márquez", 1967, "Realismo Mágico"),
            new Book("A Revolução dos Bichos", "George Orwell", 1945, "Sátira"),
            new Book("O Hobbit", "J.R.R. Tolkien", 1937, "Fantasia"),
            new Book("Crime e Castigo", "Fiódor Dostoiévski", 1866, "Psicológico"),
            new Book("Ensaio sobre a Cegueira", "José Saramago", 1995, "Filosófico"),
            new Book("A Metamorfose", "Franz Kafka", 1915, "Existencialista")
        };
    
        for (Book book : defaultBooks) {
            addBook(book);     
            books.add(book);    
        }
    
        autoGenerateRelationships();
    }
    

    private void addBook(Book book) {
        adjacencyList.putIfAbsent(book, new HashSet<>());
        
        authorIndex.computeIfAbsent(book.getAuthor().toLowerCase(), 
            k -> new HashSet<>()).add(book);
        
        genreIndex.computeIfAbsent(book.getGenre().toLowerCase(), 
            k -> new HashSet<>()).add(book);
    }

    private void autoGenerateRelationships() {
        for (Book book : adjacencyList.keySet()) {
            connectSameAuthor(book);
            connectSameGenre(book);
            connectSimilarPublicationYear(book, 10);
        }
    }

    private void connectSameAuthor(Book book) {
        authorIndex.getOrDefault(book.getAuthor().toLowerCase(), new HashSet<>())
            .forEach(other -> addEdge(book, other));
    }

    private void connectSameGenre(Book book) {
        genreIndex.getOrDefault(book.getGenre().toLowerCase(), new HashSet<>())
            .stream()
            .filter(other -> !other.equals(book))
            .forEach(other -> addEdge(book, other));
    }

    private void connectSimilarPublicationYear(Book book, int yearRange) {
        adjacencyList.keySet().stream()
            .filter(other -> Math.abs(book.getYear() - other.getYear()) <= yearRange)
            .forEach(other -> addEdge(book, other));
    }

    public void addEdge(Book book1, Book book2) {
        adjacencyList.computeIfAbsent(book1, k -> new HashSet<>()).add(book2);
        adjacencyList.computeIfAbsent(book2, k -> new HashSet<>()).add(book1);
    }

    public Set<Book> getRecommendations(Book book) {
        return adjacencyList.getOrDefault(book, new HashSet<>());
    }

    public Book findBook(String title) {
        return adjacencyList.keySet().stream()
            .filter(b -> b.getTitle().equalsIgnoreCase(title))
            .findFirst()
            .orElse(null);
    }

    public void addBookAndConnect(Book book) {
        addBook(book);
        connectSameAuthor(book);
        connectSameGenre(book);
        connectSimilarPublicationYear(book, 10);
    }

    public Map<Book, Integer> djikstraSimples(Book origem) {
    Map<Book, Integer> distancias = new HashMap<>();
    Queue<Book> fila = new LinkedList<>();

    distancias.put(origem, 0);
    fila.add(origem);

    while (!fila.isEmpty()) {
        Book atual = fila.poll();
        int distanciaAtual = distancias.get(atual);

        for (Book vizinho : adjacencyList.getOrDefault(atual, new HashSet<>())) {
            if (!distancias.containsKey(vizinho)) {
                distancias.put(vizinho, distanciaAtual + 1);
                fila.add(vizinho);
            }
        }
    }

    return distancias;
}

}