package com.moviereview.model;

public class MovieReviewDetail {
    public String getId() {
        return id;
    }

    public MovieReviewDetail()
    {

    }

    public MovieReviewDetail(String id, Float rating)
    {
        this.id = id;
        this.review = rating;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Float getReview() {
        return review;
    }

    public void setReview(Float review) {
        this.review = review;
    }

    String id;
    Float review;
}
