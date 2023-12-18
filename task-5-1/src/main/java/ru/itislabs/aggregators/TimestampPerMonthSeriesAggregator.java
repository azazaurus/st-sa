package ru.itislabs.aggregators;

import ru.itislabs.datetime.*;

import java.time.*;
import java.time.temporal.*;

public class TimestampPerMonthSeriesAggregator extends BaseTimestampSeriesAggregator {
	private static final String defaultDisplayName = "Per month";

	public TimestampPerMonthSeriesAggregator() {
		super(defaultDisplayName);
	}

	public TimestampPerMonthSeriesAggregator(DateTimeInterval acceptedDateTimeInterval) {
		super(formatDisplayName(acceptedDateTimeInterval), new DateTimeInterval[] { acceptedDateTimeInterval });
	}

	public TimestampPerMonthSeriesAggregator(String displayName, DateTimeInterval... acceptedDateTimeIntervals) {
		super(displayName, acceptedDateTimeIntervals);
	}

	@Override
	public Duration getAggregationPeriod() {
		return ChronoUnit.MONTHS.getDuration();
	}

	@Override
	protected DateTimeInterval getPeriod(LocalDateTime timestamp) {
		LocalDateTime periodStartDate = LocalDateTime.of(timestamp.getYear(), timestamp.getMonth(), 1, 0, 0);
		return new DateTimeInterval(periodStartDate, periodStartDate.plusMonths(1));
	}

	private static String formatDisplayName(DateTimeInterval interval) {
		return defaultDisplayName
			+ " from "
			+ interval.startDateTime.format(DateTimeFormats.monthFormat)
			+ " to "
			+ interval.endDateTime.format(DateTimeFormats.monthFormat)
			+ " exclusive";
	}
}
