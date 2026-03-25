package service.document;

public interface DocumentRenderer {
    String renderHeader(String title);
    String renderContent(String content);
    String renderFooter();
}
