package com.ThePirates.Backend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import java.util.List;

@Builder
@Getter
@Setter
public class Request {
    private long id;
    private String name;
    private String description;
    @Convert(converter = DeliveryConverter.class)
    private Delivery delivery;
    @Convert(converter = OptionsConverter.class)
    private List<Options> options;
}