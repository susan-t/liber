package liber;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestStore {

    private static final List<Request> requests = new ArrayList<>();

    public static void addRequest(Request req) {
        requests.add(req);
    }

    public static void removeRequest(Request req) {
        requests.remove(req);
    }

    public static List<Request> getRequestsFor(User user) {
        return requests.stream()
                .filter(r -> r.getLender() != null && r.getLender().equals(user))
                .collect(Collectors.toList());
    }

    public static List<Request> getRequestsByBorrower(User user) {
        return requests.stream()
                .filter(r -> r.getRequester() != null && r.getRequester().equals(user))
                .collect(Collectors.toList());
    }

    public static List<Request> getAllRequests() {
        return new ArrayList<>(requests);
    }
}
