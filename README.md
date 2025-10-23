Implement an Elevator:

1. Code logic
        Variables:
            current floor (int currentFloor)
            maximum floors (int maxFloor)
            direction (String direction { "UP", "DOWN", "IDLE" })
            passengers (list of destination floors)
        Methods:
            addPerson(int startFloor, int destinationFloor)
            run() → runs until all passengers reach destinations using SCAN algorithm
                SCAN is what elevators use right now, basically when moving UP, the elevator stops for people who want to get off at the current floor and people waiting at this floor who also want to go UP. When there are no more UP requests above, the elevator switches to DOWN and repeats for DOWN direction.
        Data Structures:
            List<Integer> for passenger destinations
            Map<Integer, List<Passenger>> for waiting passengers per floor
2. User Interface
    Terminal interface with CLI input/output:
        Prints current floor and elevator state each step.
        Displays # of passengers getting on/off.
    Starting conditions:
        Elevator starts at floor 1.
        10 total floors (1-10).
3. Passenger Generation
    User inputs number of people (e.g. 5).
    Each person randomly gets:
        A starting floor randomly from available floors.
        A destination floor randomly chosen, but:
            Not equal to the start floor.
            Must be a valid floor in range.
    Their direction (UP or DOWN button clicked) is determined by the sign of destination - starting.
4. Simulation Loop
    For each simulation run:
        The user can generate passengers.
        The elevator picks up and drops off according to SCAN algorithm.
    Print after each move:
        “Floor 4: Dropped off 2 passengers, picked up 1 passenger.”
    Continue until all passengers are delivered.
    Return to idle state at the floor the last person was dropped at.
5. Repeat Simulation
    After the elevator finishes:
        Ask user for a new number of passengers.
        Re-run simulation with new random people (elevator resumes from whatever floor it was at).
6. Assumptions:
    Floors: 1–10 (inclusive)
    No basements or sublevels
    1 elevator only
    Infinite capacity
    Constant speed
    Randomized passengers per run
    New people don't join while simulation is running
7. Unimplemented Features:
    Multiple elevators
    Capacity limits
    GUI visualization
    Priority passengers
    Handling new people midway through simulation
