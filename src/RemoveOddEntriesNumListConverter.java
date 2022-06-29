import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveOddEntriesNumListConverter implements NumListConverter<Integer, Double>{

    @Override
    public List<Double> convert(List<Integer> initialList) {
        List<Integer> convertedList = initialList.stream().filter(entry -> entry % 2 == 0).collect(Collectors.toList());
        List<Double> convertedListOfDoubles = new ArrayList<>();
        convertedList.forEach(entry -> convertedListOfDoubles.add((double) entry));
        return convertedListOfDoubles;
    }
}
