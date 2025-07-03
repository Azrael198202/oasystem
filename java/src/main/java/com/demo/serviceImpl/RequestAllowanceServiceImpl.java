package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.RequestAllowanceMapper;
import com.demo.entity.RequestAllowance;
import com.demo.pojo.requestAllowance.GetApplyYmDTO;
import com.demo.pojo.requestAllowance.QualificationAllowanceDTO;
import com.demo.pojo.requestAllowance.RequestAllowanceDTO;
import com.demo.service.IRequestAllowanceService;

import com.demo.util.CreateFolderUtil;
import com.demo.util.base64ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class RequestAllowanceServiceImpl implements IRequestAllowanceService {
    @Autowired
    RequestAllowanceMapper requestAllowanceMapper;

    String accessMode = ConstCode.IMAGE_RELATION.ACCESS_MODE;
    String currentDirectory = ConstCode.IMAGE_RELATION.CURRENT_DIRECTORY;
    String localDomain = ConstCode.IMAGE_RELATION.DOMAIN_URL;
    String qualification = ConstCode.IMAGE_RELATION.IMG_DIRECTORY_QUALIFICATION;
    String congratulatory = ConstCode.IMAGE_RELATION.IMG_CONGRATULATORY_OR_CONDOLENCEPAYMENTS;

    // 手当申請：詳細処理
    @Override
    public ServerResponse<List<RequestAllowanceDTO>> getRequestAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response){

        List<RequestAllowanceDTO> requestAllowanceDTOList = requestAllowanceMapper.selectRequestAllowance(requestAllowanceDTO.companyCode, requestAllowanceDTO.requestNumber);
        if (!StringUtils.isEmpty(requestAllowanceDTOList.get(0).image)){
            try {
                requestAllowanceDTOList = doGet(requestAllowanceDTOList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  ServerResponse.createBySuccess(requestAllowanceDTOList);
    }

    public List<RequestAllowanceDTO> doGet(List<RequestAllowanceDTO> requestAllowanceDTOList) throws IOException {
        File file = new File(requestAllowanceDTOList.get(0).image);
        String contentType = Files.probeContentType(file.toPath());
        //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！
        String newPath = requestAllowanceDTOList.get(0).image.replace(ConstCode.STRING_DOUBLE_BACK_SLASH, ConstCode.STRING_SLASH)
                .replace(this.currentDirectory,this.localDomain);
        requestAllowanceDTOList.get(0).setImage(newPath);
        return requestAllowanceDTOList;
    }

    // 手当申請：削除処理
    @Override
    public ServerResponse<String> deleteRequestAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response){
        String companyCode = requestAllowanceDTO.getCompanyCode();
        String requestNumber = requestAllowanceDTO.getRequestNumber();
        String applyYm = requestAllowanceDTO.applyYm;
        // 画像削除処理
        deleteImages(companyCode,applyYm,requestNumber);
        int deleteOt = requestAllowanceMapper.deleteByPrimaryKey(companyCode,applyYm,requestNumber);
        if( deleteOt == ConstCode.NUM_1){
            return ServerResponse.createBySuccessMessage("手当申請テーブルのレコードを削除しました。");
        }
        return  ServerResponse.createByErrorMessage("手当申請テーブルのレコードの削除が失敗しました。");
    }
    // 手当申請：出張,リーダー手当申請の保存処理
    @Override
    public ServerResponse<String> insertAllowance(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response) {
        // 手当申請に登録(出張,リーダー手当)
        RequestAllowance requestAllowance = new RequestAllowance();

        BeanCopier beanCopier = BeanCopier.create(RequestAllowanceDTO.class, RequestAllowance.class,false);
        beanCopier.copy(requestAllowanceDTO,requestAllowance,null);

        Date date = new Date();

        requestAllowance.setRegAccount(requestAllowanceDTO.requestUserId);//　登録者
        requestAllowance.setRegTime(date);//　登録日時
        requestAllowance.setUpdAccount(requestAllowanceDTO.requestUserId);//　更新者
        requestAllowance.setUpdTime(date);//　更新日時

        int result = requestAllowanceMapper.insert(requestAllowance);

        if (result == 0) {
            ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }
    // 手当申請：参照処理
    @Override
    public ServerResponse<List<RequestAllowanceDTO>> searchReAllowanceMonthData(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response) {
        List<RequestAllowanceDTO> requestAllowanceDTOList = requestAllowanceMapper.searchReAllowanceMonthData(requestAllowanceDTO.companyCode, requestAllowanceDTO.applyYm, requestAllowanceDTO.requestUserId, requestAllowanceDTO.requestTypeKbn);
        return  ServerResponse.createBySuccess(requestAllowanceDTOList);
    }
    // 手当申請：年月取得
    @Override
    public ServerResponse<List<GetApplyYmDTO>> searchMonth(GetApplyYmDTO getApplyYmDTO, HttpServletRequest request, HttpServletResponse response) {
        List<GetApplyYmDTO> requestAllowanceDTOList = requestAllowanceMapper.searchMonth(getApplyYmDTO.companyCode, getApplyYmDTO.requestUserId, getApplyYmDTO.requestTypeKbn);
        return  ServerResponse.createBySuccess(requestAllowanceDTOList);
    }

    // 手当申請：資格手当申請の保存処理
    @Override
    public ServerResponse<String> insertQualificationAllowance(QualificationAllowanceDTO qualificationAllowanceDTO, HttpServletRequest request, HttpServletResponse response) {
        // 資格手当チェック
        int count = requestAllowanceMapper.QualificationCheck(qualificationAllowanceDTO.companyCode,qualificationAllowanceDTO.qualificationId,
                qualificationAllowanceDTO.requestUserId,qualificationAllowanceDTO.requestNumber);
        if (count > 0){
            return ServerResponse.createByErrorMessage("該当資格が既に登録済みです。");
        }

        // 手当申請に登録(資格手当)
        RequestAllowance requestAllowance = new RequestAllowance();

        requestAllowance.setCompanyCode(qualificationAllowanceDTO.companyCode);// 会社コード
        requestAllowance.setApplyYm(qualificationAllowanceDTO.applyYm);// 年月
        requestAllowance.setRequestNumber(qualificationAllowanceDTO.requestNumber);// 申請番号
        requestAllowance.setRequestTypeKbn(ConstCode.RequestTypeKbn.ALLOWANCE＿QUALIFICATION);// 申請種類区分
        requestAllowance.setRequestUserId(qualificationAllowanceDTO.requestUserId);// 申請者
        requestAllowance.setAllowance(qualificationAllowanceDTO.allowance);// 手当金額
        requestAllowance.setQuantity(1);// 手当口数
        requestAllowance.setTotal(qualificationAllowanceDTO.total);// 手当合計
        requestAllowance.setProjectId(null);// 作番ID
        requestAllowance.setProjectName(null);// 作番名
        requestAllowance.setQualificationId(qualificationAllowanceDTO.qualificationId);// 資格ID
        requestAllowance.setQualificationExamDate(qualificationAllowanceDTO.qualificationExamDate);// 資格受検日
        //requestAllowance.setImage(qualificationAllowanceDTO.image);// 添付画像

        Date date = new Date();

        //画像保存処理
        //保存先パス作成
        Path p = createDirectory(qualificationAllowanceDTO);

        if(!StringUtils.isEmpty(qualificationAllowanceDTO.image)) {
            if (qualificationAllowanceDTO.image.contains("base:")){
                deleteImages(qualificationAllowanceDTO.companyCode,qualificationAllowanceDTO.applyYm,qualificationAllowanceDTO.requestNumber);
                qualificationAllowanceDTO.image = qualificationAllowanceDTO.image.replace("base:","");
                String fileName = qualificationAllowanceDTO.requestNumber + "-" + ConstCode.RequestTypeKbn.ALLOWANCE＿QUALIFICATION + "." + qualificationAllowanceDTO.contentType;
                String filePathName = Paths.get(p.toString(),fileName).toString();
                requestAllowance.setImage(filePathName);
                int checkResult = base64ConvertUtil.base64change(qualificationAllowanceDTO.image, filePathName);
            } else {

                String newPath = qualificationAllowanceDTO.image
                        .replace(this.localDomain, this.currentDirectory);
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    newPath = newPath.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                //String newPath = qualificationAllowanceDTO.image
                //        .replace(this.localDomain, this.currentDirectory).replace("/","\\");
                requestAllowance.setImage(newPath);
            }
        }

        requestAllowance.setRegAccount(qualificationAllowanceDTO.requestUserId);//　登録者
        requestAllowance.setRegTime(date);//　登録日時
        requestAllowance.setUpdAccount(qualificationAllowanceDTO.requestUserId);//　更新者
        requestAllowance.setUpdTime(date);//　更新日時

        int result = requestAllowanceMapper.insert(requestAllowance);

        if (result == 0) {
            ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }
    // 手当申請：資格手当申請の保存処理
    @Override
    public ServerResponse<String> qualificationAllowanceCheck(QualificationAllowanceDTO qualificationAllowanceDTO, HttpServletRequest request, HttpServletResponse response) {
        // 資格手当チェック
        int count = requestAllowanceMapper.QualificationCheck(qualificationAllowanceDTO.companyCode,qualificationAllowanceDTO.qualificationId,
                qualificationAllowanceDTO.requestUserId,qualificationAllowanceDTO.requestNumber);
        if (count > 0){
            return ServerResponse.createByErrorMessage("該当資格が既に登録済みです。");
        }
        return ServerResponse.createBySuccess();
    }

    private Path createDirectory(QualificationAllowanceDTO qualificationAllowanceDTO) {
        Path p = Paths.get(currentDirectory, qualification, qualificationAllowanceDTO.requestUserId);
        CreateFolderUtil.CreateFolder(p);

        return p;
    }

    // 画像の削除処理
    private void deleteImages(String companyCode, String applyYm ,String requestNumber) {

        RequestAllowance requestAllowance = requestAllowanceMapper.selectByPrimaryKey(companyCode,applyYm,requestNumber);
        if (requestAllowance != null){
            if (!StringUtils.isEmpty(requestAllowance.getImage())){
                Path p = Paths.get(requestAllowance.getImage());
                try{
                    Files.deleteIfExists(p);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }
    // 手当申請：慶弔見舞金申請の保存処理
    @Override
    public ServerResponse<String> insertCongratulatoryOrCondolencePayments(RequestAllowanceDTO requestAllowanceDTO, HttpServletRequest request, HttpServletResponse response) {
        // 手当申請に登録(慶弔見舞金申請)
        RequestAllowance requestAllowance = new RequestAllowance();

        BeanCopier beanCopier = BeanCopier.create(RequestAllowanceDTO.class, RequestAllowance.class,false);
        beanCopier.copy(requestAllowanceDTO,requestAllowance,null);

        Date date = new Date();
        //画像保存処理
        //保存先パス作成
        Path p = createDirectoryCongratulatory(requestAllowanceDTO);

        if(!StringUtils.isEmpty(requestAllowanceDTO.image)) {
            if (requestAllowanceDTO.image.contains("base:")){
                deleteImages(requestAllowanceDTO.companyCode,requestAllowanceDTO.applyYm,requestAllowanceDTO.requestNumber);
                requestAllowanceDTO.image = requestAllowanceDTO.image.replace("base:","");
                String fileName = requestAllowanceDTO.requestNumber + "-" + "040" + "." + requestAllowanceDTO.contentType;
                String filePathName = Paths.get(p.toString(),fileName).toString();
                requestAllowance.setImage(filePathName);
                int checkResult = base64ConvertUtil.base64change(requestAllowanceDTO.image, filePathName);
            } else {

                String newPath = requestAllowanceDTO.image
                        .replace(this.localDomain, this.currentDirectory);
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    newPath = newPath.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                //String newPath = qualificationAllowanceDTO.image
                //        .replace(this.localDomain, this.currentDirectory).replace("/","\\");
                requestAllowance.setImage(newPath);
            }
        }
        requestAllowance.setRegAccount(requestAllowanceDTO.regAccount);//　登録者
        requestAllowance.setRegTime(date);//　登録日時
        requestAllowance.setUpdAccount(requestAllowanceDTO.updAccount);//　更新者
        requestAllowance.setUpdTime(date);//　更新日時

        int result = requestAllowanceMapper.insert(requestAllowance);

        if (result == 0) {
            ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }
    private Path createDirectoryCongratulatory(RequestAllowanceDTO requestAllowanceDTO) {
        Path p = Paths.get(currentDirectory, congratulatory, requestAllowanceDTO.requestUserId);
        CreateFolderUtil.CreateFolder(p);

        return p;
    }

}