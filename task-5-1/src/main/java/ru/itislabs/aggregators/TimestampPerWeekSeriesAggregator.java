package ru.itislabs.aggregators;

import ru.itislabs.datetime.*;

import java.time.*;
import java.time.temporal.*;

public class TimestampPerWeekSeriesAggregator extends BaseTimestampSeriesAggregator {
	private static final String defaultDisplayName = "Per week";

	public TimestampPerWeekSeriesAggregator() {
		super(defaultDisplayName);
	}

	public TimestampPerWeekSeriesAggregator(DateTimeInterval acceptedDateTimeInterval) {
		super(formatDisplayName(acceptedDateTimeInterval), new DateTimeInterval[] { acceptedDateTimeInterval });
	}

	public TimestampPerWeekSeriesAggregator(String displayName, DateTimeInterval... acceptedDateTimeIntervals) {
		super(displayName, acceptedDateTimeIntervals);
	}

	@Override
	public Duration getAggregationPeriod() {
		return ChronoUnit.WEEKS.getDuration();
	}

	@Override
	protected DateTimeInterval getPeriod(LocalDateTime timestamp) {
		var periodStartDate = timestamp
			.truncatedTo(ChronoUnit.DAYS)
			.minusDays(timestamp.getDayOfWeek().getValue() - 1);
		return new DateTimeInterval(periodStartDate, periodStartDate.plusDays(ChronoUnit.WEEKS.getDuration().toDays()));
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
