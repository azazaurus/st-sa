package ru.itislabs.aggregators;

import ru.itislabs.datetime.*;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class TimestampPerDaySeriesAggregator extends BaseTimestampSeriesAggregator {
	private static final String defaultDisplayName = "Per day";

	public TimestampPerDaySeriesAggregator() {
		super(defaultDisplayName);
	}

	public TimestampPerDaySeriesAggregator(DateTimeInterval acceptedDateTimeInterval) {
		super(formatDisplayName(acceptedDateTimeInterval), new DateTimeInterval[] { acceptedDateTimeInterval });
	}

	public TimestampPerDaySeriesAggregator(String displayName, DateTimeInterval... acceptedDateTimeIntervals) {
		super(displayName, acceptedDateTimeIntervals);
	}

	@Override
	public Duration getAggregationPeriod() {
		return ChronoUnit.DAYS.getDuration();
	}

	private static String formatDisplayName(DateTimeInterval interval) {
		return defaultDisplayName
			+ " from "
			+ interval.startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
			+ " to "
			+ interval.endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
			+ " exclusive";
	}
}
