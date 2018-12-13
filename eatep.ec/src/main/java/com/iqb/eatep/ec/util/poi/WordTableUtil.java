package com.iqb.eatep.ec.util.poi;

import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class WordTableUtil {
    

    /** 
     * @Description: 设置Table的边框 
     */  
    public static void setTableBorders(XWPFTable table, STBorder.Enum borderType,  
            String size, String color, String space) {  
        CTTblPr tblPr = getTableCTTblPr(table);  
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()  
                : tblPr.addNewTblBorders();  
        CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH()  
                : borders.addNewInsideH();  
        hBorder.setVal(borderType);  
        hBorder.setSz(new BigInteger(size));  
        hBorder.setColor(color);  
        hBorder.setSpace(new BigInteger(space));  
  
        CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV()  
                : borders.addNewInsideV();  
        vBorder.setVal(borderType);  
        vBorder.setSz(new BigInteger(size));  
        vBorder.setColor(color);  
        vBorder.setSpace(new BigInteger(space));  
  
        CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders  
                .addNewLeft();  
        lBorder.setVal(borderType);  
        lBorder.setSz(new BigInteger(size));  
        lBorder.setColor(color);  
        lBorder.setSpace(new BigInteger(space));  
  
        CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders  
                .addNewRight();  
        rBorder.setVal(borderType);  
        rBorder.setSz(new BigInteger(size));  
        rBorder.setColor(color);  
        rBorder.setSpace(new BigInteger(space));  
  
        CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders  
                .addNewTop();  
        tBorder.setVal(borderType);  
        tBorder.setSz(new BigInteger(size));  
        tBorder.setColor(color);  
        tBorder.setSpace(new BigInteger(space));  
  
        CTBorder bBorder = borders.isSetBottom() ? borders.getBottom()  
                : borders.addNewBottom();  
        bBorder.setVal(borderType);  
        bBorder.setSz(new BigInteger(size));  
        bBorder.setColor(color);  
        bBorder.setSpace(new BigInteger(space));  
    } 
    
    /** 
     * @Description: 得到Table的CTTblPr,不存在则新建 
     */  
    public static CTTblPr getTableCTTblPr(XWPFTable table) {  
        CTTbl ttbl = table.getCTTbl();  
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl  
                .getTblPr();  
        return tblPr;  
    }  
    

    /** 
     * @Description: 设置表格总宽度与水平对齐方式 
     */  
    public static void setTableWidthAndHAlign(XWPFTable table, String width,  
            STJc.Enum enumValue) {  
        CTTblPr tblPr = getTableCTTblPr(table);  
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr  
                .addNewTblW();  
        if (enumValue != null) {  
            CTJc cTJc = tblPr.addNewJc();  
            cTJc.setVal(enumValue);  
        }  
        tblWidth.setW(new BigInteger(width));  
        tblWidth.setType(STTblWidth.DXA);  
    } 
    
    /** 
     * @Description: 设置单元格Margin 
     */  
    public static void setTableCellMargin(XWPFTable table, int top, int left,  
            int bottom, int right) {  
        table.setCellMargins(top, left, bottom, right);  
    } 
    
    /** 
     * @Description: 设置表格列宽 
     */  
    public static void setTableGridCol(XWPFTable table, int[] colWidths) {  
        CTTbl ttbl = table.getCTTbl();  
        CTTblGrid tblGrid = ttbl.getTblGrid() != null ? ttbl.getTblGrid()  
                : ttbl.addNewTblGrid();  
        for (int j = 0, len = colWidths.length; j < len; j++) {  
            CTTblGridCol gridCol = tblGrid.addNewGridCol();  
            gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));  
        }  
    } 
    
    /** 
     * @Description: 设置行高 
     */  
    public static void setRowHeight(XWPFTableRow row, String hight,  
            STHeightRule.Enum heigthEnum) {  
        CTTrPr trPr = getRowCTTrPr(row);  
        CTHeight trHeight;  
        if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {  
            trHeight = trPr.getTrHeightList().get(0);  
        } else {  
            trHeight = trPr.addNewTrHeight();  
        }  
        trHeight.setVal(new BigInteger(hight));  
        if (heigthEnum != null) {  
            trHeight.setHRule(heigthEnum);  
        }  
    } 
    
    /** 
     * @Description: 得到CTTrPr,不存在则新建 
     */  
    public static CTTrPr getRowCTTrPr(XWPFTableRow row) {  
        CTRow ctRow = row.getCtRow();  
        CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();  
        return trPr;  
    }  
    
    /** 
     * @Description: 设置底纹 
     */  
    public static void setCellShdStyle(XWPFTableCell cell, boolean isShd,  
            String shdColor, STShd.Enum shdStyle) {  
        CTTcPr tcPr = getCellCTTcPr(cell);  
        if (isShd) {  
            // 设置底纹  
            CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();  
            if (shdStyle != null) {  
                shd.setVal(shdStyle);  
            }  
            if (shdColor != null) {  
                shd.setColor(shdColor);  
                shd.setFill(shdColor);  
            }  
        }  
    }  
    
    /** 
     *  
     * @Description: 得到Cell的CTTcPr,不存在则新建 
     */  
    public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {  
        CTTc cttc = cell.getCTTc();  
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();  
        return tcPr;  
    }
    
    /** 
     * @Description: 得到单元格第一个Paragraph 
     */  
    public static XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {  
        XWPFParagraph p;  
        if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {  
            p = cell.getParagraphs().get(0);  
        } else {  
            p = cell.addParagraph();  
        }  
        return p;  
    } 

}
