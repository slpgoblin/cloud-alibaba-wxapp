package com.goblin.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * @Author: goblin
 * @DATE: Created in 2020/3/30 22:30
 * @Description:
 * @Version:
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
