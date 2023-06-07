package com.notification.entity;

import com.infra.entity.ABaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

/**
 * @author m.keyvanlou
 * @created 25/10/2022 - 9:25 AM
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ATTACH_FILES" , schema = "NOTIF_USER")
public class AttachFile extends ABaseEntity {

    @Id
    @Column(name = "ATTACH_ID", nullable = false, insertable = true, updatable = false, precision = 0)
    @GeneratedValue(generator = "ATTACH_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ATTACH_SEQ", allocationSize = 1, sequenceName = "ATTACH_SEQ",schema = "NOTIF_USER")
    private Long attachId;

    @Basic
    @Column(name = "ATTACH_NAME", nullable = false, insertable = true, updatable = true, length = 100)
    private String attachName;

    @Lob
    @Column(name = "ATTACH_FILE", nullable = false, insertable = true, updatable = true)
    private Blob attachFile;

    @Basic
    @Column(name = "REQUEST_MASTER_ID", nullable = false, insertable = true, updatable = false, precision = 0)
    private Long requestMasterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_MASTER_ID", referencedColumnName = "REQUEST_MASTER_ID", nullable = true, insertable = false, updatable = false,foreignKey=@ForeignKey(name = "FK_From_RequestMaster_To_AttachFile"))
    private RequestMaster RequestMasterByRequestMasterID;


}
