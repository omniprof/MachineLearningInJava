package jsr381.demo.results;

/**
 * An instance of this record class is created to hold the results for each test
 * against the model
 *
 */
public record Result(String name, Float accuracy) {

}
