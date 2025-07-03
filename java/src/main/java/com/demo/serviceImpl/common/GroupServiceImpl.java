package com.demo.serviceImpl.common;

import com.demo.common.ServerResponse;
import com.demo.dao.GroupMapper;
import com.demo.dao.GroupMemberMapper;
import com.demo.entity.Group;
import com.demo.entity.GroupMember;
import com.demo.pojo.group.GroupDTO;
import com.demo.pojo.group.GroupInfo;
import com.demo.pojo.group.GroupMemberInfo;
import com.demo.service.common.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/*
 * Author  : libin
   Time    : 2021/07/12
   Function: 勤務表処理
*/
@Service
@Slf4j

public class GroupServiceImpl implements IGroupService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupMemberMapper groupMemberMapper;
    /**
     * group情報取得
     *
     * @param companyCode
     * @return
     */
    @Override
    public ServerResponse<List<GroupDTO>> getGroup(String companyCode) {

        List<GroupDTO> group = groupMapper.selectGroup(companyCode);

        return ServerResponse.createBySuccess(group);
    }

    /**
     * /group/create.do グループ登録
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ServerResponse<String> create(GroupInfo groupInfo, HttpServletRequest request, HttpServletResponse response) {

        int countName = groupMapper.selectName(groupInfo.companyCode, groupInfo.getGroupName());
        //　同じグループ名判断
        if(countName > 0){
            // status 602
            return ServerResponse.createByBizError("グループ名は存在しました。");
        }
        // グループテーブル登録処理
        Group group = new Group();
        // 属性copy
        BeanCopier groupCopier = BeanCopier.create(GroupInfo.class,
                Group.class, false);
        groupCopier.copy(groupInfo, group, null);
        // グループコードなし
        if (StringUtils.isEmpty(groupInfo.groupCode)){
            String groupCode = groupMapper.selectCode(groupInfo.companyCode);
            if(!StringUtils.isEmpty(groupCode)){
                groupCode = String.format("%0" + 6 + "d", Integer.parseInt(groupCode) + 1);
            } else {
                groupCode = "000001";
            }
            //　グループコード設定
            group.setGroupCode(groupCode);
        } else {
            // 削除
            groupMapper.deleteByPrimaryKey(groupInfo.companyCode, groupInfo.getGroupCode(), groupInfo.getGroupName());
        }

        // 登録日時
        group.setRegistrationDatetime(new Date());
        // 登録者
        group.setRegistrationUser(groupInfo.getUserId());
        // 更新日時
        group.setUpdateDatetime(new Date());
        // 更新者
        group.setUpdateUser(groupInfo.getUserId());

        //　登録処理を行う。
        groupMapper.insert(group);

        // グループメンバーテーブル登録処理
        groupMemberMapper.deleteByCode(groupInfo.companyCode, groupInfo.getGroupCode());
        if (groupInfo.getGroupMember() != null && groupInfo.getGroupMember().size() > 0){
            for (GroupMemberInfo groupMemberInfo: groupInfo.getGroupMember()) {
                GroupMember groupMember = new GroupMember();
                // 属性copy
                BeanCopier groupMemberCopier = BeanCopier.create(GroupMemberInfo.class,
                        GroupMember.class, false);
                groupMemberCopier.copy(groupMemberInfo, groupMember, null);

                //　グループコード設定
                groupMember.setGroupCode(group.getGroupCode());
                // 登録日時
                groupMember.setRegistrationDatetime(new Date());
                // 登録者
                groupMember.setRegistrationUser(groupInfo.getUserId());
                // 更新日時
                groupMember.setUpdateDatetime(new Date());
                // 更新者
                groupMember.setUpdateUser(groupInfo.getUserId());

                //　登録処理を行う
                groupMemberMapper.insert(groupMember);
            }
        }
        return ServerResponse.createBySuccess();
    }
    /**
     * グループ削除
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> delete(GroupDTO groupDTO,HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = groupDTO.getCompanyCode();
        // グループコード
        String groupCode = groupDTO.getGroupCode();
        // グループ名
        String groupName = groupDTO.getGroupName();
        // グループテーブルに該当グループを削除する
        int deleteGroup = groupMapper.deleteByPrimaryKey(companyCode,groupCode,groupName);
        // グループメンバーテーブルに該当グループメンバーを削除する
        int deleteGroupMember = groupMemberMapper.deleteByCode(companyCode,groupCode);

        return ServerResponse.createBySuccess();
    }
    /**
     * groupDetails情報取得
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ServerResponse<List<GroupDTO>> getDetails(GroupDTO groupDTO, HttpServletRequest request, HttpServletResponse response) {
        // 会社コード
        String companyCode = groupDTO.getCompanyCode();
        // グループコード
        String groupCode = groupDTO.getGroupCode();
        // グループ名
        String groupName = groupDTO.getGroupName();

        List<GroupDTO> groupDTOS = groupMapper.selectAll(companyCode,groupCode,groupName);

        return ServerResponse.createBySuccess(groupDTOS);
    }
}
