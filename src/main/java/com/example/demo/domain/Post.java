package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    private String id;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    private Long create_time;

    @PrePersist
    public void prePersist(){
        this.create_time = System.currentTimeMillis();
    }



}
