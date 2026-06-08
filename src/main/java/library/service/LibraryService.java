package main.java.library.service;

import main.java.library.model.Book;
import main.java.library.util.FileManager;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private List<Book> books;
    public LibraryService()
    {
        books = FileManager.loadBooksFromCSV();
        FileManager.saveBooksToCSV(books);
    }
    public List<Book> getAllBooks()
    {
        return books;
    }
    public Optional<Book> findBookById(int id)
    {
        for (Book book : books)
        {
            if (book.getId() == id)
                return Optional.of(book);
        }
        return Optional.empty();
    }
    public void saveBooks()
    {
        FileManager.saveBooksToCSV(books);
    }
    public boolean editBookMetadata(int bookId, String newTitle, String newAuthor, String newPublisher, Integer newYear)
    {
        for (Book book : books)
        {
            if (book.getId() == bookId)
            {
                if (newTitle != null && !newTitle.isEmpty())
                    book.setTitle(newTitle);
                if (newAuthor != null && !newAuthor.isEmpty())
                    book.setAuthor(newAuthor);
                if (newPublisher != null && !newPublisher .isEmpty())
                    book.setPublisher(newPublisher);
                if (newYear != null)
                    book.setPublicationYear(newYear);
                FileManager.saveBooksToCSV(books);
                return true;
            }
        }
        return false;
    }
}
