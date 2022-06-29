import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class AsynchronousCalculations {

    private static final int NUM_LIST_SIZE = 10000;

    public static void main(String[] args) {
        NumListSupplier integerNumListSupplier = new RandomlyFilledIntegerNumListSupplier();
        NumListSupplier doubleNumListSupplier = new RandomlyFilledDoubleNumListSupplier();
        NumListConverter<Double, Double> multiplyByFiveConverter = new MultiplyByScaleNumListConverter(5);
        NumListConverter<Integer, Double> removeOddEntriesConverter = new RemoveOddEntriesNumListConverter();
        NumListConverter<Double, Double> narrowToInRangeEntriesConverter = new NarrowListToInRangeEntriesConverter(0.4, 0.6);

        List<Double> firstTestList = List.of(3.0, 5.0, 4.2, 4.7, 8.1, 9.3, 1.3, 9.6, 7.5, 8.4);
        List<Integer> secondTestList = List.of(1, 5, 8, 6, 4, 5, 3, 2, 7, 2);
        List<Double> thirdTestList = List.of(5.8, 4.2, 0.4, 0.9, 0.5, 0.6, 0.3, 8.5, 2.3, 4.2);
        CompletableFuture<List<Double>> future1, future2, future3, resultFuture;
        try {
            future1 = CompletableFuture.supplyAsync(() -> firstTestList)
                    .thenApplyAsync(list -> multiplyByFiveConverter.convert(list))
                    .thenApplyAsync(list ->
                    {
                        Collections.sort(list);
                        return list;
                    });
            future2 = CompletableFuture.supplyAsync(() -> secondTestList)
                    .thenApplyAsync(list -> removeOddEntriesConverter.convert(list))
                    .thenApplyAsync(list ->
                    {
                        Collections.sort(list);
                        return list;
                    });
            future3 = CompletableFuture.supplyAsync(() -> thirdTestList)
                    .thenApplyAsync(list -> narrowToInRangeEntriesConverter.convert(list))
                    .thenApplyAsync(list ->
                    {
                        Collections.sort(list);
                        return list;
                    });
            resultFuture = Stream.of(future1, future2, future3).reduce((f1, f2) -> f1.thenCombine(f2,
                    (list1, list2) -> {
                        List<Double> resultList = new ArrayList<>(list1);
                        resultList.addAll(list2);
                        return resultList;
                    })).get();
            System.out.println("Result list is " + resultFuture.get());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Double> firstList = doubleNumListSupplier.supplyNumList(NUM_LIST_SIZE);
        List<Integer> secondList = integerNumListSupplier.supplyNumList(NUM_LIST_SIZE);
        List<Double> thirdList = doubleNumListSupplier.supplyNumList(NUM_LIST_SIZE);
        System.out.println("The first list is " + firstList);
        System.out.println("The second list is " + secondList);
        System.out.println("The third list is " + thirdList);

        long time;

        time = System.currentTimeMillis();
        future1 = CompletableFuture.supplyAsync(() -> firstList)
                .thenApply(list -> multiplyByFiveConverter.convert(list))
                .thenApply(list ->
                {
                    Collections.sort(list);
                    return list;
                });
        future2 = CompletableFuture.supplyAsync(() -> secondList)
                .thenApply(list -> removeOddEntriesConverter.convert(list))
                .thenApply(list ->
                {
                    Collections.sort(list);
                    return list;
                });
        future3 = CompletableFuture.supplyAsync(() -> thirdList)
                .thenApply(list -> narrowToInRangeEntriesConverter.convert(list))
                .thenApply(list ->
                {
                    Collections.sort(list);
                    return list;
                });
        resultFuture = Stream.of(future1, future2, future3).reduce((f1, f2) -> f1.thenCombine(f2,
                (list1, list2) -> {
                    List<Double> resultList = new ArrayList<>(list1);
                    resultList.addAll(list2);
                    return resultList;
                })).get();
        time = System.currentTimeMillis() - time;
        //System.out.println("Result list is " + resultFuture.get());
        System.out.println("Time spent " + time);

        ExecutorService convertingExService = Executors.newFixedThreadPool(3);
        ExecutorService sortingExService = Executors.newFixedThreadPool(3);

        time = System.currentTimeMillis();
        future1 = CompletableFuture.supplyAsync(() -> firstList)
                .thenApplyAsync(list -> multiplyByFiveConverter.convert(list), convertingExService)
                .thenApplyAsync(list ->
                        {
                            Collections.sort(list);
                            return list;
                        },
                        sortingExService);
        future2 = CompletableFuture.supplyAsync(() -> secondList)
                .thenApplyAsync(list -> removeOddEntriesConverter.convert(list), convertingExService)
                .thenApplyAsync(list ->
                        {
                            Collections.sort(list);
                            return list;
                        },
                        sortingExService);
        future3 = CompletableFuture.supplyAsync(() -> thirdList)
                .thenApplyAsync(list -> narrowToInRangeEntriesConverter.convert(list), convertingExService)
                .thenApplyAsync(list ->
                        {
                            Collections.sort(list);
                            return list;
                        },
                        sortingExService);
        resultFuture = Stream.of(future1, future2, future3).reduce((f1, f2) -> f1.thenCombine(f2,
                (list1, list2) -> {
                    List<Double> resultList = new ArrayList<>(list1);
                    resultList.addAll(list2);
                    return resultList;
                })).get();
        time = System.currentTimeMillis() - time;
        //System.out.println("Result list is " + resultFuture.get());
        System.out.println("Time spent " + time);


    }


}
