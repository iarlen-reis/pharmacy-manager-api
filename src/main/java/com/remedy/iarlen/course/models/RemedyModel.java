package com.remedy.iarlen.course.models;

import java.time.LocalDate;

import com.remedy.iarlen.course.Remedy.Laboratory;
import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.Remedy.Via;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "Remedy")
@Entity(name = "remedys")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RemedyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Via via;

    private String batch;

    private int amount;

    private LocalDate validity;

    @Enumerated(EnumType.STRING)
    private Laboratory laboratory;
    
    private boolean active;

    public RemedyModel(RemedyDTO data) {
        this.name = data.name();
        this.amount = data.amount();
        this.via = data.via();
        this.laboratory = data.laboratory();
        this.validity = data.validity();
        this.batch = data.batch();
        this.active = true;
    }

    public void updateData(RemedyDTO data) {
        this.name = data.name();
        this.amount = data.amount();
        this.via = data.via();
        this.laboratory = data.laboratory();
        this.validity = data.validity();
        this.batch = data.batch();
    }

    public void deactivate() {
        this.active = false;
    }

    public void reactivate() {
        this.active = true;
    }
}
