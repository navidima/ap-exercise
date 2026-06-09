package main.java.library.util;

import main.java.library.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    public static final String BOOKS_TEXT_DIR = "data" + File.separator + "books_text";
    public static final String BOOK_LIST_CSV = BOOKS_TEXT_DIR + File.separator + "Book_List.txt";

    public static List<Book> loadBooksFromCSV()
    {
        List<Book> books = new ArrayList<>();
        File bookList = new File(BOOK_LIST_CSV);

        try (Scanner scn = new Scanner(bookList)) {
            scn.nextLine();
            while (scn.hasNextLine()) {
                String bookLine = scn.nextLine();
                String[] details = bookLine.split(",");
                Book newBook = new Book(details[0],BOOKS_TEXT_DIR + File.separator + details[4],details[1],details[2],Integer.parseInt(details[3]));
                books.add(newBook);
            }
        } catch (FileNotFoundException e) {
            System.err.println("file \"" + bookList.getAbsolutePath() + "\" does not exist");
        }

        return books;
    }
    public static void saveBooksToCSV(List<Book> books)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_LIST_CSV)))
        {
            bw.write("Title,Author,Publisher,Publication Year,Path");
            bw.newLine();
            for (Book book : books) {
                bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPublisher() + "," + book.getPublicationYear() + "," + new File(book.getTextFilePath()).getName());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
