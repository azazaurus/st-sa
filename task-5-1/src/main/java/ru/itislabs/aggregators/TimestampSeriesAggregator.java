package ru.itislabs.aggregators;

import java.time.*;

public interface TimestampSeriesAggregator {
	Duration getAggregationPeriod();

	double getSampleAverage();

	double getSampleDispersion();

	double getSampleAverage(Duration aggregationPeriod);

	double getSampleDispersion(Duration aggregationPeriod);

	String getDisplayName();

	void addTimestamp(LocalDateTime timestamp);
}
