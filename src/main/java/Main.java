import rss.utils.RssParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RssParser rp = new RssParser("https://blog.koo.gg/rss");

        try {
            File mainPath = new File("Main.md");
            File readMePath = new File("ReadMe.md");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(mainPath.getAbsolutePath()), "utf-8")
            );

            String temp = "";

            List<String> str = new ArrayList<>();
            while ((temp = reader.readLine()) != null) {
                str.add(temp);
            }

            str.add(rp.getRssFeed());

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(readMePath), "utf-8"));

            for (String s : str) {
                bw.write(s + "\n");
            }

            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
