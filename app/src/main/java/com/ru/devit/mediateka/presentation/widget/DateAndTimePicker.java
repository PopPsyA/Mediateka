package com.ru.devit.mediateka.presentation.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import com.ru.devit.mediateka.models.model.DateAndTimeInfo;

import java.util.Calendar;

public class DateAndTimePicker {

    private final Context context;
    private final Calendar calendar = Calendar.getInstance();
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateAndTimePicker(Context context) {
        this.context = context;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void showDateAndTimePickerDialog(DateAndTimePickerListener listener){
        DateAndTimeInfo dateAndTimeInfo = new DateAndTimeInfo();
        showDatePickerDialog((year, month, dayOfMonth) -> {
            dateAndTimeInfo.setYear(year);
            dateAndTimeInfo.setMonth(month);
            dateAndTimeInfo.setDay(dayOfMonth);
        }, () -> showTimePickerDialog((hour, minute) -> {
            dateAndTimeInfo.setHour(hour);
            dateAndTimeInfo.setMinute(minute);
            listener.onDateAndTimeSet(dateAndTimeInfo);
        }));
    }

    private void showDatePickerDialog(DataPickerListener listener , Then then){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            listener.onDateSet(year , month , dayOfMonth);
            then.then();
        }, year , month , day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(TimePickerListener listener){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            listener.onTimeSet(hourOfDay , minute);
        }, hour , minute , true);
        timePickerDialog.show();
    }

    @FunctionalInterface
    interface Then{
        void then();
    }

    public interface DateAndTimePickerListener{
        void onDateAndTimeSet(DateAndTimeInfo dateAndTimeInfo);
    }

    interface DataPickerListener {
        void onDateSet(int year , int month , int dayOfMonth);
    }

    interface TimePickerListener{
        void onTimeSet(int hour , int minute);
    }
}
