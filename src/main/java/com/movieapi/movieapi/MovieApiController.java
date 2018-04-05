package com.movieapi.movieapi;

import java.text.MessageFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/movieApi")
public class MovieApiController {

  private static final int ONE = 1;
  private MovieService testMovieService;

  private Map<String, Integer> commonActorCount;

  @Autowired
  public MovieApiController(MovieService testMovieService) {
    this.testMovieService = testMovieService;
    this.commonActorCount = new HashMap<>();
  }


  /**
   * @param dateGreaterThan Date greater than required for movie api lookup
   * @param dateLessThan    Date less than required for movie api lookup
   * @return Intersection
   */
  @RequestMapping(value = "/getActorsInTvAndMovie", method = RequestMethod.GET)
  public ResponseEntity<String> getIntersectionForGivenDates(@RequestParam String dateGreaterThan,
                                                             @RequestParam String dateLessThan) {

    String key = dateGreaterThan + "," + dateLessThan;

    if (commonActorCount.containsKey(key)) {
      return ResponseEntity.ok()
          .body(returnResponseMessage(commonActorCount.get(key), dateGreaterThan, dateLessThan));
    } else {
      int commonCountVal = resolveRequest(dateGreaterThan, dateLessThan);
      commonActorCount.put(key, commonCountVal);
      return ResponseEntity.ok()
          .body(returnResponseMessage(commonCountVal, dateGreaterThan, dateLessThan));
    }
  }

  private String returnResponseMessage(int intersectionValue,
                                       String dateGreaterThan,
                                       String dateLessThan) {
    return MessageFormat.format(
        "The number actors and actresses were in at least one movie and at least one tv episode between "
            + "{0} and " + "{1} " + "are: {2}",
        dateGreaterThan,
        dateLessThan,
        intersectionValue);
  }

  private int resolveRequest(String dateGreaterThan, String dateLessThan) {
    List<Integer> movieIds = new ArrayList<>();
    List<Integer> tvIds = new ArrayList<>();
    Integer currentMovieCount = 1;
    Integer currentTvCount = 1;
    int totalPageCountForMovies = testMovieService.getTotalMoviePageCount(ONE,
                                                                          dateGreaterThan,
                                                                          dateLessThan);
    int totalPageCountForTv = testMovieService.getTotalTvPageCount(ONE,
                                                                   dateGreaterThan,
                                                                   dateLessThan);
    while (currentMovieCount <= totalPageCountForMovies) {
      movieIds.addAll(testMovieService.getMovieIds(currentMovieCount,
                                                   dateGreaterThan,
                                                   dateLessThan));
        currentMovieCount++;
    }
    while (currentTvCount <= totalPageCountForTv) {
      tvIds.addAll(testMovieService.getTvIds(currentTvCount, dateGreaterThan, dateLessThan));
      currentTvCount++;
    }
    //System.out.println("Total size of tv id List is: " + tvIds.size());

    Set<Integer> castSet = testMovieService.createMovieCastSet(movieIds); //set of ids for actors/actresses in movies for given dates
    //System.out.println("Movie Cast set size is: " + castSet.size());s

    Set<Integer> castTvSet = testMovieService.createTvCastSet(tvIds); //set of ids for actors/actresses in tvs for given dates

    //System.out.println("TV Cast set size is: " + castTvSet.size());

    Set<Integer> intersection = new HashSet<>(castSet);
    intersection.retainAll(castTvSet); //finds actors/actresses that have been in both at least one tv AND movie in given dates
    //System.out.println("Intersection size is: " + intersection.size());
    return intersection.size();
  }
}
