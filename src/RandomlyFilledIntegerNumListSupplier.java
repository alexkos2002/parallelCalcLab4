import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomlyFilledIntegerNumListSupplier implements NumListSupplier<Integer>{

    @Override
    public List<Integer> supplyNumList(int listSize) {
        return Arrays.stream((new Random()).ints(listSize).toArray()).boxed().collect(Collectors.toList());
    }
}
