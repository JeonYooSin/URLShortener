package com.urlshortener.shortener.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "inputURL")
public class InputURLEntity {
    @Transient
    private int result;
    @Transient
    private String msg;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputURL;
    private String shortKey;
    private int callCount;
    private LocalDateTime createDate;
    public InputURLEntity() {
        createDate = LocalDateTime.now();
    }
}