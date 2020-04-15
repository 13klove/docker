package com.gdev.boot.util.sms;

import lombok.Data;

@Data
public class GameSchedule {

    private Long id;
    private String team1_id;
    private String team2_id;
    private String startDate;
    private String endDate;

    public static GameSchedule of( Long id, String startDate, String endDate, String team1_id, String team2_id ){
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(id);
        gameSchedule.setStartDate(startDate);
        gameSchedule.setEndDate(endDate);
        gameSchedule.setTeam1_id(team1_id);
        gameSchedule.setTeam2_id(team2_id);
        return gameSchedule;
    }

}
