package main.java.library.model;

public class Book {

    private int id ;
    private String title;
    private String textFilePath;
    private String author;
    private String publisher;
    private int publicationYear;
    private static int bookCounter = 1;

    public Book(String title,String textFilePath,String author,String publisher,int publicationYear)
    {
        id = bookCounter;
        this.title = title;
        this.textFilePath = textFilePath;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;

        bookCounter++;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter & Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter & Setter for textFilePath
    public String getTextFilePath() {
        return textFilePath;
    }

    public void setTextFilePath(String textFilePath) {
        this.textFilePath = textFilePath;
    }

    // Getter & Setter for author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter & Setter for publisher
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    // Getter & Setter for publicationYear
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString()
    {
        return "[" + id + "] " + title + "  |  " + author + "  |  " + publisher + "  |  " + publicationYear;
    }
}
