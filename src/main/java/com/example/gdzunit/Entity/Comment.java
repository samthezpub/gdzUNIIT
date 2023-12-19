package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    private Answer answer;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;



    public String getPublishDateFormatted() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime postTime = publishDate;

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
