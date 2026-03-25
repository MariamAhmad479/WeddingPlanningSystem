package service.document;

public abstract class Document {
    protected DocumentRenderer renderer;

    public Document(DocumentRenderer renderer) {
        this.renderer = renderer;
    }

    public abstract void exportDocument();
}
