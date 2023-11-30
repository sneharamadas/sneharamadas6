package com.Bhalerao.ScrumPlay.model;

import jakarta.persistence.*;


import lombok.*;

import java.util.List;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sprintid;
    private int teamSize;
    private int sprintLength;
    private float scrumCallLength;
    private LocalDate startDate;
    private LocalDate endDate;

    @Getter
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<UserStory> userStories;

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }
}
