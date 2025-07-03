package com.demo.serviceImpl;

import com.demo.common.ConstCode;
import com.demo.common.ServerResponse;
import com.demo.dao.NoticeMapper;
import com.demo.entity.Notice;
import com.demo.entity.TransportationExpenses;
import com.demo.pojo.notice.NoticeListDTO;
import com.demo.pojo.notice.NoticeRpnDTO;
import com.demo.pojo.notice.NoticeAllListDTO;
import com.demo.pojo.transportationExpenses.TransportationExpensesDTO;
import com.demo.service.INoticeService;
import com.demo.util.CreateFolderUtil;
import com.demo.util.base64ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/*
 * Author  : libin
   Time    : 2021/07/12
   Function: 勤務表処理
*/
@Service
@Slf4j

public class NoticeServiceImpl implements INoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    String accessMode = ConstCode.IMAGE_RELATION.ACCESS_MODE;
    String currentDirectory = ConstCode.IMAGE_RELATION.CURRENT_DIRECTORY;
    String localDomain = ConstCode.IMAGE_RELATION.DOMAIN_URL;
    String imgDirectory = ConstCode.IMAGE_RELATION.IMG_DIRECTORY_NOTICE;


    @Override
    public ServerResponse<List<NoticeRpnDTO>> getNoticeList(NoticeListDTO noticeListDTO, HttpServletRequest request, HttpServletResponse response) {

        List<NoticeRpnDTO> noticeRpnDTOList = noticeMapper.selectAll(noticeListDTO.getCompanyCode());

        return ServerResponse.createBySuccess(noticeRpnDTOList);
    }

    @Override
    public ServerResponse<NoticeRpnDTO> getNoticeById(NoticeListDTO noticeListDTO, HttpServletRequest request, HttpServletResponse response) {
        NoticeRpnDTO noticeRpnDTO = noticeMapper.selectByNoticeNumber(noticeListDTO.getCompanyCode(),noticeListDTO.getNoticeNumber());
        try {
            noticeRpnDTO = doGet(noticeMapper.selectByNoticeNumber(noticeListDTO.getCompanyCode(),noticeListDTO.getNoticeNumber()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(noticeRpnDTO);
    }

    public NoticeRpnDTO doGet(NoticeRpnDTO noticeRpnDTO) throws IOException {
        NoticeRpnDTO newDto = new NoticeRpnDTO();
        BeanCopier beanCopier = BeanCopier.create(NoticeRpnDTO.class, NoticeRpnDTO.class, false);
        beanCopier.copy(noticeRpnDTO, newDto, null);
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment1())) {
            File file = new File(noticeRpnDTO.getNoticeAttachment1());
            String contentType = Files.probeContentType(file.toPath());
            //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！
            String newPath = noticeRpnDTO.getNoticeAttachment1().replace(ConstCode.STRING_DOUBLE_BACK_SLASH, ConstCode.STRING_SLASH)
                    .replace(this.currentDirectory,this.localDomain);
            newDto.setNoticeAttachment1(newPath);
            newDto.setContentType1(contentType);
            newDto.setContentType1(contentType);
            System.out.println(newDto);
        }
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment2())) {
            File file = new File(noticeRpnDTO.getNoticeAttachment2());
            String contentType = Files.probeContentType(file.toPath());
            //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！
            String newPath = noticeRpnDTO.getNoticeAttachment2().replace(ConstCode.STRING_DOUBLE_BACK_SLASH, ConstCode.STRING_SLASH)
                    .replace(this.currentDirectory,this.localDomain);
            newDto.setNoticeAttachment2(newPath);
            newDto.setContentType2(contentType);
            System.out.println(newDto);
        }
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment3())) {
            File file = new File(noticeRpnDTO.getNoticeAttachment3());
            String contentType = Files.probeContentType(file.toPath());
            //  ローカルの場合は絶対パスをドメインパスへ変換する必要がある！！！
            String newPath = noticeRpnDTO.getNoticeAttachment3().replace(ConstCode.STRING_DOUBLE_BACK_SLASH,ConstCode.STRING_SLASH)
                    .replace(this.currentDirectory,this.localDomain);
            newDto.setNoticeAttachment3(newPath);
            newDto.setContentType3(contentType);
            newDto.setContentType3(contentType);
            System.out.println(newDto);
        }
        return newDto;
    }
    @Override
    public ServerResponse<List<NoticeAllListDTO>> getNoticeAllList(NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response) {
        List<NoticeAllListDTO> noticeAllListDTOList = noticeMapper.searchNotice(noticeAllListDTO.getCompanyCode()
                ,('%'+ noticeAllListDTO.getNoticeStatus() + '%')
                ,('%'+ noticeAllListDTO.getNoticeCategory() + '%')
                ,('%'+ noticeAllListDTO.getUserIdOrName() + '%')
        );
        return ServerResponse.createBySuccess(noticeAllListDTOList);
    }

    @Override
    public ServerResponse<String> deleteNotice(NoticeAllListDTO noticeAllListDTO, HttpServletRequest request, HttpServletResponse response) {
        String companyCode   = noticeAllListDTO.getCompanyCode();
        String noticeNumber  = noticeAllListDTO.getNoticeNumber();
        deleteImages(companyCode,noticeNumber);
        int deleteNs = noticeMapper.deleteNotice(companyCode, noticeNumber);
        if(deleteNs == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("お知らせテーブルのレコードを削除しました。");
        }

        return ServerResponse.createByErrorMessage("お知らせテーブルのレコードの削除が失敗しました。");
    }

    @Override
    public ServerResponse<String> insertNotice(NoticeAllListDTO  noticeAllListDTO, HttpServletRequest request, HttpServletResponse response){
        String noticeNumber = noticeMapper.checkNoticeNumber();
        noticeNumber = String.valueOf((Integer.parseInt(noticeNumber) + 1));
        Notice notice = new Notice();
        Date date = new Date();
        BeanCopier beanCopier = BeanCopier.create(NoticeAllListDTO.class, Notice.class, false);
        beanCopier.copy(noticeAllListDTO, notice, null);
        notice.setNoticeNumber(noticeNumber);

        notice.setUpdateDatetime(date);
        notice.setRegistrationDatetime(date);
        notice.setRegistrationUser(noticeAllListDTO.requestUserId);

        //保存先パス作成
        Path p = createDirectory(noticeAllListDTO);
        //画像保存処理
        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment1)) {
            noticeAllListDTO.noticeAttachment1 = noticeAllListDTO.noticeAttachment1.replace("base:","");
            String fileName = noticeNumber + "-" + notice.getNoticeCategory() + "noticeAttachment1." + noticeAllListDTO.getContentType1();
            String filePathName = Paths.get(p.toString(),fileName).toString();
            notice.setNoticeAttachment1(filePathName);
            int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment1, filePathName);
        }

        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment2)) {
            noticeAllListDTO.noticeAttachment2 = noticeAllListDTO.noticeAttachment2.replace("base:","");
            String fileName = noticeAllListDTO.getNoticeNumber() + "-" + noticeAllListDTO.getNoticeCategory() + "noticeAttachment2." + noticeAllListDTO.getContentType2();
            String filePathName = Paths.get(p.toString(),fileName).toString();
            notice.setNoticeAttachment2(filePathName);
            int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment2, filePathName);
        }

        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment3)) {
            noticeAllListDTO.noticeAttachment3 = noticeAllListDTO.noticeAttachment3.replace("base:","");
            String fileName = noticeAllListDTO.getNoticeNumber() + "-" + noticeAllListDTO.getNoticeCategory() + "noticeAttachment3." + noticeAllListDTO.getContentType3();
            String filePathName = Paths.get(p.toString(),fileName).toString();
            notice.setNoticeAttachment3(filePathName);
            int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment3, filePathName);
        }

        int result = noticeMapper.insertNotice(notice);
        if (result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("overTimeテーブルにデータ登録しました。");
        }
        return ServerResponse.createByErrorMessage("overTimeテーブルにデータ登録失敗しました。");
    }

    @Override
    public ServerResponse<String> updateNotice(NoticeAllListDTO  noticeAllListDTO, HttpServletRequest request, HttpServletResponse response){
        Notice notice = new Notice();
        Date date = new Date();
        BeanCopier beanCopier = BeanCopier.create(NoticeAllListDTO.class, Notice.class, false);
        beanCopier.copy(noticeAllListDTO, notice, null);

        notice.setUpdateDatetime(date);
        notice.setUpdateUser(noticeAllListDTO.requestUserId);
        //保存先パス作成
        Path p = createDirectory(noticeAllListDTO);
        NoticeRpnDTO noticeRpnDTO = noticeMapper.selectByNoticeNumber(noticeAllListDTO.getCompanyCode(),noticeAllListDTO.getNoticeNumber());
        //画像保存処理
        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment1)) {
            if (noticeAllListDTO.noticeAttachment1.contains("base:")){
                if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment1())){
                    Path p1 = Paths.get(noticeRpnDTO.getNoticeAttachment1());
                    try{
                        Files.deleteIfExists(p1);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                }
                noticeAllListDTO.noticeAttachment1 = noticeAllListDTO.noticeAttachment1.replace("base:","");

                String fileName = noticeAllListDTO.getNoticeNumber() + "-" + noticeAllListDTO.getNoticeCategory() + "noticeAttachment1." + noticeAllListDTO.getContentType1();
                String filePathName = Paths.get(p.toString(),fileName).toString();
                notice.setNoticeAttachment1(filePathName);
                int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment1, filePathName);
            } else {
                String newPath = noticeAllListDTO.getNoticeAttachment1()
                        .replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    newPath = newPath.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                notice.setNoticeAttachment1(newPath);
            }
        } else {
            if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment1())){
                Path p1 = Paths.get(noticeRpnDTO.getNoticeAttachment1());
                try{
                    Files.deleteIfExists(p1);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }

        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment2)) {
            if (noticeAllListDTO.noticeAttachment2.contains("base:")){
                if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment2())){
                    Path p2 = Paths.get(noticeRpnDTO.getNoticeAttachment2());
                    try{
                        Files.deleteIfExists(p2);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                }
                noticeAllListDTO.noticeAttachment2 = noticeAllListDTO.noticeAttachment2.replace("base:","");

                String fileName = noticeAllListDTO.getNoticeNumber() + "-" + noticeAllListDTO.getNoticeCategory() + "noticeAttachment2." + noticeAllListDTO.getContentType2();
                String filePathName = Paths.get(p.toString(),fileName).toString();
                notice.setNoticeAttachment2(filePathName);
                int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment2, filePathName);
            }else {
                String newPath = noticeAllListDTO.getNoticeAttachment2()
                        .replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    newPath = newPath.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                notice.setNoticeAttachment2(newPath);
            }
        } else {
            if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment2())){
                Path p2 = Paths.get(noticeRpnDTO.getNoticeAttachment2());
                try{
                    Files.deleteIfExists(p2);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }

        if(!StringUtils.isEmpty(noticeAllListDTO.noticeAttachment3)) {
            if (noticeAllListDTO.noticeAttachment3.contains("base:")){
                if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment3())){
                    Path p3 = Paths.get(noticeRpnDTO.getNoticeAttachment3());
                    try{
                        Files.deleteIfExists(p3);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                }
                noticeAllListDTO.noticeAttachment3 = noticeAllListDTO.noticeAttachment3.replace("base:","");

                String fileName = noticeAllListDTO.getNoticeNumber() + "-" + noticeAllListDTO.getNoticeCategory() + "noticeAttachment3." + noticeAllListDTO.getContentType3();
                String filePathName = Paths.get(p.toString(),fileName).toString();
                notice.setNoticeAttachment3(filePathName);
                int checkResult = base64ConvertUtil.base64change(noticeAllListDTO.noticeAttachment3, filePathName);
            }else {
                String newPath = noticeAllListDTO.getNoticeAttachment3()
                        .replace(this.localDomain, this.currentDirectory);
                // ローカルの場合、パスの「/」を「\\」へ変換する必要がある
                if(this.accessMode.equals(ConstCode.IMAGE_RELATION.ACCESS_MODE_LOCAL)){
                    newPath = newPath.replace(ConstCode.STRING_SLASH,ConstCode.STRING_DOUBLE_BACK_SLASH);
                }
                notice.setNoticeAttachment3(newPath);
            }
        } else {
            if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment3())){
                Path p3 = Paths.get(noticeRpnDTO.getNoticeAttachment3());
                try{
                    Files.deleteIfExists(p3);
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }

        int result = noticeMapper.updateByPrimaryKeySelective(notice);
        if (result == ConstCode.NUM_1) {
            return ServerResponse.createBySuccessMessage("overTimeテーブルにデータ登録しました。");
        }
        return ServerResponse.createByErrorMessage("overTimeテーブルにデータ登録失敗しました。");
    }

    private Path createDirectory(NoticeAllListDTO noticeAllListDTO) {
        Path p = Paths.get(currentDirectory,imgDirectory ,noticeAllListDTO.requestUserId);
        CreateFolderUtil.CreateFolder(p);

        return p;
    }

    private static String imageToBase64(BufferedImage bufferedImage) throws Exception {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bas);
        return new String(Base64.getEncoder().encode((bas.toByteArray())));
    }

    // 画像の削除処理
    private void deleteImages(String companyCode, String noticeNumber) {

        NoticeRpnDTO noticeRpnDTO = noticeMapper.selectByNoticeNumber(companyCode,noticeNumber);
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment1())){
            Path p = Paths.get(noticeRpnDTO.getNoticeAttachment1());
            try{
                Files.deleteIfExists(p);
            }catch(IOException e){
                System.out.println(e);
            }
        }
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment2())){
            Path p = Paths.get(noticeRpnDTO.getNoticeAttachment2());
            try{
                Files.deleteIfExists(p);
            }catch(IOException e){
                System.out.println(e);
            }
        }
        if (!StringUtils.isEmpty(noticeRpnDTO.getNoticeAttachment3())){
            Path p = Paths.get(noticeRpnDTO.getNoticeAttachment3());
            try{
                Files.deleteIfExists(p);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
}
