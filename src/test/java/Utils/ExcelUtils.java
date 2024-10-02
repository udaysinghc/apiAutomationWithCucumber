package Utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    private Workbook workbook;

    public ExcelUtils(String excelFilePath) {
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getCredentials(String sheetName, int rowIndex) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowIndex);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("loginType", row.getCell(0).getStringCellValue());
        credentials.put("phoneCode", row.getCell(1).getStringCellValue());
        credentials.put("phoneNumber", row.getCell(2).getStringCellValue());
        credentials.put("password", row.getCell(3).getStringCellValue());

        return credentials;
    }

    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}

