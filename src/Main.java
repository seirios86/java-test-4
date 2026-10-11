import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        Deployment deployment = new Deployment("payment-api", Envirionment.PROD, "2026-10-20 22:00");
        Deployment deployment2 = new Deployment("order-api", Envirionment.PROD, "2026-10-20 23:30", 60);
        StringBuilder sb = new StringBuilder("=== Deployment Report ===").append("\n")
                .append(getReport(deployment))
                .append(getReport(deployment2))
                .append("Conflict: " + (deployment2.isConflictWith(deployment) ? "YES" : "NO"));
        System.out.println(sb);
    }

    private static StringBuilder getReport(Deployment deployment) {
        return new StringBuilder(deployment.getServiceName()).append(" ").append("[").append(deployment.getEnvirionment().name()).append("]").append("\n")
                .append("Seoul: ").append(formatDateTime(deployment.getStartTime())).append("\n")
                .append("New York: ").append(formatDateTime(deployment.getStartTime(ZoneId.of("America/New_York")))).append("\n")
                .append("London: ").append(formatDateTime(deployment.getStartTime(ZoneId.of("Europe/London")))).append("\n")
                .append("Duration: ").append(deployment.getDuration().toMinutes()).append(" minutes").append("\n\n");
    }

    private static String formatDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return zonedDateTime.format(formatter);
    }
}