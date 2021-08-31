package com.ThePirates.Backend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Info {
    private String name;
    private String description;
    private String price;
}
