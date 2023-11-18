package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class RateableResource extends ResourceDecorator {
    public RateableResource(StudentResource decoratedResource) {
        super(decoratedResource);
    }

    private double rating;

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public StudentResource getDecoratedResource() {
        return super.getDecoratedResource();
    }
}
