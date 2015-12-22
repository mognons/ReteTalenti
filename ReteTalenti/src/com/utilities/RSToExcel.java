package com.utilities;

import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.utilities.HSSFCellUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RSToExcel {
	private class DataSheet {
		private ResultSet resultSet;
		private HSSFSheet sheet;
		private FormatType [] formatTypes;
		
		private DataSheet(ResultSet rs, FormatType [] ft, HSSFSheet sh) {
			this.resultSet = rs;
			this.formatTypes = ft;
			this.sheet = sh;
		}
	}
	
	public HSSFWorkbook workbook;
	private HSSFFont boldFont;
	private HSSFDataFormat format;
	/*
	private HSSFSheet sheet;
	private ResultSet resultSet;
	private FormatType[] formatTypes;
	*/
	List<DataSheet> dataSheets = new ArrayList<DataSheet>();

	public RSToExcel(ResultSet rs, FormatType[] ft, String sheetName) {
		workbook = new HSSFWorkbook();
		dataSheets.add(new DataSheet(rs, ft, workbook.createSheet(sheetName)));
		boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		format = workbook.createDataFormat();
	}
	
	public void addRSToExcel(ResultSet rs, FormatType[] ft, String sheetName) {
		dataSheets.add(new DataSheet(rs, ft, workbook.createSheet(sheetName)));
	}


	public void addRSToExcel(ResultSet rs, String sheetName) {
		this.addRSToExcel(rs, null, sheetName);;
	}


	public RSToExcel(ResultSet resultSet, String sheetName) {
		this(resultSet, null, sheetName);
	}

	private FormatType getFormatType(Class _class) {
//		if (_class == Integer.class || _class == Long.class) {
//			return FormatType.INTEGER;
//		} else if (_class == Float.class || _class == Double.class) {
//			return FormatType.FLOAT;
//		} else if (_class == Timestamp.class || _class == java.sql.Date.class) {
//			return FormatType.DATE;
//		} else {
//			return FormatType.TEXT;
//		}
		if (_class == Integer.class || _class == Long.class) {
			return FormatType.INTEGER;
		} else if (_class == Float.class || _class == Double.class) {
			return FormatType.FLOAT;
		} else if (_class == java.sql.Date.class) {
			return FormatType.DATE;
		} else {
			return FormatType.TEXT;
		}
	}

	public void generate(OutputStream outputStream) throws Exception {
		try {
			Iterator<DataSheet> itr = dataSheets.iterator();
			while (itr.hasNext()) {
				DataSheet sheet = itr.next();
				ResultSetMetaData resultSetMetaData = sheet.resultSet.getMetaData();
				if (sheet.formatTypes != null
						&& sheet.formatTypes.length != resultSetMetaData.getColumnCount()) {
					throw new IllegalStateException(
							"Number of types is not identical to number of resultset columns. "
									+ "Number of types: " + sheet.formatTypes.length
									+ ". Number of columns: "
									+ resultSetMetaData.getColumnCount());
				}
				int currentRow = 0;
				HSSFRow row = sheet.sheet.createRow(currentRow);
				int numCols = resultSetMetaData.getColumnCount();
				boolean isAutoDecideFormatTypes;
				if (isAutoDecideFormatTypes = (sheet.formatTypes == null)) {
					sheet.formatTypes = new FormatType[numCols];
				}
				for (int i = 0; i < numCols; i++) {
					String title = resultSetMetaData.getColumnName(i + 1);
					writeCell(row, i, title, FormatType.TEXT, boldFont);
					if (isAutoDecideFormatTypes) {
						Class _class = Class.forName(resultSetMetaData
								.getColumnClassName(i + 1));
						sheet.formatTypes[i] = getFormatType(_class);
					}
				}
				currentRow++;
				// Write report rows
				while (sheet.resultSet.next()) {
					row = sheet.sheet.createRow(currentRow++);
					for (int i = 0; i < numCols; i++) {
						Object value = sheet.resultSet.getObject(i + 1);
						writeCell(row, i, value, sheet.formatTypes[i]);
					}
				}
				// Autosize columns
				for (int i = 0; i < numCols; i++) {
					sheet.sheet.autoSizeColumn((short) i);
				}
			}
			workbook.write(outputStream);
		} finally {
			outputStream.close();
			dataSheets = null;
		}
	}

	public void generate(File file) throws Exception {
		generate(new FileOutputStream(file));
	}

	private void writeCell(HSSFRow row, int col, Object value,
			FormatType formatType) throws NestableException {
		writeCell(row, col, value, formatType, null, null);
	}

	private void writeCell(HSSFRow row, int col, Object value,
			FormatType formatType, HSSFFont font) throws NestableException {
		writeCell(row, col, value, formatType, null, font);
	}

	private void writeCell(HSSFRow row, int col, Object value,
			FormatType formatType, Short bgColor, HSSFFont font)
			throws NestableException {
		HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
		if (value == null) {
			return;
		}
		if (font != null) {
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);
			cell.setCellStyle(style);
		}
//		System.out.println("Cella " + col + " valore " + value + " tipo " + formatType);
		switch (formatType) {
		case TEXT:
			cell.setCellValue(value.toString());
			break;
		case INTEGER:
			cell.setCellValue(((Number) value).intValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("#,##0")));
			break;
		case FLOAT:
			cell.setCellValue(((Number) value).doubleValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
			break;
		case DATE:
			cell.setCellValue((Date) value);
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
			break;
		case MONEY:
			cell.setCellValue(((Number) value).intValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.DATA_FORMAT,
					format.getFormat("($#,##0.00);($#,##0.00)"));
			break;
		case PERCENTAGE:
			cell.setCellValue(((Number) value).doubleValue());
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.DATA_FORMAT,
					HSSFDataFormat.getBuiltinFormat("0.00%"));
		}
		if (bgColor != null) {
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.FILL_FOREGROUND_COLOR, bgColor);
			HSSFCellUtil.setCellStyleProperty(cell, workbook,
					HSSFCellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
		}
	}

	public enum FormatType {
		TEXT, INTEGER, FLOAT, DATE, MONEY, PERCENTAGE
	}
}