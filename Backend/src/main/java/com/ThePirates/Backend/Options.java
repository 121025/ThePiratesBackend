package com.ThePirates.Backend;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Options {
    private String name;
    private int price;
    private int stock;
}
