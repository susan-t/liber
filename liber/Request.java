package liber;

public class Request {

    private final User borrower;   
    private final User lender;     
    private final Book book;

    private String status;
    private Boolean approval;

    public Request(User borrower, User lender, Book book) {
        this.borrower = borrower;
        this.lender = lender;
        this.book = book;
        this.status = "Pending";
        this.approval = null;
    }

    public User getBorrower() { return borrower; }
    public User getRequester() { return borrower; } 
    public User getLender() { return lender; }
    public Book getBook() { return book; }
    public String getStatus() { return status; }
    public Boolean getApproval() { return approval; }

    public void setStatus(String status) { this.status = status; }
    public void setApproval(Boolean approval) { this.approval = approval; }
}
