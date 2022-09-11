package com.mate.cybermate.DTO.AcceptHistory;


import com.mate.cybermate.domain.AcceptHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptHistoryDTO {


    private String memberName;

    private Long age;

    private String sex;

    public AcceptHistoryDTO(AcceptHistory acceptHistory){

        this.memberName=acceptHistory.getStudyRoomApply().getMember().getNickName();
        this.age= acceptHistory.getAge();
        this.sex=acceptHistory.getSex();
    }
}
