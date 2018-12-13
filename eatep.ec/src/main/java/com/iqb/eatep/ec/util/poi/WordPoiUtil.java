package com.iqb.eatep.ec.util.poi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.alibaba.fastjson.JSONObject;
import com.iqb.eatep.ec.base.EcReturnInfo;
import com.iqb.eatep.ec.base.EcAttr.BizConst;
import com.iqb.etep.common.exception.IqbException;
import com.iqb.etep.common.utils.DatePattern;
import com.iqb.etep.common.utils.DateUtil;

/**
 * 
 * Description: 转换流
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年2月4日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class WordPoiUtil {
    
    /** 日志  **/
    private static final Logger logger = LoggerFactory.getLogger(WordPoiUtil.class);
    
    /** 临时文件存储路径  **/
    private static String tempWordPath = loadDefaultLogDir();
    
    private static String WORDTEMP = "wordtemp";
    
    private static String CONTRACT = "contract";
    
    /**
     * 
     * Description: 实现文字替换(输入文件地址)
     * @param
     * @return byte[]
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午3:48:31
     */
    @SuppressWarnings("rawtypes")
    public static String getDestUrlAfterReplaceWord(String srcPath, String destFileName, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String destPath = "";
        judeDirExists(new File(tempWordPath + File.separator));
        if(destFileName != null){
            destPath = tempWordPath + File.separator + destFileName;
        }
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {
            /** 比较文件扩展名  **/
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
                    /** 替换段落中的指定文字  **/
                    Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (int i = 0; i < runs.size(); i++) {
                            String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                String key = entry.getKey();
                                String val = entry.getValue();
                                if("carColor".equals(entry.getKey())){
                                    List<List> valList = JSONObject.parseArray(val, List.class);
                                    if(CollectionUtils.isEmpty(valList)){
                                        continue;
                                    }
                                    XWPFTable dTable = document.createTable(valList.size(), valList.get(1).size());
                                    WordTableUtil.setTableBorders(dTable, STBorder.SINGLE, "4", "auto", "0");
                                    WordTableUtil.setTableWidthAndHAlign(dTable, "9024", STJc.CENTER);
                                    WordTableUtil.setTableCellMargin(dTable, 0, 108, 0, 108);  
                                    int[] colWidths = new int[] { 2169, 2638, 525, 3692 };  
                                    WordTableUtil.setTableGridCol(dTable, colWidths); 
                                    createTable(dTable, document, valList);
                                    continue;
                                }
                                oneparaString = oneparaString.replace(key, val);
                            }
                            runs.get(i).setText(oneparaString, 0);
                        }
                    }

                    /** 替换表格中的指定文字  **/
                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellTextString = cell.getText();
                                for (Entry<String, String> e : map.entrySet()) {
                                    if (cellTextString.contains(e.getKey()))
                                        cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                }
                                cell.removeParagraph(0);
                                cell.setText(cellTextString);
                            }
                        }
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } 
            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc")) && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                /** doc只能生成doc，如果生成docx会出错  **/
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return destPath;
    }
    
    // 判断文件夹是否存在
    public static void judeDirExists(File file) {
        if (file.exists()) {
        } else {
            file.mkdirs();
        }
    }
    
    /**
     * 
     * Description: 实现文字替换(输入字节流)
     * @param
     * @return byte[]
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午3:48:31
     */
    @SuppressWarnings("rawtypes")
    public static String getDestUrlAfterReplaceWordWithB(String baseDir, String ecName, InputStream is, String destFileName, Map<String, String> map) {
        String[] sp = ecName.split("\\.");
        String destPath = "";
        judeDirExists(new File(baseDir + getTempServiceUrl()));
        if(destFileName != null){
            destPath = baseDir + getTempServiceUrl() + destFileName;
        }
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {
            /** 比较文件扩展名  **/
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(is);
                    /** 替换段落中的指定文字  **/
                    Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (int i = 0; i < runs.size(); i++) {
                            String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                            if(StringUtils.isEmpty(oneparaString)){
                                continue;
                            }
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                String key = entry.getKey();
                                String val = entry.getValue();
                                if(!oneparaString.contains(key)){
                                    continue;
                                }
                                if(StringUtils.contains(key, BizConst.WORD_TABLE_PRE)){
                                    List<List> valList = JSONObject.parseArray(val, List.class);
                                    if(CollectionUtils.isEmpty(valList)){
                                        continue;
                                    }
                                    XmlCursor cursor = paragraph.getCTP().newCursor();
                                    XWPFTable dTable = document.insertNewTbl(cursor);
                                    WordTableUtil.setTableBorders(dTable, STBorder.SINGLE, "4", "auto", "0");
                                    WordTableUtil.setTableWidthAndHAlign(dTable, "9024", STJc.CENTER);
                                    WordTableUtil.setTableCellMargin(dTable, 0, 108, 0, 108);  
                                    int[] colWidths = new int[] { 2169, 2638, 525, 3692 };  
                                    WordTableUtil.setTableGridCol(dTable, colWidths);
                                    createTable(dTable, document, valList);
                                    oneparaString = oneparaString.replace(key, StringUtils.EMPTY);
                                    continue;
                                }
                                oneparaString = oneparaString.replace(key, val);
                            }
                            runs.get(i).setText(oneparaString, 0);
                        }
                    }
                    
                    /** 替换表格中的指定文字  **/
                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellTextString = cell.getText();
                                for (Entry<String, String> e : map.entrySet()) {
                                    if (cellTextString.contains(e.getKey()))
                                        cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                }
                                cell.removeParagraph(0);
                                cell.setText(cellTextString);
                            }
                        }
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } 
            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc")) && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                /** doc只能生成doc，如果生成docx会出错  **/
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(is);
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return destPath;
    }
    
    /**
     * 
     * Description: 获取替换后的文字的输出流
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月4日 上午10:25:21
     */
    public static byte[] getOutputStreamAfterReplaceWord(String srcPath, String destFileName, Map<String, String> map) {
        String destPath = getDestUrlAfterReplaceWord(srcPath, destFileName, map);
        byte[] buffer = null;
        try {
            FileInputStream isStream = new FileInputStream(destPath);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            buffer = new byte[isStream.available()];
            isStream.read(buffer);
            byteOut.write(buffer);
            isStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
        return buffer;
    }
    
    
    /**
     * 
     * Description: 获取替换后的文字的输出流
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月4日 上午10:25:21
     */
    public static byte[] getOutputStreamAfterReplaceWordWithB(String baseDir, String ecName, InputStream is, String destFileName, Map<String, String> map) {
        String destPath = getDestUrlAfterReplaceWordWithB(baseDir, ecName, is, destFileName, map);
        byte[] buffer = null;
        try {
            FileInputStream isStream = new FileInputStream(destPath);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            buffer = new byte[isStream.available()];
            isStream.read(buffer);
            byteOut.write(buffer);
            isStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
        return buffer;
    }
    
    
    /**
     * 
     * Description: 获取替换后的文字的输出流
     * @param
     * @return boolean
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月4日 上午10:25:21
     */
    public static byte[] getOutputStreamAfterReplaceWord(String srcPath, Map<String, String> map) {
        return getOutputStreamAfterReplaceWord(srcPath, null, map);
    }
    
    /**
     * 
     * Description: 获取替换后的文字的输出流(输入字节流)
     * @param
     * @return byte[]
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月23日 下午2:08:47
     */
    public static synchronized byte[] getOutputStreamAfterReplaceWordWithB(String baseDir, String ecName, InputStream is, Map<String, String> map) {
        return getOutputStreamAfterReplaceWordWithB(baseDir, ecName, is, ecName, map);
    }
    
    /**
     * 
     * Description: 获取临时顺序数
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月4日 上午11:36:46
     */
    public static String getWordTempSeq(){
        return Long.toString(Thread.currentThread().getId());
    }
    
    /**
     * 
     * Description: 获取临时目录
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月4日 下午2:26:11
     */
    private static String loadDefaultLogDir() {
        String logDir = System.getProperty("catalina.home");
        if (logDir != null && logDir.length() > 0) {
            return logDir;
        }
        logDir = System.getProperty("java.io.tmpdir");
        if (logDir != null && logDir.length() > 0) {
            return logDir;
        }
        return ".";
    }
    
    /**
     * 
     * Description: word转html
     * @param
     * @return void
     * @throws Exception 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月7日 上午11:36:33
     */
    public static void PoiWordToHtml(final String filePath, String fileName) throws Exception{
        String fileUri = filePath + fileName;
        fileUri = "C:\\Users\\iqb\\Desktop\\资产端\\电子签章\\接口文档-V1.3.6_标准20170105.docx";
        File f = new File(fileUri);  
        if (!f.exists()) {  
            System.out.println("Sorry File does not Exists!");  
        } else {  
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {
                // 1) 加载word文档生成 XWPFDocument对象
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);

                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                File imageFolderFile = new File(filePath);
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                options.setIgnoreStylesIfUnused(false);
                options.setFragment(true);

                // 3) 将 XWPFDocument转换成XHTML
                OutputStream out = new FileOutputStream(new File(fileUri));
                XHTMLConverter.getInstance().convert(document, out, options);
            } else if (f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")){
                InputStream input = new FileInputStream(new File(fileUri));
                HWPFDocument wordDocument = new HWPFDocument(input);
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                //设置图片存放的位置
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                        File imgPath = new File(filePath);
                        if(!imgPath.exists()){//图片目录不存在则创建
                            imgPath.mkdirs();
                        }
                        File file = new File(filePath + suggestedName);
                        try {
                            OutputStream os = new FileOutputStream(file);
                            os.write(content);
                            os.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return filePath + suggestedName;
                    }
                });
                
                //解析word文档
                wordToHtmlConverter.processDocument(wordDocument);
                Document htmlDocument = wordToHtmlConverter.getDocument();
                
                File htmlFile = new File(filePath + "22.html");
                OutputStream outStream = new FileOutputStream(htmlFile);

                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outStream);

                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer serializer = factory.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                
                serializer.transform(domSource, streamResult);

                outStream.close();
            }else{
                System.out.println("Enter only MS Office Word files");  
            }
        }
    }
    
    /**
     * 
     * Description: word文档转html并获取html缓存地址
     * @param
     * @return String
     * @throws IqbException 
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月8日 下午4:43:05
     */
    public static String getHtmlUrlFromWord(String filePath, String fileName, String htmlFileName) throws IqbException{
        final String file = filePath + fileName;
        File f = new File(file);  
        if (!f.exists()) {  
            throw new IqbException(EcReturnInfo.EC_POI_01010005);
        } else {  
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {  
                try {
                    return Word2007ToHtml(filePath, fileName, null, htmlFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new IqbException(EcReturnInfo.EC_POI_01010006);
                }
            }else if(f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")){
                try {
                    return Word2003ToHtml(filePath, fileName, null, htmlFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new IqbException(EcReturnInfo.EC_POI_01010006);
                }
            }
        }
        throw new IqbException(EcReturnInfo.EC_POI_01010006);
    }
    
    public static String getTempServiceUrl(){
        return File.separator + CONTRACT + File.separator + WORDTEMP + File.separator + getTodayDateStr() + File.separator;
    }
    
    public static String getHtmlUrlFromWord(String baseDir, String filePath, String fileName, InputStream is, String htmlFileName) throws IqbException{
        if(filePath == null){
            filePath = baseDir + getTempServiceUrl(); 
        }
        judeDirExists(new File(filePath));
        htmlFileName = htmlFileName + ".html";
        final String file = filePath + fileName ;
        File f = new File(file);  
        if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {  
            try {
                return getTempServiceUrl() + Word2007ToHtml(filePath, fileName, is, htmlFileName);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new IqbException(EcReturnInfo.EC_POI_01010006);
            }
        }else if(f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")){
            try {
                return getTempServiceUrl() + Word2003ToHtml(filePath, fileName, is, htmlFileName);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new IqbException(EcReturnInfo.EC_POI_01010006);
            }
        }
        throw new IqbException(EcReturnInfo.EC_POI_01010006);
    }

    /**
     * 2007版本word转换成html
     * @param is 
     * @throws IOException
     */
    public static String Word2007ToHtml(String filePath, String fileName, InputStream is, String htmlFileName) throws IOException {
        if (fileName.endsWith(".docx") || fileName.endsWith(".DOCX")) {  
            XWPFDocument document = null;
            // 1) 加载word文档生成 XWPFDocument对象  
            document = new XWPFDocument(is);  
  
            // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)  
            File imageFolderFile = new File(filePath);  
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));  
            options.setExtractor(new FileImageExtractor(imageFolderFile));  
            options.setIgnoreStylesIfUnused(false);  
            options.setFragment(true);  
              
            // 3) 将 XWPFDocument转换成XHTML  
            OutputStream out = new FileOutputStream(new File(filePath + htmlFileName));  
            XHTMLConverter.getInstance().convert(document, out, options);  
            
        } else {  
            System.out.println("Enter only MS Office 2007+ files");  
        }  
        return htmlFileName;
    }  
    
    /**
     * /**
     * 2003版本word转换成html
     * @param is 
     * @return 
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public static String Word2003ToHtml(String filePath, String fileName, InputStream is, String htmlFileName) throws IOException, TransformerException, ParserConfigurationException {
        final String imagepath = filePath + "img" + File.separator;
        HWPFDocument wordDocument = null;
        wordDocument = new HWPFDocument(is);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if(!imgPath.exists()){//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });
        
        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        
        File htmlFile = new File(filePath + htmlFileName);
        OutputStream outStream = new FileOutputStream(htmlFile);
        
        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        
        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
        outStream.close();
        return htmlFileName;
    }
    
    /**
     * 
     * Description: 获取今天的日期url
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年2月27日 上午10:32:42
     */
    private static String getTodayDateStr(){
        return DateUtil.toString(new Date(), DatePattern.DATEPARTTEN_YYYYMMDD_TYPE1);
    }
    
    /**
     * 
     * Description: 创建表格
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年12月18日 下午2:11:40
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void createTable(XWPFTable xTable,XWPFDocument xdoc, List<List> list){
        xTable.setCellMargins(20, 20, 20, 20);
        for(int i = 0; i < list.size(); i++){
            List<String> rowData = list.get(i);
            if(CollectionUtils.isEmpty(rowData)){
                return;
            }
            XWPFTableRow xWPFTableRow = null;
            if(i == 0){
                xWPFTableRow = xTable.getRow(0);
            }else{
                xWPFTableRow = xTable.createRow();
            }
            for(int j = 0; j < rowData.size(); j++){
                XWPFTableCell xWPFTableCell = null;
                if(i == 0 && j == 0){
                    xWPFTableCell = xWPFTableRow.getCell(0);
                }else if(i == 0){
                    xWPFTableCell = xWPFTableRow.createCell();
                }else{
                    xWPFTableCell = xWPFTableRow.getCell(j);
                }
                setCellText(xWPFTableCell, rowData.get(j), 1000);
            }
        }
    }
    
    /**
     * 
     * Description: 获取单元和高度
     * @param
     * @return XWPFTableCell
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年12月18日 下午2:12:38
     */
    @SuppressWarnings("unused")
    private static XWPFTableCell getCellHight(XWPFTable xTable,int rowNomber,int cellNumber){
        XWPFTableRow row = null;
        row = xTable.getRow(rowNomber);
        row.setHeight(100);
        XWPFTableCell cell = null;
        cell = row.getCell(cellNumber);
        return cell;
    }
    
    /**
     * 
     * Description: 设置单元格文本。
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2017年12月18日 下午2:13:17
     */
    private static void setCellText(XWPFTableCell cell, String text, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cellPr.getTcW().setType(STTblWidth.DXA);
        XWPFParagraph pIO =cell.addParagraph();
        cell.removeParagraph(0);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily("微软雅黑");
        rIO.setColor("000000");
        rIO.setFontSize(12);
        rIO.setText(text);
    }
    
    public static void setEmptyRow(XWPFParagraph paragraph, XWPFRun r1){
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        r1 = paragraph.createRun();
    }
    
}
