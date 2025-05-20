import java.util.ArrayDeque;
import java.util.Deque;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Iterator;

public class BibliotecaVirtual {
    private static LinkedList<Book> books = new LinkedList<>();
    private static Deque<Book> history = new ArrayDeque<>();
    private static Scanner scanner = new Scanner(System.in);
    private static BookGraph bookGraph = new BookGraph(books);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Biblioteca Virtual ---");
            System.out.println("1. Adicionar livro");
            System.out.println("2. Remover livro");
            System.out.println("3. Listar todos os livros");
            System.out.println("4. Pesquisar livro por título");
            System.out.println("5. Emprestar livro");
            System.out.println("6. Devolver livro");
            System.out.println("7. Ver lista de espera de um livro");
            System.out.println("8. Ver histórico de navegação");
            System.out.println("9. Obter recomendações de livros");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    searchBookByTitle();
                    break;
                case 5:
                    checkoutBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    viewWaitlist();
                    break;
                case 8:
                    viewHistory();
                    break;
                case 9:
                    getRecommendations();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

     private static void getRecommendations() {
    System.out.print("Digite o título do livro base para recomendações: ");
    String title = scanner.nextLine();

    Book baseBook = bookGraph.findBook(title);
    if (baseBook != null) {
        Map<Book, Integer> distancias = bookGraph.djikstraSimples(baseBook);
        if (!distancias.isEmpty()) {
            System.out.println("\n--- Livros mais próximos de \"" + baseBook.getTitle() + "\" ---");
            distancias.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(baseBook))
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    Book livro = entry.getKey();
                    int distancia = entry.getValue();
                    System.out.println("• " + livro.getTitle() + " (Distância: " + distancia + ")");
                });
        } else {
            System.out.println("Nenhuma recomendação encontrada para este livro.");
        }
    } else {
        System.out.println("Livro não encontrado no sistema.");
    }
}


    private static void addBook() {
    try {
        System.out.print("Digite o título do livro: ");
        String title = scanner.nextLine();
        
        System.out.print("Digite o autor do livro: ");
        String author = scanner.nextLine();
        
        System.out.print("Digite o ano de publicação: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Digite o gênero do livro: ");
        String genre = scanner.nextLine();
        
        Book newBook = new Book(title, author, year, genre);
        books.add(newBook);
        bookGraph.addBookAndConnect(newBook);

        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title) && b.getAuthor().equalsIgnoreCase(author)) {
                System.out.println("Este livro já existe na biblioteca.");
                return;
            }
        }
        
        System.out.println("Livro adicionado com sucesso!");
    } catch (InputMismatchException e) {
        System.out.println("Erro: Ano deve ser um número inteiro!");
        scanner.nextLine();
    }
}

    private static void removeBook() {
        System.out.print("Digite o título do livro a ser removido: ");
        String title = scanner.nextLine();

        boolean removed = false;
        Iterator<Book> iterator = books.iterator();

        while(iterator.hasNext()) {
            Book book = iterator.next();
            if(book.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                removed = true;
            }
        }

        if(removed) {
            System.out.println("Livro removido com sucesso!");
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private static void listBooks() {
        if (books.isEmpty()) {
            System.out.println("A biblioteca está vazia!");
            return;
        }
        
        System.out.println("\n--- Lista de Livros ---");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    private static void searchBookByTitle() {
        System.out.print("Digite o título do livro: ");
        String title = scanner.nextLine();
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println(book);
                history.push(book);
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    private static void checkoutBook() {
        System.out.print("Digite o título do livro a ser emprestado: ");
        String title = scanner.nextLine();
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isAvailable()) {
                    book.checkOut();
                    System.out.println("Livro emprestado com sucesso.");
                } else {
                    System.out.print("Livro já emprestado. Digite seu nome para entrar na lista de espera: ");
                    String user = scanner.nextLine();
                    book.addToWaitlist(user);
                    System.out.println("Você foi adicionado à lista de espera.");
                }
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    private static void returnBook() {
        System.out.print("Digite o título do livro a ser devolvido: ");
        String title = scanner.nextLine();
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isAvailable()) {
                    book.returnBook();
                    Queue<String> waitlist = book.getWaitingQueue();
                    if (!waitlist.isEmpty()) {
                        String nextUser = waitlist.poll();
                        System.out.println("Livro devolvido. Próximo usuário na lista de espera: " + nextUser);
                    } else {
                        System.out.println("Livro devolvido com sucesso.");
                    }
                } else {
                    System.out.println("Este livro já está disponível.");
                }
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    private static void viewWaitlist() {
        System.out.print("Digite o título do livro: ");
        String title = scanner.nextLine();
        
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                Queue<String> waitlist = book.getWaitingQueue();
                if (waitlist.isEmpty()) {
                    System.out.println("Não há usuários na lista de espera para este livro.");
                } else {
                    System.out.println("Lista de espera para " + title + ":");
                    int i = 1;
                    for (String user : waitlist) {
                        System.out.println(i++ + ". " + user);
                    }
                }
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    private static void viewHistory() {
        if(history.isEmpty()) {
            System.out.println("Histórico de navegação vazio.");
            return;
        }
        
        System.out.println("\n--- Histórico de Navegação (Mais recente primeiro) ---");
        int i = 1;
        for(Book book : history) {
            System.out.println(i++ + ". " + book);
        }
    }
}