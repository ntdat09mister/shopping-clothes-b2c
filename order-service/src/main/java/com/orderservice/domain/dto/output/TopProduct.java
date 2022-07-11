package com.orderservice.domain.dto.output;
import com.orderservice.domain.model.Report.ReportTopProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopProduct {
    private ReportTopProduct reportTopProduct;
    private String name;
    private String imageUrl;
    private Long price;
}
