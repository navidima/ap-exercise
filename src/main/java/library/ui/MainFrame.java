package main.java.library.ui;

import main.java.library.model.Book;
import main.java.library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
    public List pages;
    public int currentPage;
    public MainFrame()
    {
        service = new LibraryService();
        setTitle("Personal Library");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Font globalFont = new Font("Arial", Font.PLAIN, 14);

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

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBook = book;
                    menuPanel.removeAll();
                    showBookMenu();
                }
            });
            booksPanel.add(button);
        }
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(buttonFont);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                booksPanel.removeAll();
                createBooksPanel();
            }
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
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBook(false);
            }
        });

        JButton editButton = new JButton("Edit Book");
        editButton.setBounds(275, 350, 110, 35);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBook(true);
            }
        });

        JButton saveButton = new JButton("Save changes");
        saveButton.setBounds(400, 350, 120, 35);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(535, 350, 85, 35);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                booksPanel.removeAll();
                createBooksPanel();
            }
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

    public void openBook(boolean editable)
    {

    }

}
