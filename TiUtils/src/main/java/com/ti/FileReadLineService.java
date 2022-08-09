package com.ti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileReadLineService {

    private final BlockingQueue<List<Integer>> queue = new LinkedBlockingQueue<>();

//    String fileName = "c://lines.txt";
    List<List<String>> list = new ArrayList<>();
    List<List< Integer>> intList = new ArrayList<>();
    private boolean skipHead = true;
    private String splitChar = ";";

    public void read(Path filePath){
        try(Stream<String> stream = Files.lines(filePath)){
            //1. filter line 3
            //2. convert all content to upper case
            //3. convert it into a List
            list = stream
                    .skip(skipHead ? 1 : 0)
                    .map(s -> Arrays.asList( s.split(splitChar)))
                    .collect(Collectors.toList());

        } catch(IOException e){
            e.printStackTrace();
        }

//        list.forEach(list -> System.out.println(list.toString()));
    }


    public void setSkipHead(boolean skipHead) {
        this.skipHead = skipHead;
    }

    public void setParameters(boolean skipHead, String splitChar){
        this.skipHead = skipHead;
        this.splitChar = splitChar;

    }

    public List<List<Integer>> getDataAsInteger(){
        intList =  list.stream().map(l -> l.stream().map(e -> Integer.valueOf(String.valueOf(e))).collect(Collectors.toList())).collect(Collectors.toList());
        return intList;
    }

    public static <T> List<List<T>> transpose(List<List<T>> list) {
        final int N = list.stream().mapToInt(l -> l.size()).max().orElse(-1);
        List<Iterator<T>> iterList = list.stream().map(it->it.iterator()).collect(Collectors.toList());
        return IntStream.range(0, N)
                .mapToObj(n -> iterList.stream()
                        .filter(it -> it.hasNext())
                        .map(m -> m.next())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public long sendCount = 0;

    public void circleDataProducer(long milliseconds){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(()->{
            int i = (int) (sendCount % intList.size());
            List<Integer> element = intList.get(i);
//            System.out.println(element.toString());
            queue.add(element);
            sendCount++;
        }, 0L, milliseconds, TimeUnit.MILLISECONDS);
    }

    public BlockingQueue<List<Integer>> getQueue(){
        return queue;
    }
}
