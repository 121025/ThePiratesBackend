package com.ThePirates.Backend;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "options_t")
public class OptionsTable {
    @Id
    private String name;
    private int price;
    private int stock;
    private long merch_id;
}
