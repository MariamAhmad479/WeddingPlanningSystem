package service.document;

public class WordRenderer implements DocumentRenderer {
    @Override
    public String renderHeader(String title) {
        return "=== WORD Document ===\nTitle: " + title;
    }

    @Override
    public String renderContent(String content) {
        return content;
    }

    @Override
    public String renderFooter() {
        return "=== End of WORD Document ===";
    }
}
