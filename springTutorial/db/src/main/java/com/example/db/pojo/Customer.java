package com.example.db.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    long id;
    String firstName;
    String lastName;
    int createNum = 0;
}
