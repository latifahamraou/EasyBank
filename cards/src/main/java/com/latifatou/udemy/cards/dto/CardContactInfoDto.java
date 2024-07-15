package com.latifatou.udemy.cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
@ConfigurationProperties(prefix = "cards")
@Getter @Setter
public class CardContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
