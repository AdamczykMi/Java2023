package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class CommentableResource extends ResourceDecorator {
    public CommentableResource(StudentResource decoratedResource) {
        super(decoratedResource);
    }

    private String comment;

    public void addComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public StudentResource getDecoratedResource() {
        return super.getDecoratedResource();
    }

}
