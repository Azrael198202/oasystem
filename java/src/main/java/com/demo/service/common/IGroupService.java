package com.demo.service.common;

import com.demo.common.ServerResponse;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.group.GroupInfo;
import com.demo.pojo.notice.NoticeListDTO;
import com.demo.pojo.notice.NoticeRpnDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IGroupService {
    /**
     * group情報取得
     *
     * @param companyCode
     * @return
     */
    ServerResponse<List<GroupDTO>> getGroup(String companyCode);

    /**
     * グループ登録
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> create(GroupInfo groupInfo, HttpServletRequest request, HttpServletResponse response);

    /**
     * グループ削除
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<String> delete(GroupDTO groupDTO,HttpServletRequest request, HttpServletResponse response);
    /**
     * group情報取得
     *
     * @param request
     * @param response
     * @return
     */
    ServerResponse<List<GroupDTO>> getDetails(GroupDTO groupDTO,HttpServletRequest request, HttpServletResponse response);
}
