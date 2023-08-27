package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Event",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends ABaseEntity{
    @Id
    @Column(name = "EVENT_ID")
    @GeneratedValue(generator = "SEQ_EVENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_EVENT", allocationSize = 1, sequenceName = "SEQ_EVENT",schema = "ENV_DATA")
    private Long eventId;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @Column(name = "EVENT_NAME_FA")
    private String eventNameFa;
    @Column(name = "PARENT_EVENT_ID")
    private Long parentEventId;
}
