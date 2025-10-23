import java.util.*;

public class ElevatorSim {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        Elevator elevator = new Elevator(10);
        int maxFloor = 10;

        while (true) {
            System.out.print("\nEnter number of passengers to simulate (0 to quit): ");
            int n = sc.nextInt();
            if (n == 0) break;

            // Generate random passengers
            List<Passenger> passengers = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int start = rand.nextInt(maxFloor) + 1;

                int dest = rand.nextInt(maxFloor) + 1;
                while (dest == start) {
                    dest = rand.nextInt(maxFloor) + 1;
                }

                passengers.add(new Passenger(start, dest));
            }

            System.out.println("Passengers:");
            for (Passenger p : passengers) {
                System.out.println(p);
            }

            elevator.run(passengers);
        }

        System.out.println("Simulation ended.");
    }
}

class Elevator {
    private int currentFloor;
    private String direction;
    private final int maxFloor;
    private final List<Passenger> onboard = new ArrayList<>();

    public Elevator(int maxFloor) {
        this.currentFloor = 1;
        this.direction = "IDLE";
        this.maxFloor = maxFloor;
    }

    public void run(List<Passenger> passengers) {
        System.out.println("\nStarting elevator run...");
        direction = "UP";

        // Organize waiting passengers by their starting floor for efficient lookup
        Map<Integer, List<Passenger>> waiting = new HashMap<>();
        for (Passenger p : passengers) {
            if (!waiting.containsKey(p.startFloor)) {
                waiting.put(p.startFloor, new ArrayList<>());
            }
            waiting.get(p.startFloor).add(p);
        }

        // Continue until all passengers are picked up and delivered
        while (!waiting.isEmpty() || !onboard.isEmpty()) {
            System.out.println("\nFloor " + currentFloor + " (" + direction + ")");

            // Drop off passengers at their destination floor
            int dropped = 0;
            List<Passenger> toRemove = new ArrayList<>();
            for (Passenger p : onboard) {
                if (p.destinationFloor == currentFloor) {
                    toRemove.add(p);
                    dropped++;
                }
            }
            for (Passenger p : toRemove) {
                onboard.remove(p);
            }

            // Pick up passengers going in the same direction as the elevator
            int picked = 0;
            List<Passenger> waitingHere = waiting.get(currentFloor);
            if (waitingHere != null) {
                List<Passenger> toPickUp = new ArrayList<>();
                for (Passenger p : waitingHere) {
                    boolean goingUp = p.destinationFloor > p.startFloor;

                    // Only pick up if passenger's direction matches elevator's direction
                    boolean shouldPickUp = false;
                    if (direction.equals("UP") && goingUp) {
                        shouldPickUp = true;
                    }
                    if (direction.equals("DOWN") && !goingUp) {
                        shouldPickUp = true;
                    }

                    if (shouldPickUp) {
                        toPickUp.add(p);
                        picked++;
                    }
                }

                // Pick up the passengers
                for (Passenger p : toPickUp) {
                    onboard.add(p);
                    waitingHere.remove(p);
                }

                // Clean up empty floor entries
                if (waitingHere.isEmpty()) {
                    waiting.remove(currentFloor);
                }
            }

            System.out.println("Dropped off: " + dropped + ", Picked up: " + picked);

            // Move to next floor or reverse direction at boundaries
            if (direction.equals("UP")) {
                if (currentFloor < maxFloor) {
                    currentFloor++;
                } else {
                    direction = "DOWN";
                }
            } else if (direction.equals("DOWN")) {
                if (currentFloor > 1) {
                    currentFloor--;
                } else {
                    direction = "UP";
                }
            }
        }

        direction = "IDLE";
        System.out.println("All passengers delivered. Elevator idle at floor " + currentFloor + ".");
    }
}

class Passenger {
    public final int startFloor; 
    public final int destinationFloor;

    public Passenger(int startFloor, int destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    @Override
    public String toString() {
        String dir;
        if (destinationFloor > startFloor) {
            dir = "UP";
        } else {
            dir = "DOWN";
        }
        return "Passenger(start=" + startFloor + ", dest=" + destinationFloor + ", dir=" + dir + ")";
    }
}

