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
                Book newBook = new Book(details[0],BOOKS_TEXT_DIR + File.separator + details[4],details[1],details[2],Integer.parseInt(details[3]));                books.add(newBook);
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
            bw.write("title,author,publisher,year,file");
            bw.newLine();
            for (Book book : books) {
                bw.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPublisher() + "," + book.getPublicationYear() + "," + book.getTextFilePath());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readBookPages(String filePath, int linesPerPage) {
        List<String> pages = new ArrayList<>();
        File book = new File(filePath);

        try (BufferedReader bookReader = new BufferedReader(new FileReader(book))){
            StringBuilder page = new StringBuilder();
            String line;

            outer:
            while (true) {
                for (int i = 0; i < linesPerPage; i++) {
                    if ((line = bookReader.readLine()) != null) {
                        page.append(line).append(System.lineSeparator());
                    } else {
                        pages.add(page.toString());
                        break outer;
                    }
                }
                pages.add(page.toString());
                page.setLength(0);
            }
        } catch (IOException e) {
            pages.add(String.format("file \" %s \" does not exist", book.getAbsolutePath()));
        }
        return pages;
    }

    public static int countLines(String filePath) {
        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            while (reader.readLine() != null) {
                counter++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return counter;
    }

    public static void writeBookText(String filePath, String content) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))){
            writer.write(content);
        }
    }

    public static String readFullText(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            return reader.readAllAsString();
        } catch (IOException ignored) {
            return "";
        }
    }
}
