import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by albert on 04.11.16.
 */
public class MyPlayground {

    public static void main(String[] args) throws IOException {

        XSSFWorkbook wb = new XSSFWorkbook();

        //CreationHelper factory = wb.getCreationHelper();


        XSSFSheet sheet = wb.createSheet("Muay thai");
        XSSFRow writeRow0 = sheet.createRow(0);
        XSSFCell writeCellAX = writeRow0.createCell(0);
        XSSFCell writeCellBX = writeRow0.createCell(1);
        XSSFCell writeCellCX = writeRow0.createCell(2);
        XSSFCell writeCellDX = writeRow0.createCell(3);
        XSSFCell writeCellEX = writeRow0.createCell(4);
        XSSFCell writeCellFX = writeRow0.createCell(5);
        XSSFCell writeCellGX = writeRow0.createCell(6);

        writeCellAX.setCellValue("Место");
        writeCellBX.setCellValue("Рейтинг");
        writeCellCX.setCellValue("Комментарии");
        writeCellDX.setCellValue("Тип");
        writeCellEX.setCellValue("Сайт");
        writeCellFX.setCellValue("Телефон");
        writeCellGX.setCellValue("Адрес");





        String fname = "data.xlsx";
        FileOutputStream out = new FileOutputStream(new File("/home/albert/"+fname));
        wb.write(out);
        out.close();

        wb.close();
    }

}
