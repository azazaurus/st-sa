package ru.itislabs.datetime;

import java.time.format.*;

public final class DateTimeFormats {
	public static final DateTimeFormatter timestampFormat = new DateTimeFormatterBuilder()
		.append(DateTimeFormatter.ISO_LOCAL_DATE)
		.appendLiteral(' ')
		.append(DateTimeFormatter.ISO_LOCAL_TIME)
		.toFormatter();

	private DateTimeFormats() {
	}
}
