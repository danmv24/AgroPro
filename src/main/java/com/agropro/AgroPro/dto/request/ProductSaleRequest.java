package com.agropro.AgroPro.dto.request;

import com.agropro.AgroPro.enums.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleRequest {

    @NotNull(message = "Продукт должен быть указан")
    private Product product;

    @NotNull(message = "Цена должна быть указана")
    private BigDecimal price;

    @NotNull(message = "Количество продукта должно быть указано")
    private BigDecimal quantity;

    @NotNull(message = "Дата продажи должна быть указана")
    @PastOrPresent
    private LocalDate saleDate;

}
