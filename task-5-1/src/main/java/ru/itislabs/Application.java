package ru.itislabs;

import ru.itislabs.aggregators.*;
import ru.itislabs.datetime.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;
import java.util.zip.*;

public class Application {
	private static final LocalDateTime minDateTime = LocalDateTime.ofInstant(
		Instant.ofEpochSecond(-31557014135596800L, 0),
		ZoneId.of("+00"));

	private static final String timestampsFileName = "order.zip";
	private static final Duration requiredAggregationPeriod = Duration.ofHours(1);
	private static final DateTimeInterval baseDateTimeInterval = new DateTimeInterval(
		LocalDateTime.of(2023, Month.APRIL, 1, 0, 0),
		LocalDateTime.of(2023, Month.MAY, 1, 0, 0));
	private static final Duration progressReportFrequency = Duration.ofSeconds(5);

	public static void main(String[] args) throws IOException {
		var timestampAggregators = initializeTimestampAggregators();

		long lineNumber;
		try (var timestampReader = openTimestampsFile(timestampsFileName)) {
			var lastProgressReportTime = System.nanoTime();
			var previousTimestamp = minDateTime;
			for (lineNumber = 1; true; ++lineNumber) {
				var timestampLine = timestampReader.readLine();
				if (timestampLine == null) // EOF
					break;

				var timestampParseResult = tryParseTimestamp(timestampLine, lineNumber);
				if (timestampParseResult.isEmpty())
					continue;

				var timestamp = timestampParseResult.get();
				if (timestamp.isBefore(previousTimestamp)) {
					System.err.println(
						formatNotOrderedTimestampErrorMessage(previousTimestamp, timestamp, lineNumber));
					continue;
				}
				previousTimestamp = timestamp;

				for (var aggregator : timestampAggregators)
					aggregator.addTimestamp(timestamp);

				if (System.nanoTime() - lastProgressReportTime > progressReportFrequency.toNanos()) {
					System.out.println(formatTimestampProcessingReport(lineNumber));
					lastProgressReportTime = System.nanoTime();
				}
			}
		}

		System.out.println(formatTimestampProcessingReport(lineNumber - 1));

		for (var aggregator : timestampAggregators) {
			System.out.println();
			System.out.println(aggregator.getDisplayName());
			System.out.println("Sample average: " + aggregator.getSampleAverage(requiredAggregationPeriod));
			System.out.println("Sample dispersion: " + aggregator.getSampleDispersion(requiredAggregationPeriod));
		}
	}

	private static List<TimestampSeriesAggregator> initializeTimestampAggregators() {
		var aggregators = new ArrayList<TimestampSeriesAggregator>();

		var perHourAggregatorIntervals = DateTimeIntervalGenerator
			.sequentialIntervals(
				baseDateTimeInterval.startDateTime,
				Duration.ofDays(3),
				baseDateTimeInterval.endDateTime)
			.collect(Collectors.toList());
		for (var interval : perHourAggregatorIntervals)
			aggregators.add(new TimestampPerHourSeriesAggregator(interval));

		aggregators.add(new TimestampPerDaySeriesAggregator(baseDateTimeInterval));
		aggregators.add(new TimestampPerMonthSeriesAggregator());

		var withMonthBeforeAndMonthAfterInterval = new DateTimeInterval(
			baseDateTimeInterval.startDateTime.minusMonths(1),
			baseDateTimeInterval.endDateTime.plusMonths(1));
		aggregators.add(new TimestampPerWeekSeriesAggregator(withMonthBeforeAndMonthAfterInterval));

		var firstMondayDateTime = withMonthBeforeAndMonthAfterInterval.startDateTime.plusDays(
			(ChronoUnit.WEEKS.getDuration().toDays()
					- withMonthBeforeAndMonthAfterInterval.startDateTime.getDayOfWeek().getValue()
					+ DayOfWeek.MONDAY.getValue())
				% ChronoUnit.WEEKS.getDuration().toDays());
		var perMondayAggregatorIntervals = DateTimeIntervalGenerator
			.periodicalIntervals(
				firstMondayDateTime,
				ChronoUnit.DAYS.getDuration(),
				ChronoUnit.WEEKS.getDuration(),
				withMonthBeforeAndMonthAfterInterval.endDateTime)
			.toArray(DateTimeInterval[]::new);
		aggregators.add(
			new TimestampPerDaySeriesAggregator(
				"Per Mondays from "
					+ withMonthBeforeAndMonthAfterInterval.startDateTime.format(DateTimeFormats.monthFormat)
					+ " to "
					+ withMonthBeforeAndMonthAfterInterval.endDateTime.format(DateTimeFormats.monthFormat)
					+ " exclusive",
				perMondayAggregatorIntervals));

		var firstSundayDateTime = withMonthBeforeAndMonthAfterInterval.startDateTime.plusDays(
			(ChronoUnit.WEEKS.getDuration().toDays()
					- withMonthBeforeAndMonthAfterInterval.startDateTime.getDayOfWeek().getValue()
					+ DayOfWeek.SUNDAY.getValue())
				% ChronoUnit.WEEKS.getDuration().toDays());
		var perSundayAggregatorIntervals = DateTimeIntervalGenerator
			.periodicalIntervals(
				firstSundayDateTime,
				ChronoUnit.DAYS.getDuration(),
				ChronoUnit.WEEKS.getDuration(),
				withMonthBeforeAndMonthAfterInterval.endDateTime)
			.toArray(DateTimeInterval[]::new);
		aggregators.add(
			new TimestampPerDaySeriesAggregator(
				"Per Sundays from "
					+ withMonthBeforeAndMonthAfterInterval.startDateTime.format(DateTimeFormats.monthFormat)
					+ " to "
					+ withMonthBeforeAndMonthAfterInterval.endDateTime.format(DateTimeFormats.monthFormat)
					+ " exclusive",
				perSundayAggregatorIntervals));

		return aggregators;
	}

	private static BufferedReader openTimestampsFile(String timestampsFileName) throws IOException {
		var zipStream = new ZipInputStream(
			Files.newInputStream(Path.of(timestampsFileName), StandardOpenOption.READ));
		zipStream.getNextEntry();
		return new BufferedReader(new InputStreamReader(zipStream));
	}

	private static Optional<LocalDateTime> tryParseTimestamp(String timestampString, long lineNumber) {
		try {
			return Optional.of(LocalDateTime.parse(timestampString, DateTimeFormats.timestampFormat));
		}
		catch (DateTimeParseException e) {
			System.err.println(
				"Invalid timestamp \"" + timestampString + "\" on line " + lineNumber + ", skipping it");
			return Optional.empty();
		}
	}

	private static String formatTimestampProcessingReport(long processedTimestampsCount) {
		return "Processed " + processedTimestampsCount + " lines";
	}

	private static String formatNotOrderedTimestampErrorMessage(
			LocalDateTime previousTimestamp,
			LocalDateTime timestamp,
			long lineNumber) {
		return "Timestamp "
			+ timestamp.format(DateTimeFormats.timestampFormat)
			+ " on line "
			+ lineNumber
			+ " is before the previous "
			+ previousTimestamp.format(DateTimeFormats.timestampFormat)
			+ ", skipping it";
	}
}
