package rss.utils;

import rss.model.Feed;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RssParser {

    private static final String ITEM = "item";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DATE = "pubDate";

    private URL url;
    private List<Feed> feedList = new ArrayList<>();

    public RssParser(String rssUrl) {
        try {
            this.url = new URL(rssUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getRssFeed() {
        try {

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

            String title = "";
            String link = "";
            String date = "";

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();

                    switch (localPart) {
                        case TITLE:
                            title = getElementData(event, eventReader);
                            break;
                        case LINK:
                            link = getElementData(event, eventReader);
                            break;
                        case DATE:
                            date = getElementData(event, eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        Feed feed = new Feed();

                        feed.setTitle(title);
                        feed.setLink(link);
                        feed.setDate(date);

                        feedList.add(feed);

                        event = eventReader.nextEvent();
                        continue;
                    }
                }

            }

            return feedList.stream()
                    .map(Feed::getHtml)
                    .collect(Collectors.joining());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getElementData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();

        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }

        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
