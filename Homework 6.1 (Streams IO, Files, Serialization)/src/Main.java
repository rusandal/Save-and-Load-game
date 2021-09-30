import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String[] allDir = {"//tmp", "//res", "//savegame", "//src", "//src//main", "//src//test", "//res//drawables", "//res//vectors", "//res//icons"};
        String[] allFilesMain = {"//Main.java", "//Utils.java"};
        String root = "D://Games";
        String text = "";
        StringBuilder sb = new StringBuilder(text);
        for (String myDir : allDir) {
            File dir = new File(root + myDir);
            if (dir.mkdir()) {
                sb.append("Каталог " + dir.getAbsolutePath() + " создан\n");
            }
        }
        for (String myFile : allFilesMain) {
            File file = new File(root + "//src//main" + myFile);
            try {
                if (file.createNewFile()) {
                    sb.append("Файл " + file.getAbsoluteFile() + " создан\n");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        File file = new File(root + "//tmp//text.txt");
        try {
            if (file.createNewFile()) {
                sb.append("Файл " + file.getAbsoluteFile() + " создан\n");
                try (FileWriter writer = new FileWriter(file.getAbsoluteFile(), false)) {
                    writer.write(sb.toString());
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(sb);
    }
}
