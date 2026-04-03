package com.viniciusstorch.bookwhere_api.library.dto.request;

import java.time.LocalTime;

import com.viniciusstorch.bookwhere_api.library.model.WeekDay;

import jakarta.validation.constraints.NotNull;

public record LibraryHourRequestDTO(
    
    @NotNull(message = "Week day is required")
    WeekDay weekDay,

    @NotNull(message = "Opening time is required")
    LocalTime openingTime,

    @NotNull(message = "Closing time is required")
    LocalTime closingTime
) {
}
