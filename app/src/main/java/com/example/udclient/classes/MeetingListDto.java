package com.example.udclient.classes;

import java.io.Serializable;
import java.util.List;

public class MeetingListDto implements Serializable {
    private List<MeetingDto> meetingDtoList;

    public MeetingListDto() {
    }

    public MeetingListDto(List<MeetingDto> meetingDtoList) {
        this.meetingDtoList = meetingDtoList;
    }

    public List<MeetingDto> getMeetingDtoList() {
        return meetingDtoList;
    }

    public void setMeetingDtoList(List<MeetingDto> meetingDtoList) {
        this.meetingDtoList = meetingDtoList;
    }
}
