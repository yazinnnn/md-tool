import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        foo();
    }

    public static void foo() {
        String path = System.getProperty("user.dir");
        System.out.println("当前目录: " + path + ";");
        File file = new File(path);
        List<File> dirs = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory).collect(Collectors.toList());

        dirs.forEach(dir -> {
            String dirName = dir.getName();
            List<File> mds = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                    .filter(f -> f.getName().endsWith("md"))
                    .collect(Collectors.toList());
            if (mds.size() > 0) {
                System.out.println(dirName + " 目录下的md文件: " + mds);
                mds.forEach(md -> func(md, dirName));
                System.out.println(dirName + " 目录处理完成；");
            }
        });
    }

    private static void func(File md, String dir) {
        md.setWritable(true);
        int index = dir.lastIndexOf(File.separator);
        String tag = dir.substring(index + 1);
        try (FileWriter writer = new FileWriter(md, true)) {
            writer.append("\n").append("# ").append(tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
