package com.utilities;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.contrib.CellUtil;

/**
 *  Various utility functions that make working with a cells and rows easier.  The various
 * methods that deal with style's allow you to create your HSSFCellStyles as you need them.
 * When you apply a style change to a cell, the code will attempt to see if a style already
 * exists that meets your needs.  If not, then it will create a new style.  This is to prevent
 * creating too many styles.  there is an upper limit in Excel on the number of styles that
 * can be supported.
 *
 *@author     Eric Pugh epugh@upstate.com
 */
public final class HSSFCellUtil {

    public static final String ALIGNMENT = "alignment";
    public static final String BORDER_BOTTOM = "borderBottom";
    public static final String BORDER_LEFT = "borderLeft";
    public static final String BORDER_RIGHT = "borderRight";
    public static final String BORDER_TOP = "borderTop";
    public static final String BOTTOM_BORDER_COLOR = "bottomBorderColor";
    public static final String DATA_FORMAT = "dataFormat";
    public static final String FILL_BACKGROUND_COLOR = "fillBackgroundColor";
    public static final String FILL_FOREGROUND_COLOR = "fillForegroundColor";
    public static final String FILL_PATTERN = "fillPattern";
    public static final String FONT = "font";
    public static final String HIDDEN = "hidden";
    public static final String INDENTION = "indention";
    public static final String LEFT_BORDER_COLOR = "leftBorderColor";
    public static final String LOCKED = "locked";
    public static final String RIGHT_BORDER_COLOR = "rightBorderColor";
    public static final String ROTATION = "rotation";
    public static final String TOP_BORDER_COLOR = "topBorderColor";
    public static final String VERTICAL_ALIGNMENT = "verticalAlignment";
    public static final String WRAP_TEXT = "wrapText";

    private static UnicodeMapping unicodeMappings[];

    private static final class UnicodeMapping {

        public final String entityName;
        public final String resolvedValue;

        public UnicodeMapping(String pEntityName, String pResolvedValue) {
            entityName = "&" + pEntityName + ";";
            resolvedValue = pResolvedValue;
        }
    }

    private HSSFCellUtil() {
        // no instances of this class
    }

    /**
     *  Get a row from the spreadsheet, and create it if it doesn't exist.
     *
     *@param  rowIndex  The 0 based row number
     *@param  sheet       The sheet that the row is part of.
     *@return             The row indicated by the rowCounter
     */
    public static HSSFRow getRow(int rowIndex, HSSFSheet sheet) {
    	return (HSSFRow) CellUtil.getRow(rowIndex, sheet);
    }

    /**
     * Get a specific cell from a row. If the cell doesn't exist,
     *  then create it.
     *
     *@param  row     The row that the cell is part of
     *@param  columnIndex  The column index that the cell is in.
     *@return         The cell indicated by the column.
     */
    public static HSSFCell getCell(HSSFRow row, int columnIndex) {
        return (HSSFCell) CellUtil.getCell(row, columnIndex);
    }

    /**
     *  Creates a cell, gives it a value, and applies a style if provided
     *
     * @param  row     the row to create the cell in
     * @param  column  the column index to create the cell in
     * @param  value   The value of the cell
     * @param  style   If the style is not null, then set
     * @return         A new HSSFCell
     */
    public static HSSFCell createCell(HSSFRow row, int column, String value, HSSFCellStyle style) {
    	return (HSSFCell) CellUtil.createCell(row, column, value, style);
    }

    /**
     *  Create a cell, and give it a value.
     *
     *@param  row     the row to create the cell in
     *@param  column  the column index to create the cell in
     *@param  value   The value of the cell
     *@return         A new HSSFCell.
     */
    public static HSSFCell createCell(HSSFRow row, int column, String value) {
        return createCell( row, column, value, null );
    }

    /**
     *  Take a cell, and align it.
     *
     *@param  cell     the cell to set the alignment for
     *@param  workbook               The workbook that is being worked with.
     *@param  align  the column alignment to use.
     *
     * @see HSSFCellStyle for alignment options
     */
    public static void setAlignment(HSSFCell cell, HSSFWorkbook workbook, short align) {
    	CellUtil.setAlignment(cell, workbook, align);
    }

    /**
     *  Take a cell, and apply a font to it
     *
     *@param  cell     the cell to set the alignment for
     *@param  workbook               The workbook that is being worked with.
     *@param  font  The HSSFFont that you want to set...
     */
    public static void setFont(HSSFCell cell, HSSFWorkbook workbook, HSSFFont font) {
    	CellUtil.setFont(cell, workbook, font);
    }

    /**
     *  This method attempt to find an already existing HSSFCellStyle that matches
     *  what you want the style to be. If it does not find the style, then it
     *  creates a new one. If it does create a new one, then it applies the
     *  propertyName and propertyValue to the style. This is necessary because
     *  Excel has an upper limit on the number of Styles that it supports.
     *
     *@param  workbook               The workbook that is being worked with.
     *@param  propertyName           The name of the property that is to be
     *      changed.
     *@param  propertyValue          The value of the property that is to be
     *      changed.
     *@param  cell                   The cell that needs it's style changes
     */
    public static void setCellStyleProperty(HSSFCell cell, HSSFWorkbook workbook,
			String propertyName, Object propertyValue) {
    	CellUtil.setCellStyleProperty(cell, workbook, propertyName, propertyValue);
    }

    /**
     *  Looks for text in the cell that should be unicode, like &alpha; and provides the
     *  unicode version of it.
     *
     *@param  cell  The cell to check for unicode values
     *@return       translated to unicode
     */
    public static HSSFCell translateUnicodeValues(HSSFCell cell){
    	CellUtil.translateUnicodeValues(cell);
    	return cell;
    }
}