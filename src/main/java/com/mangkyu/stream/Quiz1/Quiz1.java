package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();

        
        return csvLines.stream()
                    .map(
                        row -> row[1].split(":")
                    )
                    .flatMap(
                        row -> Arrays.stream(row)
                    )
                    .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> oldValue += newValue));

    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                    .filter(
                        row -> row[0].startsWith("정")
                    )
                    .map(row -> row[1].split(":"))
                    .flatMap(
                        hobbies -> Arrays.stream(hobbies)
                    )
                    .map( elem -> elem.trim())
                    .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> oldValue + 1));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                    .mapToInt(
                        row -> countString(row[2], 0)
                    )
                    .reduce(0, Integer::sum);

    }

    private int countString(String src, int index) {

        int newIndex = src.indexOf("좋아", index);

        if(newIndex >= 0) {

            return 1 + countString(src, newIndex+2);
        }

        return 0;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
