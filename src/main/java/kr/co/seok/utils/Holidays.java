package kr.co.seok.utils;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Holidays implements Comparable<Holidays> {

    private String year; // 연도
    private String date; // 월일
    private String name; // 휴일 명칭

    @Override
    public int compareTo(Holidays o) {
        return this.date.compareTo(o.date);
    }
}
