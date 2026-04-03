package com.viniciusstorch.bookwhere_api.library.dto.response;

import java.time.LocalTime;

import com.viniciusstorch.bookwhere_api.library.model.WeekDay;

public record LibraryHourResponseDTO(
    Long id,
    WeekDay weekDay,
    LocalTime openingTime,
    LocalTime closingTime
) {
}
