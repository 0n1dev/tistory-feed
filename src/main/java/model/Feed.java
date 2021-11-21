package rss.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feed {
    private String title;
    private String link;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        Pattern p = Pattern.compile("(\\S+ \\S+ \\S+) [0-9]{4}");
        Matcher m = p.matcher(date);

        if (m.find()) {
            this.date = m.group(1);
        } else {
            this.date = "";
        }
    }

    public String getHtml() {
        return String.format("[%s](%s) - %s <br/>\n", title, link, date);
    }

    @Override
    public String toString() {
        return "Feed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
