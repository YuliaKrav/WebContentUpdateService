package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.boot.convert.DurationUnit;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public record Scheduler(@DurationUnit(ChronoUnit.SECONDS) Duration interval) {
}
