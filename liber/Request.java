package liber;

public class Request {

    private User borrower;
    private User lender;
    private String status;
    private Boolean approval; 
    private Book book;
    private User requester;
    private User owner;


    public Request(User borrower, User lender, Book book) {
        this.borrower = borrower;
        this.lender = lender;
        this.book = book;
        this.status = "Pending Approval";
        this.approval = null;
    }

    public User getBorrower() { return borrower; }
    public User getLender() { return lender; }
    public Book getBook() { return book; }
    public User getRequester() { return requester; }
    public User getOwner() { return owner; }

    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }

    public Boolean getApproval() { return approval; }
    public void setApproval(Boolean b) { this.approval = b; }
}
