package ru.itislabs.datetime;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

public final class DurationFormatter {
	private static final Set<ChronoUnit> excludedTimeUnits = Set.of(
		ChronoUnit.HALF_DAYS,
		ChronoUnit.WEEKS,
		ChronoUnit.DECADES,
		ChronoUnit.CENTURIES,
		ChronoUnit.MILLENNIA,
		ChronoUnit.ERAS,
		ChronoUnit.FOREVER);
	private static final List<ChronoUnit> timeUnits;

	static {
		List<ChronoUnit> reversedTimeUnits = Arrays.asList(ChronoUnit.values());
		Collections.reverse(reversedTimeUnits);

		timeUnits = reversedTimeUnits.stream()
			.filter(x -> !excludedTimeUnits.contains(x))
			.collect(Collectors.toList());
	}

	private DurationFormatter() {
	}

	public static String format(Duration duration) {
		double durationInNanoseconds = duration.toNanos();
		for (ChronoUnit timeUnit : timeUnits) {
			double durationInTimeUnit = durationInNanoseconds / timeUnit.getDuration().toNanos();
			if (durationInTimeUnit >= 1)
				return durationInTimeUnit + " " + timeUnit.toString().toLowerCase(Locale.ROOT);
		}

		return durationInNanoseconds + " nanos";
	}
}
