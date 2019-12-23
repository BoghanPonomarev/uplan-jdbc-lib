package com.uplan.jdbc.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class SqlTimeUtils {

  private SqlTimeUtils() {
    throw new UnsupportedOperationException();
  }

  public static Timestamp nullableTimestamp(LocalDateTime localDateTime) {
    if (localDateTime != null) {
      return Timestamp.valueOf(localDateTime);
    }
    return null;
  }

  public static Date nullableDate(LocalDate localDate) {
    if (localDate != null) {
      return Date.valueOf(localDate);
    }
    return null;
  }

  public static Timestamp currentTimestamp() {
    return nullableTimestamp(LocalDateTime.now());
  }

  public static Date currentDate() {
    return Date.valueOf(LocalDate.now());
  }

}
