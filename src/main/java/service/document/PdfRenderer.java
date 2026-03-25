package service.document;

public class PdfRenderer implements DocumentRenderer {
    @Override
    public String renderHeader(String title) {
        return "[PDF Header] Title: " + title;
    }

    @Override
    public String renderContent(String content) {
        return "[PDF Content] " + content;
    }

    @Override
    public String renderFooter() {
        return "[PDF Footer] Page 1 of 1";
    }
}
