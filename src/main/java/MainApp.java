import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Слишком большое количество агрументов, завершение!");
            System.exit(0);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Файл не найден, завершение!");
        }
        System.out.println("Файл " + args[0] + " обнаружен, начата обработка, ожидайте:");
        Integer count = 0;

        Map<String, Integer> hMap = new LinkedHashMap<String, Integer>();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(args[0])))) {
            XMLStreamReader reader = processor.getReader();

            while (processor.doUntil(XMLEvent.START_ELEMENT, "modification")) {
                if (!hMap.containsKey(reader.getAttributeValue(0))) {
                    hMap.put(reader.getAttributeValue(0), count++);
                }
            }
        } catch (XMLStreamException |
                IOException e) {
            e.printStackTrace();
        }
        System.out.println("Количество уникальных modification - " + count);

    }
}
    