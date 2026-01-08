package com.workintech.FSWEB_s19_Challenge.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="like",schema = "tweet")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //User ve Tweet tablosu ile bagladik
    @ManyToOne
    private User user;

    @ManyToOne
    private Tweet tweet;
}
