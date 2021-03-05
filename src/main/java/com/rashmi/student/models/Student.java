package com.rashmi.student.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private static final String ERROR_NAME = "Name should be between 2 and 50 characters.";
    private static final String ERROR_SUBJECT = "Subject should be between 2 and 5 characters.";
    private static final String ERROR_UNIVERSITY = "University should be between 2 and 10 characters.";

    @Pattern(message = ERROR_NAME,regexp = "[a-zA-Z .]{2,50}")
    @NotEmpty(message = ERROR_NAME)
    private String name;

    @Pattern(message = ERROR_SUBJECT,regexp = "[a-zA-Z .]{2,5}")
    @NotEmpty(message = ERROR_SUBJECT)
    private String subject;

    @Pattern(message = ERROR_UNIVERSITY,regexp = "[a-zA-Z .]{2,10}")
    @NotEmpty(message = ERROR_UNIVERSITY)
    private String university;
}

