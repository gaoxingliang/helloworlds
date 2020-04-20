package com.example.init.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
/**
 * example resp:
 * {
 *   "type": "success",
 *   "value": {
 *     "id": 7,
 *     "quote": "The real benefit of Boot, however, is that it's just Spring. That means any direction the code takes, regardless of
 *     complexity, I know it's a safe bet."
 *   }
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    String type; // success or not
    Value value;
}
