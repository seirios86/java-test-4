import java.time.*;
import java.time.format.DateTimeFormatter;

public class Deployment {

    private final String serviceName;
    private final Environment environment;
    private final ZonedDateTime startTime;
    private final Duration duration;

    public Deployment(String serviceName, Environment environment, String startTime) {
        this(serviceName, environment, startTime, null);
    }

    public Deployment(String serviceName, Environment environment, String startTime, Integer duration) {
        this.serviceName = serviceName;
        this.environment = environment;
        this.startTime = parseDateTime(startTime);
        this.duration = (duration != null) ? Duration.ofMinutes(duration) : environment.getDefaultDuration();
    }

    public String getServiceName() {
        return serviceName;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public ZonedDateTime getStartTime() {
        return getStartTime(null);
    }

    public ZonedDateTime getStartTime(ZoneId zoneId) {
        if (zoneId == null) {
            return startTime;
        }
        return startTime.withZoneSameInstant(zoneId);
    }

    public ZonedDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean isConflictWith(Deployment deployment) {
        Instant otherStartTime = Instant.from(deployment.getStartTime());
        Instant otherEndTime = otherStartTime.plus(deployment.getDuration());
        Instant thisStartTime = Instant.from(startTime);
        Instant thisEndTime = thisStartTime.plus(duration);
        return thisStartTime.isBefore(otherEndTime) && otherStartTime.isBefore(thisEndTime);
    }

    private static ZonedDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        return localDateTime.atZone(ZoneId.of("Asia/Seoul"));
    }

    @Override
    public String toString() {
        return "Deployment{" +
                "serviceName='" + serviceName + '\'' +
                ", envirionment=" + environment +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
