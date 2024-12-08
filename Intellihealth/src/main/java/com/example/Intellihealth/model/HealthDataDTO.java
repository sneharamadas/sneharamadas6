package com.example.Intellihealth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataDTO {
    private @Setter String age;
    private @Getter @Setter String height;
    private @Getter @Setter String weight;
    private @Getter @Setter String gender;
    private @Getter @Setter String alcohol;
    private @Getter @Setter String smoke;
    private @Getter @Setter String cholesterol;
    private @Getter @Setter String hypertension;
    private @Getter @Setter String copd;
    private @Getter @Setter String diabetes;
    private @Getter @Setter String muscularProblems;
    private @Getter @Setter String obesity;
    private @Getter @Setter String pneumonia;
    private @Getter @Setter String asthma;
    private @Getter @Setter String bloodPressure;
}

