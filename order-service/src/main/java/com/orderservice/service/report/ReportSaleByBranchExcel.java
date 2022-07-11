package com.orderservice.service.report;

import com.orderservice.domain.model.Report.ReportBranch;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
@Component
@RequiredArgsConstructor
public class ReportSaleByBranchExcel {
    private final OrderService orderService;
    public static final int COLUMN_INDEX_BRANCH_ID = 0;
    public static final int COLUMN_INDEX_TOTAL_ORDER = 1;
    public static final int COLUMN_INDEX_TOTAL_AMOUNT = 2;
    private static CellStyle cellStyleFormatNumber = null;

    public void export() throws IOException {

        final List<ReportBranch> getReportByBranch = orderService.getReportByBranch();
        final String excelFilePath = "D:\\work\\report\\ReportSaleBranch.xlsx";
        writeExcel(getReportByBranch, excelFilePath);
    }

    public static void writeExcel(List<ReportBranch> books, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Books"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (ReportBranch book : books) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(book, row);
            rowIndex++;
        }

        // Write footer
        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }


    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_BRANCH_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Branch ID");

        cell = row.createCell(COLUMN_INDEX_TOTAL_ORDER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total Order");

        cell = row.createCell(COLUMN_INDEX_TOTAL_AMOUNT);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total Amount");

    }

    // Write data
    private static void writeBook(ReportBranch reportBranch, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_BRANCH_ID);
        cell.setCellValue(reportBranch.getBranch());

        cell = row.createCell(COLUMN_INDEX_TOTAL_ORDER);
        cell.setCellValue(reportBranch.getTotalOrder());

        cell = row.createCell(COLUMN_INDEX_TOTAL_AMOUNT);
        cell.setCellValue(reportBranch.getTotalAmount());
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Write footer
    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(COLUMN_INDEX_TOTAL_AMOUNT, CellType.FORMULA);
//        cell.setCellFormula("SUM(E2:E6)");
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
