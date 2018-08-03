package com.ru.devit.mediateka.domain;

import com.ru.devit.mediateka.models.model.DateAndTimeInfo;

public interface SystemTimeCalculator {
    long futureTimeInMillisFromDateAndTimeInfo(DateAndTimeInfo dateAndTimeInfo);
    long currentTimeInMillis();
}
