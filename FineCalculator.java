import java.time.LocalDateTime;
import java.time.Duration;

public class FineCalculator {
    static final double FINE_PER_SECOND = 0.06;

    public static double calculateFine(LocalDateTime dueDate) {
        LocalDateTime now = LocalDateTime.now();  // Get current time
        Duration duration = Duration.between(dueDate, now);
        long secondsLate = duration.getSeconds();

        if (secondsLate > 0) {
            return secondsLate * FINE_PER_SECOND;
        } else {
            return 0.0;
        }
    }
}
