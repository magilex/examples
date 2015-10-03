package com.magilex.examples.java8;

import static java.lang.System.out;
import static java.time.Month.JULY;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.TemporalAdjusters.*;

public class TimeExample {
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
		
		out.println("------LOCAL DATE:");
		out.printf("LocalDate parse(str): %s \n", LocalDate.parse("1983-364", DateTimeFormatter.ISO_ORDINAL_DATE));
		out.printf("LocalDate ofEpochDay(day): %s \n", LocalDate.ofEpochDay(0));
		
		out.println("------LOCAL TIME:");
		LocalTime lTime = LocalTime.of(14, 28, 59);
		out.printf("LocalTime of(y,M,d,h,m): %s \n", lTime);
		out.printf("LocalTime getHour(): %d \n", lTime.getHour());
		out.printf("LocalTime getLong(HOUR_OF_AMPM): %d \n", lTime.getLong(HOUR_OF_AMPM));
		out.printf("LocalTime withSecond(s): %s \n", lTime.withSecond(10));
		out.printf("LocalTime plusHours(h): %s \n", lTime.plusHours(23));
		out.printf("LocalTime truncatedTo(chronoUnit): %s \n", lTime.truncatedTo(HOURS));
	}
}
