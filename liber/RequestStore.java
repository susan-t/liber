package liber;

import java.util.ArrayList;
import java.util.List;

public class RequestStore {

    private static final List<Request> allRequests = new ArrayList<>();

    public static void addRequest(Request request) {
        if (request != null) allRequests.add(request);
    }

    public static void removeRequest(Request request) {
        allRequests.remove(request);
    }

    public static List<Request> getRequestsFor(User lender) {
        List<Request> result = new ArrayList<>();
        for (Request r : allRequests) {
            if (r.getLender().equals(lender)) result.add(r);
        }
        return result;
    }

    public static List<Request> getRequestsByBorrower(User borrower) {
        List<Request> result = new ArrayList<>();
        for (Request r : allRequests) {
            if (r.getBorrower().equals(borrower)) result.add(r);
        }
        return result;
    }
}
