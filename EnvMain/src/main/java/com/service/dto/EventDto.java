package com.service.dto;

import com.dao.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long eventId;
    private String eventNameFa;
    private String eventName;
    private ArrayList<EventDto> children;

    public EventDto(Long eventId) {
        this.eventId = eventId;
    }

    public EventDto(Event ent){
        this(ent.getEventId(),ent.getEventNameFa(),ent.getEventName(),new ArrayList<>());
    }

    public ArrayList<EventDto> getChildren() {
        if(children == null){
            children = new ArrayList<>();
        }
        return children;
    }
}
