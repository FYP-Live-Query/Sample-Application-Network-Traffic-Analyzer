import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class XlsxUtilitiesTest {

    @Test
    void getExecutorService() throws Exception {
        XlsxUtilities utilities = new XlsxUtilities();

        utilities.createXlsxSheet("sheet-a");
        utilities.createXlsxSheet("sheet-b");

        List<String> a = new ArrayList<>();
        a.add("John");
        a.add("John");
        a.add("John");

        List<Collection<String>> data = new ArrayList<>();
        data.add(a);

        utilities.writeRow("sheet-a",data);

        a.add("John");
        utilities.writeRow("sheet-b",data);

        long g = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async");
        System.out.println("Async : " + (System.currentTimeMillis() - g));

        long l = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-a");
        System.out.println("Async : " + (System.currentTimeMillis() - l));

        long d = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-b");
        System.out.println("Async : " + (System.currentTimeMillis() - d));

        long H = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-c");
        System.out.println("Async : " + (System.currentTimeMillis() - H));

        long D = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - D));

        long s = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - s));

        long h = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - h));

        long k = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - k));

        long i = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - i));

        long jj = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-e");
        System.out.println("Async : " + (System.currentTimeMillis() - jj));

        long jjj = System.currentTimeMillis();
        utilities.sheetsDiskWriteAsync("workbook-async-l");
        System.out.println("Async : " + (System.currentTimeMillis() - jjj));
        utilities.close();

    }

}