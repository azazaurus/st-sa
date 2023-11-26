package ru.itislabs.datetime;

import java.time.*;

public final class DateTimeInterval {
	/**
	 * Inclusive
	 */
	public final LocalDateTime startDateTime;

	/**
	 * Exclusive
	 */
	public final LocalDateTime endDateTime;

	public DateTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public boolean includes(LocalDateTime dateTime) {
		return startDateTime.compareTo(dateTime) >= 0 && endDateTime.compareTo(dateTime) < 0
			|| startDateTime.compareTo(dateTime) <= 0 && endDateTime.compareTo(dateTime) > 0;
	}

	public boolean notIncludes(LocalDateTime dateTime) {
		return !includes(dateTime);
	}
}
