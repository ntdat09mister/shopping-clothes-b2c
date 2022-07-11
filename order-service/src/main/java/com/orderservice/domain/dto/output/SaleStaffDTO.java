package com.orderservice.domain.dto.output;

import com.orderservice.domain.model.Report.ReportBySaleStaff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleStaffDTO {
    private ReportBySaleStaff reportBySaleStaff;
    private String name;
}
