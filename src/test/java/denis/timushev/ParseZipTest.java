package denis.timushev;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseZipTest {

    File file = new File("src/test/resources/files/FilesForTest.zip");

    @Test
    void parseXlsxInZipTest() throws Exception {
        ZipFile zipFile = new ZipFile(file);
        ZipEntry zipEntry = zipFile.getEntry("NDFL.xlsx");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        XLS xls = new XLS(inputStream);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(14)
                .getCell(1)
                .getStringCellValue()).contains("7604074992");
    }

    @Test
    void parsePdfInZipTest() throws Exception {
        ZipFile zipFile = new ZipFile(file);
        ZipEntry zipEntry = zipFile.getEntry("Guide.pdf");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        PDF pdf = new PDF(inputStream);
        assertThat(pdf.title).isEqualTo("Краткое справочное руководство");
        assertThat(pdf.numberOfPages).isEqualTo(1);
    }

    @Test
    void parseCsvInZipTest() throws Exception {
        ZipFile zipFile = new ZipFile(file);
        ZipEntry zipEntry = zipFile.getEntry("wholesale-trade-survey.csv");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        List<String[]> content = reader.readAll();
        assertThat(content.get(2)).contains("WTSQ.SFA1CA",
                "1995.06",
                "2100.44",
                "",
                "F",
                "Dollars",
                "6",
                "Wholesale Trade Survey - WTS",
                "Industry by variable - (ANZSIC06) - Subannual Financial Collection",
                "Basic material wholesaling",
                "Sales (operating income)",
                "Current prices",
                "Unadjusted",
                "");
    }
}
