import java.util.List;
import java.util.stream.Collectors;

public class MultiplyByScaleNumListConverter implements NumListConverter<Double, Double>{

    private int scale;

    public MultiplyByScaleNumListConverter(int scale) {
        this.scale = scale;
    }

    @Override
    public List<Double> convert(List<Double> initialList) {
        return initialList.stream().map(entry -> entry * scale).collect(Collectors.toList());
    }

}
