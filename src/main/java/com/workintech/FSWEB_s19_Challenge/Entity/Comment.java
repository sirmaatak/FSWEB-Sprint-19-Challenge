package com.workintech.FSWEB_s19_Challenge.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="comment",schema = "tweet")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    //User ve Tweet tablosu ile bagladik
    @ManyToOne
    private User user;

    @ManyToOne
    private Tweet tweet;
}
