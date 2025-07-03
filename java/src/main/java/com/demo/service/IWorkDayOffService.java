package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.workDayOff.GetWorkDayOffInfoDTO;
import com.demo.pojo.workDayOff.WorkDayOffDTO;
import com.demo.pojo.workDayOff.WorkDayOffResultDTO;
import com.demo.pojo.workDayOff.WorkDayOffSearchDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IWorkDayOffService {
    ServerResponse<String> insertWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<List<WorkDayOffDTO>> selectWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response);
    ServerResponse<String> deleteWorkDayOff(WorkDayOffDTO workDayOffDTO, HttpServletRequest request, HttpServletResponse response);
    //　　代休残入力画面：休日出勤テーブルの申請番号取得
    ServerResponse<WorkDayOffResultDTO> selectRequestNumberWorkDayOff(WorkDayOffSearchDTO workDayOffSearchDTO, HttpServletRequest request, HttpServletResponse response);
    //　　休日出勤データ取得
    ServerResponse<List<GetWorkDayOffInfoDTO>> getWorkDayOffInfo(GetWorkDayOffInfoDTO getWorkDayOffINfoDTO, HttpServletRequest request, HttpServletResponse response);
}
