package it.teems.codingtask.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageProperties {
    int page = 0;
    int size = 20;
}
