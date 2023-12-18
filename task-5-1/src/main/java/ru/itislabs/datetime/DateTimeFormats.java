package ru.itislabs.datetime;

import java.time.format.*;

public final class DateTimeFormats {
	public static final DateTimeFormatter minuteFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM");

	public static final DateTimeFormatter timestampFormat = new DateTimeFormatterBuilder()
		.append(DateTimeFormatter.ISO_LOCAL_DATE)
		.appendLiteral(' ')
		.append(DateTimeFormatter.ISO_LOCAL_TIME)
		.toFormatter();

	private DateTimeFormats() {
	}
}
