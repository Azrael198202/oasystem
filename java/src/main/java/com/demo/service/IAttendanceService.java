package com.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.common.ServerResponse;
import com.demo.entity.AtndStting;
import com.demo.pojo.atndApply.AtndApplyRpnDTO;
import com.demo.pojo.atndApply.AtndApplySearchDTO;
import com.demo.pojo.atndStting.AtndSttingDTO;
import com.demo.pojo.atndStting.AtndSttingRpnDTO;
import com.demo.pojo.attendance.ApplyAttendanceDTO;
import com.demo.pojo.attendance.AttendanceDTO;
import com.demo.pojo.attendance.AttendanceRPnDTO;
import com.demo.pojo.attendance.*;
import com.demo.pojo.dayCheckHistory.DayCheckHistoryDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface IAttendanceService {
    /**
     * 勤務表詳細情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<AttendanceRPnDTO>> getDetail(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 勤務表承認済み申請情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<ApplyAttendanceDTO>> selectByApply(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);
    /**
     * 勤務表回覧中申請情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<ApplyAttendanceDTO>> selectByApplyActivity(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response) ;
    /**
     * 勤務表検索
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<AttendanceRPnDTO>> search(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 勤務表登録　保存・申請
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> create(List<AttendanceRPnDTO> attendanceRPnDTOList, HttpServletRequest request, HttpServletResponse response);
    /**
     * 勤務表登録　更新
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> attendanceCreateUpdate(List<AttendanceRPnDTO> attendanceRPnDTOList, HttpServletRequest request, HttpServletResponse response);
    /**
     * 勤務表　上司承認
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> aprove(AttendanceRPnDTO attendanceRPnDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 出勤登録
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> workStart(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 退勤登録
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> workEnd(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 出勤登録
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> workStartSetting(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 退勤登録
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> workEndSetting(AttendanceDTO attendanceDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 勤怠設定情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<AtndSttingRpnDTO>> getAtndStting(AtndSttingDTO atndSttingDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 月末締一括出力(検索押下時の処理
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<BulkExportDTO>> searchBulkExport(BulkExportDTO bulkExportDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 勤怠表ファイルストリームを作成（Excel一括エクスポート）
     * @return
     */
    AttendanceExcelFileDTO createAttendanceFileStream(AttendanceDTO attendanceDTO);

    /**
     * 勤怠表PDFストリームを作成（PDF出力）
     * @return
     */
    Map<String, Object> outPutAttendancePdfStream(BulkExportDTO bulkExportDTO);
}
