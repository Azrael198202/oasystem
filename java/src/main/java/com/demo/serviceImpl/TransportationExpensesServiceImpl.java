package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.TransportationExpensesMapper;
import com.demo.entity.TransportationExpenses;
import com.demo.pojo.requestHoliday.RequestHolidayDTO;
import com.demo.pojo.transportationExpenses.SelectApplyMonthTpDTO;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.service.ITransportationExpensesService;
import com.demo.util.base64ConvertUtil;
import com.demo.util.CreateFolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class TransportationExpensesServiceImpl implements ITransportationExpensesService {
    @Autowired
    TransportationExpensesMapper transportationExpensesMapper;

    String accessMode = ConstCode.IMAGE_RELATION.ACCESS_MODE;
    String currentDirectory = ConstCode.IMAGE_RELATION.CURRENT_DIRECTORY;
    String localDomain = ConstCode.IMAGE_RELATION.DOMAIN_URL;
    String imgDirectory = ConstCode.IMAGE_RELATION.IMG_DIRECTORY_TRANSPORT;
    String imgTemporary = ConstCode.IMAGE_RELATION.IMG_TEMPORARY_TRANSPORT;

    @Override
    public ServerResponse insertTransportationExpenses(List<TransportationExpensesDTO> transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {
        //保存先パス作成
        Path p = createDirectory(transportationExpensesDTO);
        String userId = transportationExpensesDTO.get(0).requestUserId;

        Date date = new Date();
        Random rnd = new Random();
        for(int i = 0; i < transportationExpensesDTO.size(); i++) {
            if(transportationExpensesDTO.get(i).image == ""){
                transportationExpensesDTO.get(i).image = null;
            }
            TransportationExpenses transportationExpensesList = new TransportationExpenses();

            BeanCopier beanCopier = BeanCopier.create(TransportationExpensesDTO.class, TransportationExpenses.class, false);
            beanCopier.copy(transportationExpensesDTO.get(i), transportationExpensesList, null);

            transportationExpensesList.setRegistrationUser(transportationExpensesDTO.get(i).requestUserId);
            transportationExpensesList.setRegistrationDatetime(date);
            transportationExpensesList.setUpdateUser(transportationExpensesDTO.get(i).requestUserId);
            transportationExpensesList.setUpdateDatetime(date);

            //画像保存処理
            if(!StringUtils.isEmpty(transportationExpensesDTO.get(i).image)) {
                int index = rnd.nextInt(100000);
                int one = transportationExpensesDTO.get(i).image.lastIndexOf(".");
                String conType = transportationExpensesDTO.get(i).image.substring(one + 1, transportationExpensesDTO.get(i).image.length());
                String fileName = transportationExpensesDTO.get(i).requestNumber + "-" + index + "." + conType;
                String filePathName = Paths.get(p.toString(),fileName).toString();
                transportationExpensesList.setImage(filePathName);

                String path = transportationExpensesDTO.get(i).image.replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    path = path.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                int checkResult = base64ConvertUtil.base64change(GetImageStr(path), filePathName);
            }

            int result = transportationExpensesMapper.insert(transportationExpensesList);

            if (result < ConstCode.NUM_1) {
                return ServerResponse.createBySuccessMessage("交通費申請内容テーブルへのデータ登録が失敗しました。");
            }
        }
        deleteImageTemporary(userId);
        return ServerResponse.createByErrorMessage("交通費申請内容テーブルにデータ登録しました。");
    }

    //画像イメージをバイナリデータへ変換し，Base64にエンコードする
    public String GetImageStr(String imgFile) {
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(Base64.getEncoder().encode(data));
    }
    @Override
    public ServerResponse<String> saveImgTemporary(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {
        //保存先パス作成
        Path p = Paths.get(this.currentDirectory, this.imgTemporary ,transportationExpensesDTO.requestUserId);
        CreateFolderUtil.CreateFolder(p);
        String filePathName ="";
        //画像保存処理
        if(!StringUtils.isEmpty(transportationExpensesDTO.img64Str)) {
            String fileName = transportationExpensesDTO.requestNumber + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + transportationExpensesDTO.img64contentType;
            filePathName = Paths.get(p.toString(),fileName).toString();
            //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！

            int checkResult = base64ConvertUtil.base64change(transportationExpensesDTO.img64Str, filePathName);
            filePathName = filePathName.replace(ConstCode.STRING_DOUBLE_BACK_SLASH,ConstCode.STRING_SLASH)
                    .replace(this.currentDirectory, this.localDomain);
        }
        return ServerResponse.createBySuccess(filePathName);
    }

    @Override
    public ServerResponse<List<TransportationExpensesDTO>> getTransportationExpenses(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {

        List<TransportationExpensesDTO> TransportationDTOList = null;
        try {
            TransportationDTOList = doGet(transportationExpensesMapper.selectAll(transportationExpensesDTO.companyCode, transportationExpensesDTO.requestUserId, transportationExpensesDTO.requestNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.createBySuccess(TransportationDTOList);
    }

    @Override
    public ServerResponse<String> deleteTransportationExpenses(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {

        String companyCode = transportationExpensesDTO.getCompanyCode();
        String requestNumber = transportationExpensesDTO.getRequestNumber();
        String requestUserId = transportationExpensesDTO.getRequestUserId();

//        画像の削除処理
        deleteImages(requestNumber);

        int deleteRh = transportationExpensesMapper.deleteTransportationExpenses(companyCode, requestNumber, requestUserId);
        if(deleteRh >= ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("交通費申請内容テーブルのレコードを削除しました。");
        }

        return ServerResponse.createByErrorMessage("交通費申請内容テーブルのレコードの削除が失敗しました。");
    }

    @Override
    public ServerResponse<String> updateTransportationExpenses(List<TransportationExpensesDTO> transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {

        String companyCode = transportationExpensesDTO.get(0).companyCode;
        String requestNumber = transportationExpensesDTO.get(0).requestNumber;
        String requestUserId = transportationExpensesDTO.get(0).requestUserId;
        String applyYm = transportationExpensesDTO.get(1).applyYm;

        Path p = createDirectory(transportationExpensesDTO);

        Date date = new Date();
        Random rnd = new Random();
        ArrayList<TransportationExpenses> insertList = new ArrayList<TransportationExpenses>();
        //  ループで囲む
        for(int i = 1; i < transportationExpensesDTO.size(); i++) {
            TransportationExpenses transportationExpensesList = new TransportationExpenses();

            BeanCopier beanCopier = BeanCopier.create(TransportationExpensesDTO.class, TransportationExpenses.class, false);
            beanCopier.copy(transportationExpensesDTO.get(i), transportationExpensesList, null);

            transportationExpensesList.setRegistrationUser(transportationExpensesDTO.get(i).requestUserId);
            transportationExpensesList.setRegistrationDatetime(date);
            transportationExpensesList.setUpdateUser(transportationExpensesDTO.get(i).requestUserId);
            transportationExpensesList.setUpdateDatetime(date);

            //画像保存処理
            if  (!StringUtils.isEmpty(transportationExpensesDTO.get(i).image) && transportationExpensesDTO.get(i).image.contains("temporary")) {
                //  画像の削除処理
                deleteImageOne(transportationExpensesDTO.get(i).requestNumber,transportationExpensesDTO.get(i).requestSubNumber,transportationExpensesDTO.get(i).companyCode);
                int index = rnd.nextInt(100000);
                int one = transportationExpensesDTO.get(i).image.lastIndexOf(".");
                String conType = transportationExpensesDTO.get(i).image.substring(one + 1, transportationExpensesDTO.get(i).image.length());
                String fileName = transportationExpensesDTO.get(i).requestNumber + "-" + index + "." + conType;
                String filePathName = Paths.get(p.toString(),fileName).toString();
                transportationExpensesList.setImage(filePathName);

                String path = transportationExpensesDTO.get(i).image.replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    path = path.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }

                int checkResult = base64ConvertUtil.base64change(GetImageStr(path), filePathName);
            } else {
                String path = transportationExpensesDTO.get(i).image.replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    path = path.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                transportationExpensesList.setImage(path);
            }
            if(StringUtils.isEmpty(transportationExpensesDTO.get(i).image)) {
                deleteImageOne(transportationExpensesDTO.get(i).requestNumber,transportationExpensesDTO.get(i).requestSubNumber,transportationExpensesDTO.get(i).companyCode);
            }
            insertList.add(transportationExpensesList);

        }
        int deleteRh = transportationExpensesMapper.deleteTransportationExpenses(companyCode, requestNumber, requestUserId);

        for(int i = 0; i < insertList.size(); i++) {
            int result = transportationExpensesMapper.insert(insertList.get(i));
            if(result != ConstCode.NUM_1) {
                return ServerResponse.createByErrorMessage("交通費申請内容テーブルへのデータ更新が失敗しました。");
            }
        }

        deleteImageTemporary(requestUserId);
        //  ループで囲む
        return ServerResponse.createBySuccessMessage("交通費申請内容テーブルのデータを更新しました。");
    }

    @Override
    public ServerResponse<List<TransportationExpensesDTO>> searchRefMonthData(TransportationExpensesDTO transportationExpensesDTO, HttpServletRequest request, HttpServletResponse response) {

        List<TransportationExpensesDTO> TransportationDTOList = null;
        try {
            TransportationDTOList = doGet(transportationExpensesMapper.searchRefMonthData(transportationExpensesDTO.companyCode, transportationExpensesDTO.applyYm, transportationExpensesDTO.requestUserId));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.createBySuccess(TransportationDTOList);
    }

    @Override
    public ServerResponse<SelectApplyMonthTpDTO> selectMonth(SelectApplyMonthTpDTO selectApplyMonthTpDTO, HttpServletRequest request, HttpServletResponse response) {

        SelectApplyMonthTpDTO SelectApplyMonthTp = transportationExpensesMapper.selectMonth(selectApplyMonthTpDTO.companyCode,selectApplyMonthTpDTO.requestUserId);

        return ServerResponse.createBySuccess(SelectApplyMonthTp);
    }

    public List<TransportationExpensesDTO> doGet(List<TransportationExpensesDTO> list) throws IOException {
        List<TransportationExpensesDTO> newList = list;
        for (int i = 0; i < list.size();i++){
            TransportationExpensesDTO array = list.get(i);
            if(array.image != null && !StringUtils.isEmpty(array.image)){
                File file= new File(array.image);
                String contentType = Files.probeContentType(file.toPath());
                //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！
                String newPath = array.image.replace(ConstCode.STRING_DOUBLE_BACK_SLASH,ConstCode.STRING_SLASH)
                        .replace(this.currentDirectory,this.localDomain);
                array.image = newPath;
                array.contentType = contentType;
            }
            System.out.println(array);
            newList.set(i,array);
            System.out.println(newList);
        }
        System.out.println(newList);
        return newList;
    }

    // 画像の削除処理
    private void deleteImages(String requestNumber) {

        List<TransportationExpensesDTO> deleteImage = transportationExpensesMapper.searchImage(requestNumber);

        for(int i = 0; i < deleteImage.size(); i++) {
            TransportationExpensesDTO img = deleteImage.get(i);
            if(img != null && img.image != null){
                Path p = Paths.get(deleteImage.get(i).image);
                try{
                    Files.deleteIfExists(p);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }

    // 画像の削除処理
    private void deleteImageOne(String requestNumber, Byte requestSubNumber, String companyCode ) {

        List<TransportationExpensesDTO> deleteImage = transportationExpensesMapper.searchImageOne(requestNumber, requestSubNumber, companyCode);

        for(int i = 0; i < deleteImage.size(); i++) {
            TransportationExpensesDTO img = deleteImage.get(i);
            if(img != null && img.image != null){
                Path p = Paths.get(deleteImage.get(i).image);
                try{
                    Files.deleteIfExists(p);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
    }
    // 画像の削除処理
    @Override
    public ServerResponse<String> deleteImageTemporary(String userId, HttpServletRequest request, HttpServletResponse response) {
        File files = null;
        // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
        if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
            files = new File((currentDirectory + imgTemporary + userId )
                    .replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH));
        }else{
            files = new File((currentDirectory + imgTemporary + userId ));
        }

        if (files.isDirectory() && files.exists()){
            String[] fileList = files.list();
            for(String file : fileList) {
                Path p = null;
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    p = Paths.get((currentDirectory + imgTemporary + userId + ConstCode.STRING_DOUBLE_BACK_SLASH + file)
                            .replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH));
                }else{
                    p = Paths.get((currentDirectory + imgTemporary + userId + ConstCode.STRING_SLASH + file));
                }

                try{
                    Files.deleteIfExists(p);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
            files.delete();
        }
        return ServerResponse.createBySuccessMessage("Temporary画像を削除しました。");
    }

    private void deleteImageTemporary(String userId) {
        File files = null;
        // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
        if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
            files = new File((currentDirectory + imgTemporary + userId )
                    .replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH));
        }else{
            files = new File((currentDirectory + imgTemporary + userId ));
        }

        if (files.isDirectory() && files.exists()){
            String[] fileList = files.list();
            for(String file : fileList) {

                Path p = null;
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    p = Paths.get((currentDirectory + imgTemporary + userId + ConstCode.STRING_DOUBLE_BACK_SLASH + file)
                            .replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH));
                }else{
                    p = Paths.get((currentDirectory + imgTemporary + userId + ConstCode.STRING_SLASH + file));
                }

                try{
                    Files.deleteIfExists(p);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
            files.delete();
        }
    }
    private Path createDirectory(List<TransportationExpensesDTO> transportationExpensesDTO) {
        //ローカル用カレントディレクトリ取得
        Path p = Paths.get(this.currentDirectory, this.imgDirectory ,transportationExpensesDTO.get(0).requestUserId);
        CreateFolderUtil.CreateFolder(p);

        return p;
    }
}
