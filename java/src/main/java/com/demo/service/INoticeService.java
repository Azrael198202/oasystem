package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.pojo.notice.NoticeListDTO;
import com.demo.pojo.notice.NoticeRpnDTO;
import com.demo.pojo.notice.NoticeAllListDTO;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface INoticeService {
    /**
     * お知らせ情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<NoticeRpnDTO>> getNoticeList(NoticeListDTO noticeListDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<NoticeRpnDTO> getNoticeById(NoticeListDTO noticeListDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<List<NoticeAllListDTO>> getNoticeAllList(NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> deleteNotice(NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> insertNotice(NoticeAllListDTO  noticeAllListDTO, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> updateNotice(NoticeAllListDTO  noticeAllListDTO, HttpServletRequest request, HttpServletResponse response);
}
