package com.demo.controller.group;

import com.demo.common.Const;
import com.demo.common.ServerResponse;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.group.GroupInfo;
import com.demo.service.common.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/*
 * Author  : libin
   Time    : 2021/08/06
   Function:
*/
@RestController
@RequestMapping("/group/")
public class GroupController {

    @Autowired
    IGroupService iGroupService;


    /**
     * /group/getGroup.do group情報取得
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/getGroup.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GroupDTO>> getGroup(@RequestBody Map<String, String> params){
        // 会社コード
        String companyCode = params.get("companyCode");
        return iGroupService.getGroup(companyCode);
    }

    /**
     * /group/create.do グループ登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> create(@RequestBody GroupInfo groupInfo, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {


        return iGroupService.create(groupInfo, request, response);
    }

    /**
     * /group/delete.do グループ削除
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delete(@RequestBody GroupDTO groupDTO, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        return iGroupService.delete(groupDTO,request, response);
    }

    /**
     * /group/getDetails.do group情報取得
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDetails.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<GroupDTO>> getDetails(@RequestBody GroupDTO groupDTO, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {

        return iGroupService.getDetails(groupDTO,request,response);
    }

}
