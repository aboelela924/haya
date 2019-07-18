package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.room.TypeConverter;

import java.util.Date;

public class TimeConverters {
    @TypeConverter
    public static Date longToDate(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }
}
