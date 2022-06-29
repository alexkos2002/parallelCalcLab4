import java.util.List;
import java.util.stream.Collectors;

public class NarrowListToInRangeEntriesConverter implements NumListConverter<Double, Double>{

    private double min;
    private double max;

    public NarrowListToInRangeEntriesConverter(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public List<Double> convert(List<Double> initialList) {
        return initialList.stream().filter(entry -> entry >= min && entry <= max).collect(Collectors.toList());
    }
}
