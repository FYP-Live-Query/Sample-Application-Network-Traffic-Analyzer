import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class XlsxUtilities implements AutoCloseable{
    private final Workbook workbook;
    private final HashMap<String,Sheet> sheetsHashMap;

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private final ExecutorService executorService;

    public XlsxUtilities(){
        workbook = new XSSFWorkbook();
        sheetsHashMap = new HashMap<>();
        executorService = Executors.newFixedThreadPool(3);
    }

    public void sheetsDiskWriteSync(String workbookName){
        try (FileOutputStream outputStream = new FileOutputStream(workbookName + "_LQS.xlsx")) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createXlsxSheet(String nameOfTheSheet){
        Sheet sheet = workbook.createSheet(nameOfTheSheet);
        sheetsHashMap.put(nameOfTheSheet,sheet);
    }

    private synchronized byte[] workbookToBytes(Workbook workbook) throws IOException {

        byte[] dataBytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            dataBytes = baos.toByteArray();
        }
        return dataBytes;
    }

    public <T> void writeRow(String sheetName ,Collection<Collection<T>> dataRowAsCollection){
        Iterator<Collection<T>> tIterator = dataRowAsCollection.iterator();
        int rowNum = 0;
        while(tIterator.hasNext()){
            Sheet sheet = sheetsHashMap.get(sheetName);
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (T cellData : tIterator.next()) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(cellData.toString());
            }
        }
    }

    public void sheetsDiskWriteAsync(String workbookName) throws InterruptedException {
        // Write the workbook to a file asynchronously
        Path path = Paths.get(workbookName + "_LQS.xlsx");
        executorService.submit(() -> {
            try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                byte[] dataBytes = workbookToBytes(workbook);
                ByteBuffer buffer = ByteBuffer.allocate(dataBytes.length);
                buffer = buffer.put(dataBytes);
                buffer.flip();
                channel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("num of bytes written: " + result);

                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("Write failed");
                        exc.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void close() throws Exception {
        boolean b = executorService.awaitTermination(4,TimeUnit.SECONDS);
        if(b)
            executorService.shutdown();
    }
}
