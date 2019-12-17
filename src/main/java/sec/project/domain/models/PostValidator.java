package sec.project.domain.models;

import javax.validation.constraints.NotEmpty;

public class PostValidator {
    @NotEmpty
    private String label;

    @NotEmpty
    private String content;

    public PostValidator(String label, String content) {
        this.label = label;
        this.content = content;
    }

    public PostValidator() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
