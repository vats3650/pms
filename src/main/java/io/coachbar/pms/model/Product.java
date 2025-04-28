package io.coachbar.pms.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private BigDecimal price;
    @NonNull
    private int quantity;
}
