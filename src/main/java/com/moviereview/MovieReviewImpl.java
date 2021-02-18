package com.moviereview;

import com.moviereview.model.MovieDetail;
import com.moviereview.model.MovieReviewDetail;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

@Service
public class MovieReviewImpl {

    private HashMap<String, MovieReviewDetail> reviewDB = new HashMap<>();

    private Logger log = LoggerFactory.getLogger(MovieReviewImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private String movieInfoServiceKey="movie-info-service";

    void populateMovieReview()
    {
        if(reviewDB.size() == 0) {
            //String Uri = String.format("http://localhost:%s/details", port);
            String Uri = String.format("http://%s/details",movieInfoServiceKey);
            log.info("Sreejith - URI = {}", Uri);
            try {
                MovieDetail[] movies = restTemplate.getForObject(Uri, MovieDetail[].class);
                List<MovieDetail> movie = Arrays.asList(movies);
                float count = 0.0f;
                for (MovieDetail m : movie) {
                    count++;
                    reviewDB.put(m.getId(), new MovieReviewDetail(m.getId(), count + 0.667f));
                }
            } catch (RestClientException rex) {
                log.error("Rest exception occured while getting movie details: " + rex.getCause());
            }
        }
        else
        {
            log.info("Sreejith - DB already exists. Nothing to be done");
        }

    }

    public Float getReview(String movieId) throws Exception {
        Optional<MovieReviewDetail> movie = Optional.ofNullable(reviewDB.get(movieId));
        if(movie.isPresent())
        {
            return movie.get().getReview();
        }
        else
        {
            throw new Exception("Movie ID not found");
        }
    }


   public  List getAllReviews()
    {
        if(reviewDB.size() <= 0)
        {
            populateMovieReview();
        }
        return Arrays.asList(reviewDB.values().toArray());
    }

}
