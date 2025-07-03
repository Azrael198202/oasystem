package com.demo.controller.user;

import com.demo.common.Const;
import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.MenuMapper;
import com.demo.dao.UserDetailMapper;
import com.demo.dao.UserMasterMapper;
import com.demo.entity.UserDetail;
import com.demo.entity.UserMaster;
import com.demo.pojo.common.AvatarDTO;
import com.demo.pojo.common.LoginUserInfo;
import com.demo.pojo.common.MenuInfo;
import com.demo.pojo.user.*;
import com.demo.service.IUserService;
import com.demo.util.CommonUtil;
import com.demo.util.CreateFolderUtil;
import com.demo.util.JwtUtil;
import com.demo.util.ShiroMD5Util;
import com.demo.util.base64ConvertUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function:
*/
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    IUserService iUserService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserMasterMapper userMasterMapper;

    @Autowired
    UserDetailMapper userDetailMapper;

    /**
     * パスワードを忘れた
     * メール送信
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "findPwdMail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> findPwdMail(@RequestBody Map<String, String> params) {

        String userId = params.get("userId");
        String companyCode = params.get("companyCode");

        // mail 存在チェック
        UserMaster userMaster = iUserService.getMailByUser(companyCode, userId);

        if(userMaster == null) {
            // ユーザー存在していないの場合、
            return ServerResponse.createByBizError("入力された社員番号に該当するユーザーは存在しませんでした。");
        } else {
            // メール送信
            return iUserService.findPwdMail(userMaster);
        }

    }
    /**
     * 申請 20211011
     * メール送信
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "reqMail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> reqMail(@RequestBody UsermailDTO params) {

        return iUserService.reqMail(params);
    }
    /**
     * パスワードを忘れた
     * パスワード再設定
     *
     * @param resetPwdDTO
     * @return
     */
    @RequestMapping(value = "findPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> findPwd(@RequestBody ResetPwdDTO resetPwdDTO) {

        String token = resetPwdDTO.getToken();

        if(JwtUtil.verify(token, Const.TOKEN_SECRET)) {
            String userid = JwtUtil.getClaim(token,Const.TOKEN_CLAIM_USERID);
            String companyCode = JwtUtil.getClaim(token,Const.COMPANY_CODE);

            return iUserService.updatePwd(userid,companyCode,resetPwdDTO);
        } else {
            return ServerResponse.createByBizError("入力されたメールアドレスに該当するユーザーは存在しませんでした。");
        }
    }

    /**
     * 個人イメージアップロード
     * 個人イメージアップロード
     *
     * @param resetPwdDTO
     * @return
     */
    @RequestMapping(value = "upload-avatar.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> UpdateAvatar(
        @RequestParam("userId") String userId,
        @RequestParam("companyCode") String companyCode,
        @RequestParam("image") MultipartFile image) {

        String currentDirectory = ConstCode.IMAGE_RELATION.CURRENT_DIRECTORY;
        String localDomain = ConstCode.IMAGE_RELATION.DOMAIN_URL;
        String imgTemporary = ConstCode.IMAGE_RELATION.IMG_TEMPORARY_TRANSPORT;

        Path p = Paths.get(currentDirectory, imgTemporary ,userId);
        CreateFolderUtil.CreateFolder(p);
        String filePathName ="";

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = userId + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + image.getOriginalFilename();

                filePathName = Paths.get(p.toString(),fileName).toString();

                Path fullPath = p.resolve(fileName);
                image.transferTo(fullPath); 

                filePathName = fullPath.toString()
                    .replace("\\", "/")
                    .replace(currentDirectory, localDomain);

                iUserService.setImageUrl(userId,companyCode,filePathName);  
                return ServerResponse.createBySuccess(filePathName);    
            }
            catch (IOException e) {
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("画像の保存に失敗しました");
            }
        } 

        return ServerResponse.createByErrorMessage("画像ファイルが存在しません");
        
    }

    /**
     * パスワードリセット
     *
     * @param resetPwdDTO
     * @return
     */
    @RequestMapping(value = "resetPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPwd(HttpServletRequest request, @RequestBody ResetPwdDTO resetPwdDTO) {

        //TODO Tokenから
        LoginUserInfo loginUserInfo = commonUtil.getUserInfoByToken(request);
        String companyCode = loginUserInfo.getCompanyCode();
        String userId = loginUserInfo.getUserId();

        UserMaster  userMaster = iUserService.getUserByKey(companyCode,userId);

        userMaster.getPassWord();

        String oldPwd = resetPwdDTO.getOldPwd();

        // MD5密码加密 salt为userId
        String md5PassWord = ShiroMD5Util.encryptPassword(oldPwd, companyCode + userId);

        if(userMaster.getPassWord().equals(md5PassWord)) {
            return  iUserService.updatePwd(userId,companyCode,resetPwdDTO);
        } else {
            return ServerResponse.createByBizError("現在パスワードが間違いです。");
        }
    }

    /**
     * ユーザー情報更新
     *
     * @param request
     * @param userDetailInfo
     * @return
     */
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> userUpdate(HttpServletRequest request, @RequestBody UserDetailInfo userDetailInfo) {

        LoginUserInfo loginUserInfo = commonUtil.getUserInfoByToken(request);

        // TODO
       return iUserService.userUpdate(loginUserInfo,userDetailInfo);
    }

    /**
     * ユーザー情報取得
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "getInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> getUserDetailInfo(@RequestBody Map<String, String> params) {

        String companyCode = params.get("companyCode");
        String userId = params.get("userId");

        //TODO　引数チェック

        //
        return iUserService.getUserDetailInfo(companyCode,userId);
    }

    /**
     * ユーザー情報取得2
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "getUserDetail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<UserDetailInfo>> getUserDetail(@RequestBody Map<String, String> params) {

        String companyCode = params.get("companyCode");
        String userId = "%" + params.get("userId") + "%";

        return iUserService.getUserDetail(companyCode,userId);
    }
    /**
     * ユーザー権限取得
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "getUserPermission.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<MenuInfo>> getUserPermission(@RequestBody Map<String, String> params) {

        String companyCode = params.get("companyCode");
        String permissionKbn = params.get("permissionKbn");

        return ServerResponse.createBySuccess(menuMapper.selectByKbn(companyCode, permissionKbn));
    }

//    /**
//     * ユーザー権限取得
//     *
//     * @param params
//     * @return
//     */
//    @RequestMapping(value = "getUserPermissionById.do", method = RequestMethod.POST)
//    @ResponseBody
//    public ServerResponse<MenuInfo> getUserPermissionById(@RequestBody Map<String, String> params) {
//
//        String companyCode = params.get("companyCode");
//        String permissionKbn = params.get("permissionKbn");
//        String menuNumber = params.get("menuNumber");
//
//        return ServerResponse.createBySuccess(menuMapper.selectById(companyCode, permissionKbn, menuNumber));
//    }
    /**
     * 全員パスワード更新
     *
     * @param passWord
     * @return
     */
    @RequestMapping(value = "resetPassWordAllUser.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassWordAllUser(@RequestBody String passWord) {

        // ユーザー情報リスト取得
        List<UserMaster> userMasterList = userMasterMapper.getAllUserByCompanyCode("000001");
        if (userMasterList != null) {
            Date date = new Date();
            for (int i = 0; i < userMasterList.size(); i++) {
                String companyCode = userMasterList.get(i).getCompanyCode();
                String userId = userMasterList.get(i).getUserId();
                String userKey = companyCode + userId;
                // パスワード暗号化
                String md5PassWord = ShiroMD5Util.encryptPassword(passWord, userKey);
                // パスワード更新
                userMasterMapper.updatePassward(companyCode,userId,md5PassWord,date,"999999");
            }
            return ServerResponse.createBySuccessMessage("パスワード更新しました");
        } else {
            return ServerResponse.createByErrorMessage("ユーザーなし");
        }
    }
    /**
     * 残有給数取得
     *
     *  @param userRemainHolidayDTO
     *  @param request
     *  @param response
     * @return
     */
    @RequestMapping(value = "UserRemainHolidayList.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<UserRemainHolidayDTO>>UserRemainHolidayList(@RequestBody UserRemainHolidayDTO userRemainHolidayDTO, HttpServletRequest request,
                                                               HttpServletResponse response){
        return iUserService.getUserRemainHolidayList(userRemainHolidayDTO,request,response);
    }
    //    権限付与画面：検索押下時の処理
    @RequestMapping(value = "/research.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<UserDTO>> research(@RequestBody UserDTO userDTO,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        return iUserService.research(userDTO,request, response);
    }
    //    権限付与画面：更新押下時の処理(利用者マスタ（基本情報）のデータを更新)
    @RequestMapping(value = "/authorityCreate.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> authorityCreate(@RequestBody UserDTO userDTO,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        return iUserService.authorityCreate(userDTO,request, response);
    }
}