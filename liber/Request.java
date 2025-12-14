public class Request{
    //attributes:
    private String borrower;
    private String lender;
    private String status;
    private Boolean approval;
    private String rating;

    //constructor:
    Request(String borrower,String lender){
        this.borrower = borrower;
        this.lender = lender;
        this.status = "Pending Approval";
        this.approval = null;
        this.rating = null;
    }


    public void setApproval(boolean b){
        this.approval = b;
    }




    public void setStatus(String s){
        this.status = s;
        }
    }

    public void rate(int r){
        this.rating = r;
        //supposed to tell Borrower to call its own rating function to update its average
    }
}