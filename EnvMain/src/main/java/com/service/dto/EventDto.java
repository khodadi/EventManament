package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long eventId;
    private String eventNameFa;
    private String eventName;

    public EventDto(Long eventId) {
        this.eventId = eventId;
    }
}
