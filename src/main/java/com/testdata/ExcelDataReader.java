package com.testdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelDataReader {

	private ExcelDataReader() {
	}

	public static List<String> getColumnValues(String filePath, String sheetName, String headerName) {
		List<String> values = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = requireSheet(workbook, sheetName);
			Row headerRow = sheet.getRow(0);
			int colIndex = findColumnIndex(headerRow, headerName);

			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				String value = getCellValue(row.getCell(colIndex));
				if (!value.isEmpty()) {
					values.add(value);
				}
			}
		} catch (IOException e) {
			throw new TestDataException("Could not read Excel file: " + filePath, e);
		}
		return values;
	}

	public static String getCellData(String filePath, String sheetName, String headerName, int dataRowIndex) {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = requireSheet(workbook, sheetName);
			Row headerRow = sheet.getRow(0);
			Row dataRow = sheet.getRow(dataRowIndex);
			if (headerRow == null || dataRow == null) {
				throw new TestDataException("Header or data row is missing in sheet: " + sheetName);
			}
			int colIndex = findColumnIndex(headerRow, headerName);
			return getCellValue(dataRow.getCell(colIndex));
		} catch (IOException e) {
			throw new TestDataException("Could not read Excel file: " + filePath, e);
		}
	}

	private static Sheet requireSheet(Workbook workbook, String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			throw new TestDataException("Sheet not found: " + sheetName);
		}
		return sheet;
	}

	private static int findColumnIndex(Row headerRow, String headerName) {
		for (int col = 0; col < headerRow.getLastCellNum(); col++) {
			Cell cell = headerRow.getCell(col);
			if (cell != null && cell.getStringCellValue().trim().equalsIgnoreCase(headerName)) {
				return col;
			}
		}
		throw new TestDataException("Header not found: " + headerName);
	}

	private static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}
}
