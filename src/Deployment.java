import java.time.*;
import java.time.format.DateTimeFormatter;

public class Deployment {

    private String serviceName;
    private Envirionment envirionment;
    private ZonedDateTime startTime;
    private Duration duration;

    public Deployment(String serviceName, Envirionment envirionment, String startTime) {
        this(serviceName, envirionment, startTime, null);
    }

    public Deployment(String serviceName, Envirionment envirionment, String startTime, Integer duration) {
        this.serviceName = serviceName;
        this.envirionment = envirionment;
        this.startTime = parseDateTime(startTime);
        this.duration = (duration != null) ? Duration.ofMinutes(duration) : envirionment.getDefaultDuration();
    }

    public String getServiceName() {
        return serviceName;
    }

    public Envirionment getEnvirionment() {
        return envirionment;
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

    public Duration getDuration() {
        return duration;
    }

    public boolean isConflictWith(Deployment deployment) {
        Instant otherStartTime = Instant.from(deployment.getStartTime());
        Instant otherEndTime = otherStartTime.plus(deployment.getDuration());
        Instant thisStartTime = Instant.from(startTime);
        Instant thisEndTime = thisStartTime.plus(duration);
        return thisStartTime.isBefore(otherEndTime) || thisEndTime.isAfter(otherStartTime);
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
                ", envirionment=" + envirionment +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
