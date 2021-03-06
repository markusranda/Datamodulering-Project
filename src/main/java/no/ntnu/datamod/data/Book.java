package no.ntnu.datamod.data;


import java.sql.Blob;

public class Book extends Literature {

  private int idBook;
  private String authors;
  private int idBranch;
  private int quantity;
  private String genre;
  private String publisher;
  private String isbn;
  private byte[] image;
  private String branch;


  /**
   * Creates a new book. This constructor is to be used when there is a image.
   * @param publisher publisher
   * @param title title
   * @param idBook idBook
   * @param authors authors
   * @param isbn isbn
   * @param image image
   */
  public Book(String publisher, String title, int idBook, String authors, String isbn, byte[] image) {
    super(publisher, title);
    this.idBook = idBook;
    this.authors = authors;
    this.isbn = isbn;
    this.image = image;
  }

  public Book(int idBook, String title, String authors, int idBranch, int quantity, String genre, String publisher, String branch , String isbn, byte[] image) {
    super(publisher, title);
    this.idBook = idBook;
    this.authors = authors;
    this.idBranch = idBranch;
    this.quantity = quantity;
    this.genre = genre;
    this.publisher = publisher;
    this.isbn = isbn;
    this.branch = branch;
    this.image = image;
  }

  /**
   * Creates a new book. This constructor is to be used when there is no image.
   * @param publisher publisher
   * @param title title
   * @param idBook idBook
   * @param authors authors
   * @param isbn isbn
   */
  public Book(String publisher, String title, int idBook, String authors, String isbn) {
    super(publisher, title);
    this.idBook = idBook;
    this.authors = authors;
    this.isbn = isbn;
    this.image = null;
  }


  public int getIdBook() { return idBook; }


  public String getAuthors() {
    return authors;
  }


  public String getIsbn() {
    return isbn;
  }


  public int getIdBranch() {
        return idBranch;
    }


  public int getQuantity() {
        return quantity;
    }


  public String getGenre() {
        return genre;
    }


  public String getBranch() {
    return branch;
  }


  @Override
  public String getPublisher() {
      return publisher;
  }

  @Override
  public byte[] getImage() {
    return image;
  }
}
