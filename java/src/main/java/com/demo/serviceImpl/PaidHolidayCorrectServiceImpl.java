package com.demo.serviceImpl;

import com.demo.common.ServerResponse;
import com.demo.dao.PaidHolidayContentsUpdateMapper;
import com.demo.entity.PaidHoliday;
import com.demo.entity.PaidHolidayContentsUpdate;
import com.demo.entity.PaidHolidayCorrect;
import com.demo.pojo.paidHolidayCorrect.DefaultPaidHolidayCorrectDTO;
import com.demo.service.IPaidHolidayCorrectService;
import com.demo.dao.PaidHolidayCorrectMapper;
import com.demo.dao.PaidHolidayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

@Service
@Slf4j

public class PaidHolidayCorrectServiceImpl implements IPaidHolidayCorrectService {
    @Autowired
    PaidHolidayCorrectMapper paidHolidayCorrectMapper;
    @Autowired
    PaidHolidayContentsUpdateMapper paidHolidayContentsUpdateMapper;
    @Autowired
    PaidHolidayMapper paidHolidayMapper;
    @Override
    public ServerResponse<List<DefaultPaidHolidayCorrectDTO>>getPaidHolidayCorrect(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request, HttpServletResponse response){
        //選択された年月を取得して修正対象月のものを返す
        List<DefaultPaidHolidayCorrectDTO> getPaidHolidayCorrect=paidHolidayCorrectMapper.getSelectPaidHolidayCorrect(defaultPaidHolidayCorrectDTO.getCompanyCode(),defaultPaidHolidayCorrectDTO.getUserId(),defaultPaidHolidayCorrectDTO.getDateYm());
        return ServerResponse.createBySuccess(getPaidHolidayCorrect);
    }

    @Override
    public ServerResponse<String> PaidHolidayCorrect(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO , HttpServletRequest request, HttpServletResponse response){


        String status=defaultPaidHolidayCorrectDTO.getStatus();
        String Kbn=defaultPaidHolidayCorrectDTO.getCorrectKbn();

        if(Kbn.equals("020")&&status.equals("1")){
            ServerResponse<String> msg=insertFunc(defaultPaidHolidayCorrectDTO);
            return msg;
        }
        else if(Kbn.equals("020")&&status.equals("2")){
            deleteFunc(defaultPaidHolidayCorrectDTO);
            insertFunc(defaultPaidHolidayCorrectDTO);
            return ServerResponse.createBySuccessMessage("消化データの修正に成功しました");
        }
        else if(Kbn.equals("020")&&status.equals("3")){
            deleteFunc(defaultPaidHolidayCorrectDTO);
            return ServerResponse.createBySuccessMessage("消化データの削除に成功しました");}

        else if(Kbn.equals("030")&&status.equals("1")){
            ServerResponse<String> msg= insertFunc2(defaultPaidHolidayCorrectDTO);
            return msg;
        }
        else if(Kbn.equals("030")&&status.equals("2")){
            deleteFunc2(defaultPaidHolidayCorrectDTO);
            insertFunc2(defaultPaidHolidayCorrectDTO);
            return ServerResponse.createBySuccessMessage("付与データの修正に成功しました");
        }
        else if(Kbn.equals("030")&&status.equals("3")){
            deleteFunc2(defaultPaidHolidayCorrectDTO);
            return ServerResponse.createBySuccessMessage("付与データの削除に成功しました");
        }
        return ServerResponse.createByErrorMessage("区分またはステータスが無効です");
    }

///////////消化側メソッド///////////////
    //修正区分が消化の時の登録関数
    private ServerResponse<String> insertFunc(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO){

        //初期化処理
        PaidHolidayCorrect paidHolidayCorrect=new PaidHolidayCorrect();
        PaidHolidayContentsUpdate paidHolidayContentsUpdate=new PaidHolidayContentsUpdate();
       //日付代入
        Date date = new Date();
        String str=yyyyMMdd(date);

        //取得データの値追加代入
        defaultPaidHolidayCorrectDTO.setChangeDate(str);
        defaultPaidHolidayCorrectDTO.setRegistrationDatetime(date);
        defaultPaidHolidayCorrectDTO.setUpdateDatetime(date);

        //枝番の取得代入
        String Slength=SubNumber(defaultPaidHolidayCorrectDTO);
        defaultPaidHolidayCorrectDTO.setSubNumber(Slength);

        //vue側で取得したデータを有休修正テーブルで使えるようにする
        BeanCopier beanCopier = BeanCopier.create(DefaultPaidHolidayCorrectDTO.class,PaidHolidayCorrect.class,false);
        beanCopier.copy(defaultPaidHolidayCorrectDTO,paidHolidayCorrect,null);

        //vue側で取得したデータを有休残更新内容テーブルで使えるようにする
        BeanCopier beanCopier2 = BeanCopier.create(DefaultPaidHolidayCorrectDTO.class, PaidHolidayContentsUpdate.class,false);
        beanCopier2.copy(defaultPaidHolidayCorrectDTO,paidHolidayContentsUpdate,null);

        return updateTable1(defaultPaidHolidayCorrectDTO,paidHolidayCorrect ,paidHolidayContentsUpdate,date);
    }
    //3テーブル登録/更新処理
    private ServerResponse<String>updateTable1(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO ,PaidHolidayCorrect paidHolidayCorrect,PaidHolidayContentsUpdate paidHolidayContentsUpdate,Date date){
        //書き込み処理
        PaidHoliday paidHolidayYear1;
        PaidHoliday paidHolidayYear2;
        //有休残テーブル２年分のデータを取得
        List<PaidHoliday> paidHolidayList = paidHolidayMapper.getPaidHolidayAssignmentYear(defaultPaidHolidayCorrectDTO.getCompanyCode(), defaultPaidHolidayCorrectDTO.getUserId());
        //有休残テーブルレコードが2年分の時
        if(paidHolidayList.size()==2){
            paidHolidayYear1=paidHolidayList.get(0);
            paidHolidayYear2=paidHolidayList.get(1);
            //前年度分が足りない時、今年度分のを使う
            if(paidHolidayCorrect.getCorrectDays()>paidHolidayYear1.getPaidHolidayRemaining()) {
                //a=修正日数-前年度有休残数
                float a = paidHolidayYear1.getPaidHolidayRemaining();
                float b= paidHolidayCorrect.getCorrectDays() - paidHolidayYear1.getPaidHolidayRemaining();
                //もし今年分の有休残数が足りない場合
                if(paidHolidayYear2.getPaidHolidayRemaining()<a){
                    return ServerResponse.createByErrorMessage("有休残数が不足しています");
                }
                //有休残テーブルのレコード更新/有休残更新内容テーブルのレコード登録
                minusUpdatePaidHolidayDays(paidHolidayYear1, a, defaultPaidHolidayCorrectDTO, date);
                insertPaidHolidayContentsUpdate(paidHolidayYear1,a, paidHolidayContentsUpdate);

                minusUpdatePaidHolidayDays(paidHolidayYear2, b, defaultPaidHolidayCorrectDTO, date);
                insertPaidHolidayContentsUpdate(paidHolidayYear2,b, paidHolidayContentsUpdate);
            }
            //前年度分で足りるとき
            else{
                minusUpdatePaidHolidayDays(paidHolidayYear1,paidHolidayCorrect.getCorrectDays(),defaultPaidHolidayCorrectDTO,date);
                insertPaidHolidayContentsUpdate(paidHolidayYear1,paidHolidayCorrect.getCorrectDays(), paidHolidayContentsUpdate);
            }
        }
        //有休残テーブルレコードが1年分の時
        else if(paidHolidayList.size()==1) {
            paidHolidayYear1=paidHolidayList.get(0);
            if (paidHolidayCorrect.getCorrectDays() > paidHolidayYear1.getPaidHolidayRemaining()) {
                return ServerResponse.createByErrorMessage("有休残数が不足しています");
            }
            else {
                minusUpdatePaidHolidayDays(paidHolidayYear1,paidHolidayCorrect.getCorrectDays(),defaultPaidHolidayCorrectDTO,date);
                insertPaidHolidayContentsUpdate(paidHolidayYear1,paidHolidayCorrect.getCorrectDays(), paidHolidayContentsUpdate);
            }
        }
        //有休残テーブルレコードが存在しない時
        else if(paidHolidayList.size()==0){
            return ServerResponse.createByErrorMessage("有休残テーブルが存在しません");
        }
        //有休修正テーブルのレコード追加
        paidHolidayCorrectMapper.insert(paidHolidayCorrect);
        return ServerResponse.createBySuccessMessage("消化データの登録に成功しました");
    }
    //有休残テーブルのレコード更新関数
    private void minusUpdatePaidHolidayDays(PaidHoliday ph ,float x,DefaultPaidHolidayCorrectDTO pc,Date date){
        ph.setPaidHolidayRemaining(-x);
        ph.setUpdateUser(pc.getUpdateUser());
        ph.setUpdateDatetime(date);
        paidHolidayMapper.updatePaidHolidayRemaining(ph);
    }
    //有休残更新内容テーブルレコード登録関数
    private void insertPaidHolidayContentsUpdate(PaidHoliday ph,float x,PaidHolidayContentsUpdate pu) {
        pu.setDateYm(ph.getDateYm());
        pu.setAssignmentYear(ph.getAssignmentYear());
        pu.setUpdateDays(x);
        paidHolidayContentsUpdateMapper.insert(pu);
        }
    //修正区分が消化の時の削除関数
    private void deleteFunc(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO) {

       //更新内容テーブルを呼び出し
        List<PaidHolidayContentsUpdate> y = paidHolidayContentsUpdateMapper.getPaidHolidayContentsUpdate(defaultPaidHolidayCorrectDTO.getCompanyCode(), defaultPaidHolidayCorrectDTO.getUserId(), defaultPaidHolidayCorrectDTO.getChangeDate(), defaultPaidHolidayCorrectDTO.getSubNumber());
        //初期化宣言
        PaidHoliday paidHolidayDeleteList1 = new PaidHoliday();
        PaidHoliday paidHolidayDeleteList2 = new PaidHoliday();
        PaidHolidayContentsUpdate paidHolidayContentsUpdateList1 = new PaidHolidayContentsUpdate();
        PaidHolidayContentsUpdate paidHolidayContentsUpdateList2 = new PaidHolidayContentsUpdate();
        //コピー
        BeanCopier beanCopier = BeanCopier.create(PaidHolidayContentsUpdate.class, PaidHolidayContentsUpdate.class, false);
        BeanCopier beanCopier2 = BeanCopier.create(PaidHolidayContentsUpdate.class, PaidHoliday.class, false);

        //全3つテーブル更新/削除処理
        if (y.size()==1) {
            updateTable2(beanCopier,beanCopier2,paidHolidayContentsUpdateList1,paidHolidayDeleteList1,y,0);

        }
        if (y.size()==2) {
            updateTable2(beanCopier,beanCopier2,paidHolidayContentsUpdateList1,paidHolidayDeleteList1,y,0);
            updateTable2(beanCopier,beanCopier2,paidHolidayContentsUpdateList2,paidHolidayDeleteList2,y,1);
         }
    }
    //３テーブル更新/削除関数
    public void updateTable2(BeanCopier b1,BeanCopier b2,PaidHolidayContentsUpdate pu,PaidHoliday ph,List<PaidHolidayContentsUpdate> y,int num){
        //numのレコードを
        b1.copy(y.get(num), pu, null);
        b2.copy(y.get(num), ph, null);
        ph.setPaidHolidayRemaining(pu.getUpdateDays());
        ph.setAssignmentYear(pu.getAssignmentYear());
        //残テーブル更新
        paidHolidayMapper.updatePaidHolidayRemaining(ph);
        //残更新内容テーブル削除
        paidHolidayContentsUpdateMapper.deleteByPrimaryKey(pu.getCompanyCode(), pu.getUserId(), pu.getChangeDate(), pu.getSubNumber(), pu.getDateYm(), pu.getAssignmentYear());
        //修正テーブル削除
        paidHolidayCorrectMapper.deleteByPrimaryKey(pu.getCompanyCode(), pu.getUserId(), pu.getChangeDate(), pu.getSubNumber());

    }
///////////付与側メソッド///////////////
    //修正区分が付与の時の登録関数
    private ServerResponse<String> insertFunc2(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO){
        //現在日付代入
        Date date = new Date();
        //
        String str=yyyyMMdd(date);
        //失効日計算
        String str2 = expireDate(defaultPaidHolidayCorrectDTO,date);
        //変更日セット
        defaultPaidHolidayCorrectDTO.setChangeDate(str);
        //登録時刻セット
        defaultPaidHolidayCorrectDTO.setRegistrationDatetime(date);
        //更新時刻セット
        defaultPaidHolidayCorrectDTO.setUpdateDatetime(date);
        //有休残_付与年度セット
        String assignmentDay = defaultPaidHolidayCorrectDTO.getCorrectDate();
        String assignmentYear = assignmentDay.substring(0,4);
        defaultPaidHolidayCorrectDTO.setAssignmentYear(assignmentYear);

        //枝番の取得代入
        String Slength=SubNumber(defaultPaidHolidayCorrectDTO);
        defaultPaidHolidayCorrectDTO.setSubNumber(Slength);

        PaidHoliday paidHolidayList=new PaidHoliday();
        BeanCopier beanCopier=BeanCopier.create(DefaultPaidHolidayCorrectDTO.class,PaidHoliday.class,false);
        beanCopier.copy(defaultPaidHolidayCorrectDTO,paidHolidayList,null);
        paidHolidayList.setAssignmentDay(assignmentDay);
        paidHolidayList.setExpiredYmd(str2);
        paidHolidayList.setPaidHolidayRemaining(defaultPaidHolidayCorrectDTO.getCorrectDays());
        PaidHoliday x= paidHolidayMapper.getPaidHoliday(defaultPaidHolidayCorrectDTO.getCompanyCode(),defaultPaidHolidayCorrectDTO.getUserId(),defaultPaidHolidayCorrectDTO.getDateYm(),defaultPaidHolidayCorrectDTO.getAssignmentYear());
        if(x!=null){
            return ServerResponse.createByErrorMessage("有休残テーブルのレコードが既に存在しています");
        }
        paidHolidayMapper.insert(paidHolidayList);

        PaidHolidayCorrect paidHolidayCorrect=new PaidHolidayCorrect();
        BeanCopier beanCopier2=BeanCopier.create(DefaultPaidHolidayCorrectDTO.class,PaidHolidayCorrect.class,false);
        beanCopier2.copy(defaultPaidHolidayCorrectDTO,paidHolidayCorrect,null);
        paidHolidayCorrectMapper.insert(paidHolidayCorrect);

        PaidHolidayContentsUpdate paidHolidayContentsUpdateList=new PaidHolidayContentsUpdate();
        BeanCopier beanCopier3=BeanCopier.create(DefaultPaidHolidayCorrectDTO.class,PaidHolidayContentsUpdate.class,false);
        beanCopier3.copy(defaultPaidHolidayCorrectDTO,paidHolidayContentsUpdateList,null);
        paidHolidayContentsUpdateMapper.insert(paidHolidayContentsUpdateList);
       
        return ServerResponse.createBySuccessMessage("付与データの登録に成功しました");
    }
    //修正区分が付与の時の削除関数
    private void deleteFunc2(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO ){
        List<PaidHolidayContentsUpdate> y = paidHolidayContentsUpdateMapper.getPaidHolidayContentsUpdate(defaultPaidHolidayCorrectDTO.getCompanyCode(), defaultPaidHolidayCorrectDTO.getUserId(), defaultPaidHolidayCorrectDTO.getChangeDate(), defaultPaidHolidayCorrectDTO.getSubNumber());
        //初期化宣言
        PaidHoliday paidHolidayDeleteList = new PaidHoliday();
        PaidHolidayContentsUpdate paidHolidayContentsUpdateList = new PaidHolidayContentsUpdate();
        //コピー
        BeanCopier beanCopier = BeanCopier.create(PaidHolidayContentsUpdate.class, PaidHolidayContentsUpdate.class, false);
        BeanCopier beanCopier2 = BeanCopier.create(PaidHolidayContentsUpdate.class, PaidHoliday.class, false);
        //テーブルのレコードを削除する関数
        deleteTable(beanCopier,beanCopier2,paidHolidayContentsUpdateList,paidHolidayDeleteList,y);
    }
    //テーブルのレコードを削除する関数
    private void deleteTable(BeanCopier b1,BeanCopier b2,PaidHolidayContentsUpdate pu,PaidHoliday ph,List<PaidHolidayContentsUpdate> y){
        //numのレコードを
        b1.copy(y.get(0), pu, null);
        b2.copy(y.get(0), ph, null);
        //残テーブル削除
        paidHolidayMapper.deleteByPrimaryKey(ph.getCompanyCode(),ph.getUserId(), ph.getDateYm(), ph.getAssignmentYear());
        //残更新内容テーブル削除
        paidHolidayContentsUpdateMapper.deleteByPrimaryKey(pu.getCompanyCode(), pu.getUserId(), pu.getChangeDate(), pu.getSubNumber(), pu.getDateYm(), pu.getAssignmentYear());
        //修正テーブル削除
        paidHolidayCorrectMapper.deleteByPrimaryKey(pu.getCompanyCode(), pu.getUserId(), pu.getChangeDate(), pu.getSubNumber());

    }
////////////共通メソッド///////////////
    //枝番を取得してそれに１を足した値を返す処理
    private String SubNumber(DefaultPaidHolidayCorrectDTO defaultPaidHolidayCorrectDTO){
        PaidHolidayCorrect pc;
        //データをList型で取得
        List<PaidHolidayCorrect> x=paidHolidayCorrectMapper.getPaidHolidayCorrect(defaultPaidHolidayCorrectDTO.getCompanyCode(),defaultPaidHolidayCorrectDTO.getUserId(), defaultPaidHolidayCorrectDTO.getChangeDate());
        //データなしの時0を返す
        if(x.size()==0){
            return "0";
        }
        //データが存在するならばxの一番下のデータを取得
        int size=x.size()-1;
        pc=x.get(size);
        //データの枝番を取得してそれに１を足した値を返す
        int subNumber=Integer.parseInt(pc.getSubNumber())+1;
        String str=Integer.toString(subNumber);
        return str;
    }
    //Date型の引数をyyyyMMddのString型に変換して返す処理
    private String yyyyMMdd(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(date);
        return str;
    }
    //修正対象日の２年後を失効日としてその値を返す
    private String expireDate(DefaultPaidHolidayCorrectDTO pc , Date date){
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            //取得したString型データをDate型に変換
            date=sdf.parse(pc.getCorrectDate());
        } catch (ParseException e) {
            System.out.println("Date型に変換できません！！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 2);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        String str = sdf.format(date);
        return str;
    }
}
