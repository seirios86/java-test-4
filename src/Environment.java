import java.time.Duration;

public enum Environment {
    DEV(Duration.ofMinutes(30)),
    STAGE(Duration.ofMinutes(60)),
    PROD(Duration.ofMinutes(120));

    private Duration defaultDuration;

    Environment(Duration defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public Duration getDefaultDuration() {
        return defaultDuration;
    }
}
