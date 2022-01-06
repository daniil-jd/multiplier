package ru.example.common.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsCalculateResponseDto {
    private LinkedList<Info> info = new LinkedList<>();
    private LinkedList<Long> costs = new LinkedList<>();
    private LinkedList<Timestamp> dates = new LinkedList<>();
}
