import java.time.Duration;

public enum Envirionment {
    DEV(Duration.ofMinutes(30)),
    STAGE(Duration.ofMinutes(60)),
    PROD(Duration.ofMinutes(120));

    private Duration defaultDuration;

    Envirionment(Duration defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public Duration getDefaultDuration() {
        return defaultDuration;
    }
}
