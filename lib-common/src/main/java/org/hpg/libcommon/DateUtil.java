/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Util functions for date time
 *
 * @author wws2003
 */
public class DateUtil {

    /**
     * Convert date to given timestamp string
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateTime2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * Convert date to given timestamp string
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateTime2String(LocalDateTime date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return date.format(dateTimeFormatter);
    }

    // TODO Add methods
}
