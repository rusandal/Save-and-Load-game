import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(15, 20, 30, 12.3);
        GameProgress gameProgress1 = new GameProgress(16, 21, 31, 13.3);
        GameProgress gameProgress2 = new GameProgress(17, 22, 32, 14.3);
        String path = "D://Games//GunRunner//savegames";
        String[] arrPath = path.split("//");
        String creatPath = "";
        for (String dir : arrPath) {
            creatPath += dir + "//";
            File file = new File(creatPath);
            if (file.mkdir()) {
                System.out.println("Папка " + file.getAbsoluteFile() + " создана");
            }
        }
        String saveFile1 = path + "//save1.dat";
        String saveFile2 = path + "//save2.dat";
        String saveFile3 = path + "//save3.dat";
        List<String> files = new ArrayList<>();
        files.add(saveFile1);
        files.add(saveFile2);
        files.add(saveFile3);
        saveGame(saveFile1, gameProgress1);
        saveGame(saveFile2, gameProgress2);
        saveGame(saveFile3, gameProgress);
        String zipFile = "myZip.zip";
        zipFiles(path + "//" + zipFile, files);
        deleteFilesAfterZip(path, zipFile);
        File file = new File(path + "//unzip");
        if (file.mkdir()) {
            System.out.println("Папка " + file.getAbsoluteFile() + " создана");
        }
        openZip(path + "//" + zipFile, path + "//unzip");
        System.out.println(openProgress(file.getAbsolutePath() + saveFile1));

    }

    private static void deleteFilesAfterZip(String path, String nameZip) {
        File dir = new File(path);
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(path + "//" + nameZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                if (dir.isDirectory()) {
                    for (File item : dir.listFiles()) {
                        System.out.println(item.getName());
                        if (!item.getName().equals(name) & item.isFile())
                            item.delete();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String pathAndFile, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathAndFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String dirWithZip, List<String> dirZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(dirWithZip))) {
            for (String path : dirZip) {
                File file = new File(path);
                String fileName = file.getName();
                FileInputStream fis = new FileInputStream(path);
                ZipEntry entry = new ZipEntry(fileName);
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
            }
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void openZip(String from, String to) {
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(from))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                // распаковка
                FileOutputStream fout = new FileOutputStream(to + "//" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String loadPath) {

        try (FileInputStream fis = new FileInputStream(loadPath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            GameProgress gameProgress = (GameProgress) ois.readObject();
            return gameProgress;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
