package com.demo.controller.notice;

import com.demo.common.ServerResponse;
import com.demo.pojo.notice.NoticeAllListDTO;
import com.demo.pojo.notice.NoticeListDTO;
import com.demo.pojo.notice.NoticeRpnDTO;
import com.demo.service.INoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/notice/")
public class NoticeController {
    @Autowired
    INoticeService iNoticeService;

    @RequestMapping(value = "getNoticeAllList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<NoticeAllListDTO>> getNoticeAllList(@RequestBody NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response) {
        return iNoticeService.getNoticeAllList(noticeAllListDTO,request,response);
    }

    @RequestMapping(value = "deleteNotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteNotice(@RequestBody NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response) {
        return iNoticeService.deleteNotice(noticeAllListDTO,request,response);
    }

    @RequestMapping(value = "/insertNotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertNotice(@RequestBody NoticeAllListDTO  noticeAllListDTO,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws Exception {
        return iNoticeService.insertNotice(noticeAllListDTO, request, response);
    }

    @RequestMapping(value = "/updateNotice.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateNotice(@RequestBody NoticeAllListDTO  noticeAllListDTO,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        return iNoticeService.updateNotice(noticeAllListDTO, request, response);
    }

    @RequestMapping(value = "getNoticeById.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<NoticeRpnDTO> getNoticeById(@RequestBody NoticeListDTO noticeListDTO, HttpServletRequest request, HttpServletResponse response) {
        return iNoticeService.getNoticeById(noticeListDTO,request,response);
    }
}
