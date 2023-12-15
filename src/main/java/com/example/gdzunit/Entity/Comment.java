package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@Table(name = "comments")
@Entity
public class Comment {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "author")
    @ManyToOne
    private User author;

    @JoinColumn(name = "answer")
    @ManyToOne
    private Answer answer;

    @Column(name = "content")
    private String content;

    @Column(name = "publishTime")
    private LocalDateTime publishTime;

    @Transient
    private String publishTimeFormatted;



    public String getPublishTimeFormatted() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postTime = publishTime;

        long secondsDiff = ChronoUnit.SECONDS.between(postTime, now);
        long minutesDiff = ChronoUnit.MINUTES.between(postTime, now);
        long hoursDiff = ChronoUnit.HOURS.between(postTime, now);

        String formattedTime;
        if (secondsDiff < 5) {
            formattedTime = "только что";
        } else if (secondsDiff < 60) {
            formattedTime = String.format("%d секунд назад", secondsDiff);
        } else if (minutesDiff < 60) {
            formattedTime = String.format("%d минут назад", minutesDiff);
        } else if (hoursDiff < 24) {
            formattedTime = String.format("%d часов назад", hoursDiff);
        } else {
            formattedTime = postTime.format(DateTimeFormatter.ofPattern("HH:mm, dd MMMM, yyyy"));
        }

        return formattedTime;
    }
}
