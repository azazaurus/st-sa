package ru.itislabs.datetime;

import java.time.*;
import java.util.function.*;
import java.util.stream.*;

public final class DateTimeIntervalGenerator {
	private DateTimeIntervalGenerator() {
	}

	public static Stream<DateTimeInterval> sequentialIntervals(
			LocalDateTime startDateTime,
			Duration intervalDuration) {
		return Stream.generate(
			new PeriodicalIntervalsGenerator(startDateTime, intervalDuration, intervalDuration));
	}

	public static Stream<DateTimeInterval> sequentialIntervals(
			LocalDateTime startDateTime,
			Duration intervalDuration,
			LocalDateTime lastDateTime) {
		var intervalCount = Duration.between(startDateTime, lastDateTime).toNanos()
			/ intervalDuration.toNanos();
		return sequentialIntervals(startDateTime, intervalDuration).limit(intervalCount);
	}

	public static Stream<DateTimeInterval> periodicalIntervals(
			LocalDateTime startDateTime,
			Duration intervalDuration,
			Duration period) {
		return Stream.generate(
			new PeriodicalIntervalsGenerator(startDateTime, intervalDuration, period));
	}

	public static Stream<DateTimeInterval> periodicalIntervals(
			LocalDateTime startDateTime,
			Duration intervalDuration,
			Duration period,
			LocalDateTime lastDateTime) {
		if (period.compareTo(intervalDuration) < 0)
			throw new IllegalArgumentException(
				"Period ("
					+ period
					+ ") must be greater than interval duration ("
					+ intervalDuration
					+ ")");

		var intervalCount = Duration
				.between(startDateTime, lastDateTime.plus(period.minus(intervalDuration)))
				.toNanos()
			/ period.toNanos();
		return periodicalIntervals(startDateTime, intervalDuration, period).limit(intervalCount);
	}

	private static class PeriodicalIntervalsGenerator implements Supplier<DateTimeInterval> {
		private final LocalDateTime startDateTime;
		private final Duration intervalDuration;
		private final Duration period;

		private DateTimeInterval currentInterval;

		private PeriodicalIntervalsGenerator(
				LocalDateTime startDateTime,
				Duration intervalDuration,
				Duration period) {
			this.startDateTime = startDateTime;
			this.intervalDuration = intervalDuration;
			this.period = period;
		}

		@Override
		public DateTimeInterval get() {
			if (currentInterval != null) {
				var newStartDateTime = currentInterval.startDateTime.plus(period);
				currentInterval = new DateTimeInterval(
					newStartDateTime,
					newStartDateTime.plus(intervalDuration));
			}
			else
				currentInterval = new DateTimeInterval(startDateTime, startDateTime.plus(intervalDuration));
			return currentInterval;
		}
	}
}
