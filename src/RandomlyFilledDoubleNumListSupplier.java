import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomlyFilledDoubleNumListSupplier implements NumListSupplier<Double>{

    @Override
    public List<Double> supplyNumList(int listSize) {
        return Arrays.stream((new Random()).doubles(listSize).toArray()).boxed().collect(Collectors.toList());
    }
}
