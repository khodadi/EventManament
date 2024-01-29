package com.env.service.dto;

import com.env.dao.entity.Event;
import com.utility.GeneralUtility;
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
        this(ent.getEventId(),
                GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.event"+"."+ent.getEventName()).toLowerCase()):ent.getEventNameFa(),
                ent.getEventName(),
                new ArrayList<>());
    }

    public ArrayList<EventDto> getChildren() {
        if(children == null){
            children = new ArrayList<>();
        }
        return children;
    }
}
