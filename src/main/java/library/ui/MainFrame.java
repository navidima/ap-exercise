package main.java.library.ui;

import main.java.library.model.Book;
import main.java.library.service.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {
    public LibraryService service;
    public JPanel booksPanel;
    public JPanel menuPanel;
    public JPanel readerPanel;
    public Book selectedBook;
    public JTextField titleField;
    public JTextField authorField;
    public JTextField publisherField;
    public JTextField yearField;
    public JTextArea pageArea;
    public List<String> pages;
    public int currentPage;
    public MainFrame()
    {
        service = new LibraryService();
        setTitle("Personal Library");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        readerPanel = new JPanel();
        readerPanel.setLayout(null);
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(0xADFFBD));

        booksPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(5, 1, 10, 10);
        booksPanel.setLayout(gridLayout);
        booksPanel.setBackground(new Color(0xADFFBD));

        createBooksPanel();
        setVisible(true);
    }
    public void createBooksPanel()
    {
        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        List<Book> books = service.getAllBooks();

        for (Book book : books)
        {
            JButton button = new JButton(book.getTitle());
            button.setFont(buttonFont);

            button.addActionListener(_ -> {
                selectedBook = book;
                menuPanel.removeAll();
                showBookMenu();
            });
            booksPanel.add(button);
        }
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.addActionListener(_ -> {
            booksPanel.removeAll();
            createBooksPanel();
        });

        booksPanel.add(refreshButton);

        getContentPane().removeAll();
        add(booksPanel);
        revalidate();
        repaint();
    }
    public void showBookMenu()
    {

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(150, 50, 100, 30);
        titleField = new JTextField();
        titleField.setBounds(270, 50, 350, 30);
        titleField.setText(selectedBook.getTitle());

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(150, 110, 100, 30);
        authorField = new JTextField();
        authorField.setBounds(270, 110, 350, 30);
        authorField.setText(selectedBook.getAuthor());

        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setBounds(150, 170, 100, 30);
        publisherField = new JTextField();
        publisherField.setBounds(270, 170, 350, 30);
        publisherField.setText(selectedBook.getPublisher());

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(150, 230, 100, 30);
        yearField = new JTextField();
        yearField.setBounds(270, 230, 350, 30);
        yearField.setText(String.valueOf(selectedBook.getPublicationYear()));

        // Count line must show here

        JButton readButton = new JButton("Read Book");
        readButton.setBounds(150, 350, 110, 35);
        readButton.addActionListener(_ -> openBook(false));

        JButton editButton = new JButton("Edit Book");
        editButton.setBounds(275, 350, 110, 35);
        editButton.addActionListener(_ -> openBook(true));

        JButton saveButton = new JButton("Save changes");
        saveButton.setBounds(400, 350, 120, 35);
        saveButton.addActionListener(_ -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = publisherField.getText();
            try {
                int year = Integer.parseInt(yearField.getText());

                boolean situation = service.editBookMetadata(selectedBook.getId(),title,author,publisher,year);
                if (situation)
                    JOptionPane.showMessageDialog(MainFrame.this, "Information changed successfully");
                else
                    JOptionPane.showMessageDialog(MainFrame.this, "An error occurred");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this, "Please inter a valid number for year");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(535, 350, 85, 35);
        backButton.addActionListener(_ -> {
            booksPanel.removeAll();
            createBooksPanel();
        });

        menuPanel.add(titleLabel);
        menuPanel.add(authorLabel);
        menuPanel.add(publisherLabel);
        menuPanel.add(yearLabel);

        menuPanel.add(titleField);
        menuPanel.add(authorField);
        menuPanel.add(publisherField);
        menuPanel.add(yearField);

        menuPanel.add(saveButton);
        menuPanel.add(readButton);
        menuPanel.add(editButton);
        menuPanel.add(backButton);

        getContentPane().removeAll();
        add(menuPanel);
        revalidate();
        repaint();

    }

    public void openBook(boolean editable) {

        readerPanel.removeAll();
        pages = service.getBookPages(selectedBook, 3);
        currentPage = 0;

        JLabel bookTitle = new JLabel(selectedBook.getTitle(), SwingConstants.CENTER);
        bookTitle.setBounds(280, 10, 240, 25);
        bookTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));

        pageArea = new JTextArea();
        pageArea.setBounds(80, 40, 640, 400);
        pageArea.setMargin(new Insets(15, 15, 10, 15));
        pageArea.setEditable(editable);
        pageArea.setLineWrap(true);
        pageArea.setWrapStyleWord(true);
        pageArea.setText(pages.get(currentPage));
        pageArea.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        pageArea.setBackground(Color.decode("#F5F1E8"));
        pageArea.setForeground(Color.decode("#2C2C2C"));
        pageArea.setCaretColor(Color.red);

        JButton nextPage = new JButton(">");
        nextPage.setBounds(660, 450, 60, 30);
        nextPage.addActionListener(_ -> {
            if (currentPage < pages.size() - 1) {
                currentPage ++;
                pageArea.setText(pages.get(currentPage));
            }
        });

        JButton previousPage = new JButton("<");
        previousPage.setBounds(80, 450, 60, 30);
        previousPage.addActionListener(_ -> {
            if (currentPage > 0) {
                currentPage --;
                pageArea.setText(pages.get(currentPage));
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(370, 450, 80, 30);
        backButton.addActionListener(_ -> showBookMenu());

        readerPanel.add(bookTitle);
        readerPanel.add(pageArea);
        readerPanel.add(nextPage);
        readerPanel.add(previousPage);
        readerPanel.add(backButton);

        if (editable) {
            JButton apply = new JButton("Apply");
            apply.setBounds(410, 450, 80, 30);
            backButton.setBounds(310, 450, 80, 30);
            apply.addActionListener(_ -> {
                pages.set(currentPage, pageArea.getText());
                try {
                    service.editBookContent(selectedBook.getId(), String.join("", pages));
                    JOptionPane.showMessageDialog(readerPanel, "Your changes have been successfully sav");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(readerPanel,
                            String.format("Something Went Wrong In Editing File %s",
                                    new File(selectedBook.getTextFilePath()).getAbsolutePath()));
                }
            });
            readerPanel.add(apply);
        }

        getContentPane().removeAll();
        add(readerPanel);
        revalidate();
        repaint();
    }
}
