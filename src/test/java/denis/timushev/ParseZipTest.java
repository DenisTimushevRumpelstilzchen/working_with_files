package denis.timushev;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseZipTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void zipTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("files/FilesForTest.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).isEqualTo("NDFL.xlsx");
            }
        }
    }
}
