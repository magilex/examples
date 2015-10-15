package com.magilex.examples.java8;

import static java.lang.System.out;
import static java.time.Month.JULY;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.TemporalAdjusters.*;

public class JavaTime {
	public static void main(String[] args) {
		out.println("------LOCAL DATE TIME:");
		LocalDateTime lDateTime = LocalDateTime.now();
		out.printf("LocalDateTime now(): %s \n", LocalDateTime.now());
		out.printf("LocalDateTime of(y,M,d,h,m) %s \n", lDateTime);
		out.printf("LocalDateTime of(y,M,d,h,m,s,nano): %s \n", LocalDateTime.of(1983, JULY, 9, 01, 33, 8, 1));
		out.printf("LocalDateTime parse(str): %s \n", LocalDateTime.parse("1983-07-09T01:33:08"));
		out.printf("LocalDateTime with(adjuster.firstDayOfNextMonth): %s \n", lDateTime.with(firstDayOfNextMonth()));
		out.printf("LocalDateTime with(adjuster.custom.15ofMonth): %s \n", 
				lDateTime.with((temporal) -> temporal.with(DAY_OF_MONTH, 15).plus(1, MONTHS)));

        out.println("------ZONED DATE TIME: Base local date time: " + lDateTime);
        ZoneId id = ZoneId.of("Europe/Kiev");
        out.printf("ZonedDateTime of(LocalDateTime, zoneId): %s \n", ZonedDateTime.of(lDateTime, id));
        out.printf("zonedDateTime toOffsetDateTime(LocalDateTime, zoneId): %s \n", ZonedDateTime.of(lDateTime, id).toOffsetDateTime());
        out.printf("ZonedDateTime now(zoneId): %s \n", ZonedDateTime.now(id));

		out.println("------LOCAL DATE:");
		out.printf("LocalDate parse(str): %s \n", LocalDate.parse("1983-364", DateTimeFormatter.ISO_ORDINAL_DATE));
		out.printf("LocalDate ofEpochDay(day): %s \n", LocalDate.ofEpochDay(0));

        out.println("------OFFSET DATE TIME:");
        out.println("From java docs: 'This is useful for serializing data into a database and also should be used as " +
                "the serialization format for logging time stamps if you have servers in different time zones.'");
        out.printf("OffsetDateTime now(): %s \n", OffsetDateTime.now());
        //out.printf("LocalDate ofEpochDay(day): %s \n", LocalDate.ofEpochDay(0));

		out.println("------LOCAL TIME:");
		LocalTime lTime = LocalTime.of(14, 28, 59);
		out.printf("LocalTime of(y,M,d,h,m): %s \n", lTime);
		out.printf("LocalTime getHour(): %d \n", lTime.getHour());
		out.printf("LocalTime getLong(HOUR_OF_AMPM): %d \n", lTime.getLong(HOUR_OF_AMPM));
		out.printf("LocalTime withSecond(s): %s \n", lTime.withSecond(10));
		out.printf("LocalTime plusHours(h): %s \n", lTime.plusHours(23));
		out.printf("LocalTime truncatedTo(chronoUnit): %s \n", lTime.truncatedTo(HOURS));

        out.println("------PERIODS:");
        out.printf("Period of(y,m,d) 9 months: %s \n", Period.of(0, 9, 0));
        out.printf("Period of(y,m,d) -9 months: %s \n", Period.of(0, -9, 0));
        out.printf("LocalDate.now().plus(period) period=9 month: %s \n", LocalDate.now().plus(Period.of(0, 1, 0)));

        out.println("------DURATIONS:");
        out.printf("Duration of(minutes): %s \n", Duration.ofMinutes(185));
        out.printf("Duration between(minutes) now and now+5hours: %s \n", Duration.between(LocalDateTime.now(), LocalDateTime.now().minus(5, HOURS)));
	}
}
