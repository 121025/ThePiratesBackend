package com.ThePirates.Backend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import java.util.List;

@Builder
@Getter
@Setter
public class DetailedInfo {
    private String name;
    private String description;
    private String delivery;
    @Convert(converter = DetailedInfoOptionsConverter.class)
    private List<DetailedInfoOptions> options;
}
