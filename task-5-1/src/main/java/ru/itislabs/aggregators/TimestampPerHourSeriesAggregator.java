package ru.itislabs.aggregators;

import ru.itislabs.datetime.*;

import java.time.*;
import java.time.temporal.*;

public class TimestampPerHourSeriesAggregator extends BaseTimestampSeriesAggregator {
	private static final String defaultDisplayName = "Per hour";

	public TimestampPerHourSeriesAggregator() {
		super(defaultDisplayName);
	}

	public TimestampPerHourSeriesAggregator(DateTimeInterval acceptedDateTimeInterval) {
		super(formatDisplayName(acceptedDateTimeInterval), new DateTimeInterval[] { acceptedDateTimeInterval });
	}

	public TimestampPerHourSeriesAggregator(String displayName, DateTimeInterval... acceptedDateTimeIntervals) {
		super(displayName, acceptedDateTimeIntervals);
	}

	@Override
	public Duration getAggregationPeriod() {
		return ChronoUnit.HOURS.getDuration();
	}

	private static String formatDisplayName(DateTimeInterval interval) {
		return defaultDisplayName
			+ " from "
			+ interval.startDateTime.format(DateTimeFormats.minuteFormat)
			+ " to "
			+ interval.endDateTime.format(DateTimeFormats.minuteFormat)
			+ " exclusive";
	}
}
