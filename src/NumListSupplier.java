import java.util.List;

/**
 * Class for supplying list of numbers of type T.
 *
 * @param <T> - type of list entries.
 */
@FunctionalInterface
public interface NumListSupplier<T extends Number> {

    /**
     * Supplies new list of numeric entries of type T.
     * @param listSize - size of supplied list.
     * @return new list with numeric entries of type T.
     */
    List<T> supplyNumList(int listSize);

}
