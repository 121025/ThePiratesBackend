package com.ThePirates.Backend;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DetailedInfoOptions {
    private String name;
    private int price;
}
