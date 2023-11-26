package ru.itislabs.aggregators;

import ru.itislabs.datetime.*;

import java.time.*;

public abstract class BaseTimestampSeriesAggregator implements TimestampSeriesAggregator {
	private static final DateTimeInterval[] emptyDateTimeIntervals = new DateTimeInterval[0];

	protected final String displayName;
	protected final DateTimeInterval[] acceptedDateTimeIntervals;

	protected long currentPeriodCounter;
	protected DateTimeInterval currentPeriod;

	protected long timestampCountsSum;
	protected long timestampCountSquaresSum;
	protected long periodsCount = 1;

	protected BaseTimestampSeriesAggregator(String displayName) {
		this.displayName = displayName;
		this.acceptedDateTimeIntervals = emptyDateTimeIntervals;
	}

	protected BaseTimestampSeriesAggregator(String displayName, DateTimeInterval[] acceptedDateTimeIntervals) {
		this.displayName = displayName;
		this.acceptedDateTimeIntervals = acceptedDateTimeIntervals;
	}

	@Override
	public double getSampleAverage() {
		return (double)timestampCountsSum / periodsCount;
	}

	@Override
	public double getSampleDispersion() {
		var correctedPeriodsCount = periodsCount - 1;
		return (double)timestampCountSquaresSum / correctedPeriodsCount
			- (double)(timestampCountsSum * timestampCountsSum) / (correctedPeriodsCount * correctedPeriodsCount);
	}

	@Override
	public double getSampleAverage(Duration aggregationPeriod) {
		return getSampleAverage() / getPeriodConversionInverseCoefficient(aggregationPeriod);
	}

	@Override
	public double getSampleDispersion(Duration aggregationPeriod) {
		var periodConversionInverseCoefficient = getPeriodConversionInverseCoefficient(aggregationPeriod);
		return getSampleDispersion() / (periodConversionInverseCoefficient * periodConversionInverseCoefficient);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void addTimestamp(LocalDateTime timestamp) {
		if (acceptedDateTimeIntervals.length > 0 && isNotInAny(timestamp, acceptedDateTimeIntervals))
			return;

		if (currentPeriod == null) {
			currentPeriod = getPeriod(timestamp);
		}
		else if (timestamp.compareTo(currentPeriod.endDateTime) >= 0) {
			flushPeriod();
			currentPeriod = getPeriod(timestamp);
		}

		++currentPeriodCounter;
	}

	protected double getPeriodConversionInverseCoefficient(Duration requiredAggregationPeriod) {
		return (double)getAggregationPeriod().toSeconds() / requiredAggregationPeriod.toSeconds();
	}

	protected void flushPeriod() {
		timestampCountsSum += currentPeriodCounter;
		timestampCountSquaresSum += currentPeriodCounter * currentPeriodCounter;
		++periodsCount;

		currentPeriod = null;
		currentPeriodCounter = 0;
	}

	protected DateTimeInterval getPeriod(LocalDateTime timestamp) {
		var zoneOffset = ZoneOffset.UTC;
		var aggregationPeriodSeconds = getAggregationPeriod().toSeconds();
		var timestampEpochSeconds = timestamp.toEpochSecond(zoneOffset);
		var periodStartDateEpochSeconds = timestampEpochSeconds - timestampEpochSeconds % aggregationPeriodSeconds;
		return new DateTimeInterval(
			LocalDateTime.ofEpochSecond(
				periodStartDateEpochSeconds,
				0,
				zoneOffset),
			LocalDateTime.ofEpochSecond(
				periodStartDateEpochSeconds + aggregationPeriodSeconds,
				0,
				zoneOffset));
	}

	private static boolean isNotInAny(LocalDateTime timestamp, DateTimeInterval[] intervals) {
		for (var interval : intervals)
			if (interval.includes(timestamp))
				return false;

		return true;
	}
}
