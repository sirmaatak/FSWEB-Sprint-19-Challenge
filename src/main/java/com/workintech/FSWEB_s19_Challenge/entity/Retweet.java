package com.workintech.FSWEB_s19_Challenge.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "retweet",schema = "tweet")
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private LocalDate createDate;

    //User ve Tweet tablosu ile bagladik
    @ManyToOne
    private User user;

    @ManyToOne
    private Tweet tweet;
}
