import java.util.List;

/**
 * Interface for converting list of numbers to another list of numbers.
 * Number types of initial and converted lists may not be the same.
 * @param <T> - number type of initial list entries
 * @param <C> - number type of converted list entries
 */

@FunctionalInterface
public interface NumListConverter<T extends Number, C extends Number> {


    /**
     * Converts list of numbers to another list of numbers
     * @param initialList - list to convert with entries of type T.
     * @return converted list of numbers of type C.
     */
    List<C> convert(List<T> initialList);

}
