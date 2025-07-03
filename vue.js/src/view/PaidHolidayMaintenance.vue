<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">               
                <div class="holidayList-area">
                    <el-row>
                        <el-col :span="10">
                            <span class="card-title-big">有休一覧</span>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 40px" >
                        <!-- 部門マスタを取得して表示 -->  
                        <el-col :span="5" class="flex-start">                            
                            <div class="select-title">　　所属：</div>
                            <el-select v-model="departmentKbnStatus" placeholder="">                                    
                                <el-option
                                    v-for="item in departmentKbnList"
                                    :key="item.departmentCode"
                                    :label="item.departmentName"
                                    :value="item.departmentCode">
                                    {{item.departmentName}} 
                                </el-option> 
                            </el-select>
                        </el-col>
                        <!--年表の項目表示-->                       
                        <el-col :span="5" class="flex-start">                           
                            <div class="select-title">年：</div>
                                <el-date-picker
                                    v-model="targetYear"
                                    type="year"
                                    :editable="false"
                                    :clearable="false">
                                </el-date-picker>
                        </el-col>
                        <!--１月から１２月までの選択項目-->                      
                        <el-col :span="5" class="flex-start">
                            <span class="select-title">月：</span>
                            <el-select v-model="monthStatus" placeholder="">
                                <el-option
                                    v-for="item in months"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                        <!--検索ボタンの表示-->  
                        <el-col :span="7" class="flex-end" style="margin-top:5px">
                                <div class="green-btn"  @click="search()">検索</div>
                        </el-col>
                        <el-col style="margin-top:20px"></el-col>                        
                     <div class="under-line" >
                    <!--有休残メンテナンスデータを取得してテーブルに表示-->
                    <!-- cellFuncのコメントアウトを解除する時に:data="tableData" に以下一行を追加↓ -->
                    <!-- :cell-class-name="cellFunc" -->
                    <el-table                  
                        :data="tableData" 
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                        style="width: 100%"
                        height="450px"   
                        @cell-click="cellClick"
                        >                        
                            <el-table-column
                                fixed                           
                                prop="userId"
                                label="ID"
                                width="50" 
                                align="center"                 
                                >                            
                            </el-table-column>
                            <el-table-column
                                fixed
                                prop="userName"
                                label="社員名"
                                width="120"
                                align="center"               
                                >
                            </el-table-column>
                            <el-table-column
                                fixed
                                prop="departmentName"
                                label="所属" 
                                width="100"                  
                                >
                            </el-table-column> 
                            <el-table-column
                                fixed
                                prop="joinDate"
                                label="入社日" 
                                width="80"                  
                                align="center"
                                >
                            </el-table-column>                            
                            <el-table-column
                                fixed
                                prop="paidHolidayStartDay"
                                label="初回有休　付与日　 " 
                                width="80"                  
                                align="center"
                                header-align="left">
                            </el-table-column>
                            <el-table-column
                                fixed
                                prop="nextPaidHolidayFlag"
                                label="入社年数" 
                                width="60"                  
                                align="right">
                            </el-table-column>
                            <el-table-column   
                                fixed
                                prop="paidHolidayRemaining"                        
                                label="現在　有休数" 
                                width="70"
                                align="right">                      
                            </el-table-column>
                            <el-table-column
                                fixed
                                label="次回有休" 
                                width="160"
                                align="center">
                                <el-table-column
                                    prop="nextPaidFullday"
                                    label="付与日" 
                                    width="80"                  
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="categoryKbnOption2"
                                    label="付与日数" 
                                    width="80"                  
                                    align="center">
                                </el-table-column>
                            </el-table-column>
                            <el-table-column label="有休日数">
                                <el-table-column 
                                    :prop="tableMonth[monthStatusIndex+0].list"
                                    :label="tableMonth[monthStatusIndex+0].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+1].list"
                                    :label="tableMonth[monthStatusIndex+1].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+2].list"
                                    :label="tableMonth[monthStatusIndex+2].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+3].list"
                                    :label="tableMonth[monthStatusIndex+3].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+4].list"
                                    :label="tableMonth[monthStatusIndex+4].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+5].list"
                                    :label="tableMonth[monthStatusIndex+5].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+6].list"
                                    :label="tableMonth[monthStatusIndex+6].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+7].list"
                                    :label="tableMonth[monthStatusIndex+7].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                :prop="tableMonth[monthStatusIndex+8].list"
                                    :label="tableMonth[monthStatusIndex+8].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+9].list"
                                    :label="tableMonth[monthStatusIndex+9].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+10].list"
                                    :label="tableMonth[monthStatusIndex+10].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                                <el-table-column
                                    :prop="tableMonth[monthStatusIndex+11].list"
                                    :label="tableMonth[monthStatusIndex+11].label"
                                    width="70"
                                    align="right">
                                </el-table-column>
                            </el-table-column>                       
                        </el-table>
                         </div>
                    </el-row>
                    <!--戻るボタンの表示-->
                    <el-row>
                        <el-col :span="3" class="flex-end" style="margin-top:5px">
                                <div class="back-btn" style="margin: 20px" @click="backToToppage()">戻る</div>
                        </el-col>
                    </el-row>
                    <!--ダイアログの表示-->
                    <el-dialog                       
                        :visible.sync="dialogmaintenance"
                        width="40%">
                        <!--テーブルにて選択した年月の前月の有休残数の表示-->
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <div style="margin-left:40px" class="select-title">{{lastMonthSelect}}末残:{{selectLastPaidHolidayRemaining}}</div>
                            </el-col> 
                        </el-row>
                        <div style="margin-top:20px"></div>
                         <!--テーブルにて選択した年月の有休修正テーブルのデータを取得してテーブルに表示-->
                        <el-table
                            :data="dialogTableData" 
                            @selection-change="handleSelectionChange"
                            height="140px">
                            <el-table-column
                                type="selection"
                                width="80"                  
                                align="center">
                            </el-table-column> 
                            <el-table-column
                                prop="correctDate"
                                label="修正対象日"
                                width="90"                  
                                align="center">
                            </el-table-column> 
                            <el-table-column
                                prop="correctKbnName"
                                label="修正区分"
                                width="90"                  
                                align="center">
                            </el-table-column> 
                            <el-table-column
                                prop="correctDays"
                                label="修正日数"
                                width="90"                  
                                align="right">                          
                            </el-table-column> 
                            <el-table-column
                                prop="changeDate"
                                label="変更日"
                                width="90"                  
                                align="right">                          
                            </el-table-column> 
                            <el-table-column
                                prop="correctReason"
                                label="修正理由"
                                width="120"                  
                                align="right">                          
                            </el-table-column> 
                            <el-table-column
                                prop="correctRemark"
                                label="修正備考"
                                width="120"                  
                                align="right">                          
                            </el-table-column> 
                        </el-table>
                        
                        <!--テーブルにて選択した年月の有休残数の表示-->
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <div style="margin-left:40px" class="select-title">{{currentMonthSelect}}末残:{{selectPaidHolidayRemaining}}</div>
                            </el-col>                      
                        </el-row>
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <div style="margin-top:20px" >追加/修正/削除を行うデータ</div>
                            </el-col> 
                        </el-row>
                        <!--追加修正削除ボタンを押下したときの処理データをテーブルに表示-->
                        <el-table
                            :data="paidHolidayUpdateInfoData"                                               
                            height="120px">  
                            <el-table-column
                                prop="statusName"
                                label="更新内容"
                                width="80"                  
                                align="right">                          
                            </el-table-column>                      
                            <el-table-column
                                prop="targetDate"
                                label="修正対象日"
                                width="90"                  
                                align="center">
                            </el-table-column> 
                            <el-table-column
                                prop="paidHolidayKbnName"
                                label="修正区分"
                                width="90"                  
                                align="center">
                            </el-table-column> 
                            <el-table-column
                                prop="paidHolidayDays"
                                label="修正日数"
                                width="90"                  
                                align="right">                          
                            </el-table-column> 
                            
                            <el-table-column
                                prop="reason"
                                label="修正理由"
                                width="120"                  
                                align="right">                          
                            </el-table-column> 
                            <el-table-column
                                prop="remark"
                                label="修正備考"
                                width="120"                  
                                align="right">  
                            </el-table-column>
                        </el-table>
                        <el-form ref="form" :rules="rules" :model="form">
                            <el-row>
                                <el-col :span="10" class="flex-start">
                                    <el-form-item prop="targetDate" label="修正対象日">
                                        <el-date-picker
                                            v-model="targetDate"
                                            type="date"
                                            :editable="false"
                                            :clearable="false">
                                        </el-date-picker>                           
                                    </el-form-item >
                                </el-col>           
                                <el-col :span="8" class="flex-start">
                                    <el-form-item prop="paidHolidayKbnStatus" label="修正区分">
                                        <el-select v-model="form.paidHolidayKbnStatus" placeholder="">                                      
                                            <el-option                                 
                                                v-for="item in paidHolidayKbnList"
                                                :key="item.categoryKbn"
                                                :label="item.categoryKbnName"
                                                :value="item.categoryKbn">
                                                {{item.categoryKbnName}} 
                                            </el-option> 
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="6" class="flex-start">
                                    <el-form-item prop="days" label="修正日数">
                                        <el-input
                                        type="number"
                                            placeholder=""
                                            v-model="form.days"
                                            min="0" 
                                            max="20"
                                            step="0.5">
                                        </el-input>
                                    </el-form-item>           
                                </el-col>
                            </el-row>  
                            <el-row>
                                <el-form-item prop="paidHolidayFixRsnTextarea" label="修正理由">
                                    <el-col :span="14" class="flex-end" style="margin-top:5px">
                                        <el-input 
                                            type="text"
                                            placeholder="入力してください"
                                            v-model="form.paidHolidayFixRsnTextarea"
                                            maxlength="20"
                                            show-word-limit>
                                        </el-input>
                                    </el-col>
                                </el-form-item>     
                            </el-row>  
                            <el-row>
                                <el-form-item  label="修正備考：">
                                    <el-col :span="14" class="flex-end" style="margin-top:5px" >
                                        <el-input 
                                            type="text"
                                            placeholder="入力してください"
                                            v-model="paidHolidayRemarksTextarea"
                                            maxlength="20"
                                            show-word-limit>
                                        </el-input>
                                    </el-col>
                                </el-form-item>
                            </el-row> 
                            <el-row v-if=" form.paidHolidayKbnStatus==='030' ">
                                <el-form-item  label="有休残_年月:">
                                    <el-col  :span="10" class="flex-end" style="margin-top:5px" >
                                        <el-date-picker
                                            type="month"
                                            v-model="targetMonth"
                                            :editable="false"
                                            :clearable="false">
                                        </el-date-picker>
                                    </el-col>
                                </el-form-item>
                            </el-row> 
                        </el-form>  
                        <span slot="footer" class="dialog-footer">
                            <el-button @click="dataInsert()">追加</el-button>
                            <el-button @click="dataUpdate()">修正</el-button>
                            <el-button @click="dataDelete()">削除</el-button>
                            <el-button type="danger" @click="dialogOut()">確定</el-button>
                        </span>
                    </el-dialog>
                </div>
            </div>   
        </div> 
    <commonFootter></commonFootter>
    </div>
</template>
<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getDepartment } from '@/api/department'
import { getPaidHolidayMaintenance,getPaidHolidayRemaining} from '@/api/paidHoliday'
import { paidHolidayCorrect,getpaidHolidayCorrect} from '@/api/paidHolidayCorrect'
import { getUserInfo} from '@/utils/auth'
import { getCategory } from '@/api/category'
import { Message } from 'element-ui';
export default {
    name: "PaidHolidayMaintenance",
    components: {
        commonHeader,
        commonFootter
    },
    created() {
      //初期処理  
        //部門取得
        this.getDepartmentKbnList()
        //テーブルヘッダーの年月表示処理
        this.changeMonth(this.monthStatusIndex)
    },   
    data() {
        return {
            departmentKbnList: [],
            departmentKbnStatus: [],
            targetYear:new Date,        //初期画面　年項目
            targetDate:new Date,        //ダイアログ画面　日付項目
            targetMonth:new Date,       //ダイアログ画面　有休残_年月項目
            setYear:new Date,           //検索ボタン押下時のtargetYearを保存
            tableData:[],
            dialogTableData:[],
            paidHolidayUpdateData:[],
            paidHolidayUpdateInfoData:[],
            days:'',                    //修正日数
            paidHolidayRemarksTextarea:'',//備考
            dialogmaintenance: false,   //選択時のダイアログ表示      
            lastMonthSelect:'',
            currentMonthSelect:'',            
            paidHolidayKbnStatus:[],
            paidHolidayKbnList:[],
            multipleSelection:[],
            monthStatus:'0',
            //選択した箇所のデータ
            selectRowData:[],
            selectColumnData:[],
            selectYear:'',
            selectMonth:'',
            selectYm:'', 
            selectLastYm:'',
            selectPaidHolidayRemaining:0,
            selectLastPaidHolidayRemaining:0,
            //ユーザー単位の有休残データ
            tableMonth:[{list:'janCrt',label:''},
                        {list:'febCrt',label:''},
                        {list:'marCrt',label:''},
                        {list:'aplCrt',label:''},
                        {list:'mayCrt',label:''},
                        {list:'junCrt',label:''},
                        {list:'julCrt',label:''},
                        {list:'augCrt',label:''},
                        {list:'sepCrt',label:''},
                        {list:'octCrt',label:''},
                        {list:'novCrt',label:''},
                        {list:'decCrt',label:''},
                        {list:'janNext',label:''},
                        {list:'febNext',label:''},
                        {list:'marNext',label:''},
                        {list:'aplNext',label:''},
                        {list:'mayNext',label:''},
                        {list:'junNext',label:''},
                        {list:'julNext',label:''},
                        {list:'augNext',label:''},
                        {list:'sepNext',label:''},
                        {list:'octNext',label:''},
                        {list:'novNext',label:''},
                        {list:'decNext',label:''}],
            //１月から１２月までの選択項目のデータ
            months:[{value: '0',label: '1月'},
                    {value: '1',label: '2月'},
                    {value: '2',label: '3月'},
                    {value: '3',label: '4月'},
                    {value: '4',label: '5月'},
                    {value: '5',label: '6月'},
                    {value: '6',label: '7月'},
                    {value: '7',label: '8月'},
                    {value: '8',label: '9月'},
                    {value: '9',label: '10月'},
                    {value: '10',label: '11月'},
                    {value: '11',label: '12月'}],
            monthStatusIndex:0,
            
            form:{
                
                 paidHolidayKbnStatus:'',
                days:'0',
                 paidHolidayFixRsnTextarea:'',
            },
            rules: {
                    paidHolidayKbnStatus: [
                            { required: true, message: '修正区分を選択してください', trigger: 'blur' }
                    ],
                    days:[
                            { required: true, message: '修正日数を入力してください', trigger: 'blur' }
                    ],
                     paidHolidayFixRsnTextarea: [
                            { required: true, message: '修正理由を入力してください', trigger: 'blur' }
                    ],
                },
        }
    },
    methods: {
        //メンテナンステーブルのセルの表示を変更
    //     cellFunc({row,column,rowIndex,columnIndex}){
    //        if(columnIndex>8){
    //         return 'cellFunc'
    //        }          
    //    },
        //メンテナンステーブルの表整理
        async getPaidHolidayMaintenanceListOrg(){
            //メンテナンステーブルの取得処理
            let kariData1=await this.getPaidHolidayMaintenanceList()         
            let kariData2=[]
            //条件ごとに分ける           
            for(let i=0;i<kariData1.length;i++) {
               //部門毎に絞り込み
                if(this.departmentKbnStatus=='')kariData2.push(kariData1[i])
                else if(this.departmentKbnStatus==kariData1[i].departmentCode)kariData2.push(kariData1[i])
                else{}                       
            }
            //テーブル整形
             for(let i=0;i<kariData2.length;i++) {
                kariData2[i].joinDate=this.arrangeYmd(kariData2[i].joinDate)
                kariData2[i].paidHolidayStartDay=this.arrangeYmd(kariData2[i].paidHolidayStartDay)
                kariData2[i].nextPaidFullday=this.arrangeYmd(kariData2[i].nextPaidFullday)
                }
            this.tableData=kariData2
        },
        //検索ボタン押下時の処理
            search(){         
            //メンテバンステーブル表示処理            
            this.getPaidHolidayMaintenanceListOrg()
            //月を特定
            this.monthStatusIndex=Number(this.monthStatus)
            //年を設定
            this.setYear=this.targetYear
            //テーブルヘッダーの年月表示処理
            this.changeMonth(this.monthStatusIndex)
        },
        //戻るボタン押下時の処理
        backToToppage() {
            this.$router.push({               
                path: '/maintenance'                  
            })
  
        }, 
        
         //ダイアログ遷移時の処理
        // dialogIn(row,column){
        //         //ダイアログ起動       
        //         this.dialogmaintenance=true
        //         //選択セルのデータを取得 
        //         this.selectRowData=row
        //         this.selectColumnData=column
        //         //追加/修正/削除テーブルの初期化
        //         this.paidHolidayUpdateInfoData.splice(0)
        //         //データの初期化               
        //         this.paidHolidayUpdateData.splice(0)
        //         //選択月の取得
        //         this.getSelectYm(column)              
        //         //有休区分取得(有休付与、有休消化)
        //         this.getpaidHolidayKbn()                
        //         //選択月のユーザーごとの有休修正テーブルを取得
        //         this.getpaidHolidayCorrectList()
        //         //有休残日数を取得
        //         this.getPaidHolidayRemainingList()
            
        // },
        //値代入
        forminputfunc(){
            this.paidHolidayKbnStatus=this.form.paidHolidayKbnStatus
            this.days=this.form.days
        },
        //選択月、選択前月、選択年の取得
        getSelectYm(column){
            //選択月の取得
            this.selectMonth=this.SelectM(column)
            //選択年の所得
            this.selectYear=this.selectY(column)
            //選択年月
            this.selectYm=this.selectYear+this.selectMonth
            //修正対象日の初期化
            let kariTargetDate=new Date
            kariTargetDate.setFullYear(this.selectYear)
            kariTargetDate.setMonth(this.selectMonth-1)
            this.targetDate=kariTargetDate
            //選択月の前月計算
            let kariLastYear
            let kariLastMonth
            if(this.selectMonth=='01'){
                this.lastMonthSelect='12'+'月'
                this.currentMonthSelect=this.selectMonth-0+'月'
                kariLastYear=this.selectYear-1
                kariLastMonth='12'
            }
            else{
                this.lastMonthSelect=this.selectMonth-1+'月'
                this.currentMonthSelect=this.selectMonth-0+'月'
                kariLastYear=this.selectYear
                kariLastMonth=this.selectMonth-1
                kariLastMonth='0'+kariLastMonth
                kariLastMonth=kariLastMonth.slice(-2)
            }
            this.selectLastYm=kariLastYear.toString()+kariLastMonth.toString()
        },
        selectY(column){
            let kariyear
            if(column.property.slice(3)=='Crt')kariyear=this.dateY(this.setYear)+0
            else if(column.property.slice(3)=='Next')kariyear=this.dateY(this.setYear)+1
            else{return}
            return kariyear
        },
        SelectM(column){
            let kariMonth
            if(column.property.slice(0,3)=='jan')kariMonth='01'
            else if(column.property.slice(0,3)=='feb')kariMonth='02'
            else if(column.property.slice(0,3)=='mar')kariMonth='03'
            else if(column.property.slice(0,3)=='apl')kariMonth='04'
            else if(column.property.slice(0,3)=='may')kariMonth='05'
            else if(column.property.slice(0,3)=='jun')kariMonth='06'
            else if(column.property.slice(0,3)=='jul')kariMonth='07'
            else if(column.property.slice(0,3)=='aug')kariMonth='08'
            else if(column.property.slice(0,3)=='sep')kariMonth='09'
            else if(column.property.slice(0,3)=='oct')kariMonth='10'
            else if(column.property.slice(0,3)=='nov')kariMonth='11'
            else if(column.property.slice(0,3)=='dec')kariMonth='12'            
            else {return}
            return kariMonth
        },
        // チェックボックス選択時の処理
        handleSelectionChange(val) {
            if(val.length==2){
                val.pop()
                    Message({
                        message: "複数選択はできません",
                        type: 'error',
                        duration: 2 * 1000
                    })   
                }
            this.multipleSelection = val
           
        },
        //追加ボタン押下時の処理
        dataInsert(){
            this.forminputfunc()
            this.$refs["form"].validate((valid) => {
            if (valid) {
                let data={
                    companyCode:this.selectRowData.companyCode,
                    userId:this.selectRowData.userId,
                    targetDate:this.dateYmd(this.targetDate),
                    paidHolidayOpeKbn:this.paidHolidayKbnStatus,
                    paidHolidayKbnName:'',
                    paidHolidayDays:this.days,
                    reason:this.form.paidHolidayFixRsnTextarea,
                    remark:this.paidHolidayRemarksTextarea,
                    targetMonth:this.dateYm(this.targetMonth),
                    status:'1',
                    statusName:'追加'
                }
            this.pushData(data)
            }
            else{
                console.log('error submit!!');
                    return false;
                }
            }); 
       },
        //修正ボタン押下時の処理//
        dataUpdate(){  
            if(this.multipleSelection.length==0){
                Message({
                    message: "対象のデータが選択されていません",
                    type: 'error',
                    duration: 2 * 1000
                }) 
                }  
            else{   
                //修正区分が異なるものを修正しようとしたときエラーを返す     
                if(this.multipleSelection[0].correctKbn!=this.form.paidHolidayKbnStatus){
                     Message({
                    message: "修正区分が異なる修正は行えません",
                    type: 'error',
                    duration: 2 * 1000
                }) 
                }
                else{ 
                    this.forminputfunc()
                    this.$refs["form"].validate((valid) => {
                        if (valid) {
                            let data={
                                companyCode:this.selectRowData.companyCode,
                                userId:this.selectRowData.userId,
                                targetDate:this.dateYmd(this.targetDate),
                                
                                paidHolidayOpeKbn:this.multipleSelection[0].correctKbn,
                                paidHolidayKbnName:'',
                                paidHolidayDays:this.days,
                                reason:this.form.paidHolidayFixRsnTextarea,
                                remark:this.paidHolidayRemarksTextarea,
                                targetMonth:this.dateYm(this.targetMonth),
                                status:'2',
                                statusName:'修正'
                            }
                            this.pushData(data)
                        }
                        else{
                            console.log('error submit!!');
                            return false;
                        }
                    }); 
                }          
            }
        },
        //削除ボタン押下時の処理
        dataDelete(){           
            if(this.multipleSelection.length==0){
                Message({
                    message: "対象のデータが選択されていません",
                    type: 'error',
                    duration: 2 * 1000
                }) 
                } 
            else{  
                
                this.forminputfunc()
                let data={
                    companyCode:this.multipleSelection[0].companyCode,
                    userId:this.multipleSelection[0].userId,
                    targetDate:this.multipleSelection[0].changeDate,
                    paidHolidayOpeKbn:this.multipleSelection[0].correctKbn,
                    paidHolidayKbnName:'',
                    paidHolidayDays:this.multipleSelection[0].paid,
                    reason:this.multipleSelection[0].correctReason,
                    remark:this.multipleSelection[0].correctRemark,
                    targetMonth:this.dateYm(this.targetMonth),
                    status:'3',
                    statusName:'削除'
                }
            this.pushData(data)
            }
        },
        pushData(data){
            data.paidHolidayKbnName= this.paidHolidayKbnName(data.paidHolidayOpeKbn,data.paidHolidayKbnName)
            this.paidHolidayUpdateData.splice(0)
            this.paidHolidayUpdateInfoData.splice(0)
            this.paidHolidayUpdateData.push(data)   
            this.paidHolidayUpdateInfoData.push(data)
        },
        //対象ユーザーの有休残テーブルを全て呼び出し休残テーブルの対象月以上のもの抽出しそれを更新する
        //確定選択時の処理
        dialogOut(){        
            if(this.paidHolidayUpdateData==''){
                Message({
                    message: "操作するデータが登録されていません",
                    type: 'error',
                    duration: 2 * 1000
                }) 
            }
            else{
            this.dialogmaintenance=false          
            //データベース更新
            this.paidHolidayCorrectList()  
            }         
        },
        //部門取得
        getDepartmentKbnList(){
                let data ={companyCode:getUserInfo().companyCode}
                getDepartment(data).then(res => {
                    console.log(res)
                    this.departmentKbnList = res.data
                     let blockArray = { departmentCode: '', departmentName: '全て'}
                    this.departmentKbnList.unshift(blockArray)
                }).catch(error => {
                })                
        },
        
        
        //有休修正テーブルを取得
        getpaidHolidayCorrectList(){
            let data={
                companyCode:this.selectRowData.companyCode,
                userId:this.selectRowData.userId,
                dateYm:this.selectYear+this.selectMonth
            }
            getpaidHolidayCorrect(data).then(res => { 
                 console.log(res)
                    this.dialogTableData= res.data
                    for(let i=0;i<this.dialogTableData.length;i++){
                    this.dialogTableData[i].correctKbnName= this.paidHolidayKbnName(this.dialogTableData[i].correctKbn,this.dialogTableData[i].correctKbnName)
                    }
                }).catch(res => {
                    console.log(res.data)
                }) 
        },
        
        //データベース更新処理            
        paidHolidayCorrectList(){
                let data
                if(this.paidHolidayUpdateData[0].status=='1'){
                    data={
                        companyCode:this.paidHolidayUpdateData[0].companyCode,
                        userId:this.paidHolidayUpdateData[0].userId,
                        changeDate:null,
                        subNumber:null,
                        correctDate:this.paidHolidayUpdateData[0].targetDate,
                        correctKbn:this.paidHolidayUpdateData[0].paidHolidayOpeKbn,
                        correctDays:this.paidHolidayUpdateData[0].paidHolidayDays,
                        correctReason:this.paidHolidayUpdateData[0].reason,
                        correctRemark:this.paidHolidayUpdateData[0].remark,      
                        registrationUser:getUserInfo().userId,
                        updateUser:getUserInfo().userId,
                        dateYm:this.paidHolidayUpdateData[0].targetMonth,
                        assignmentYear:null,
                        updateDays:this.paidHolidayUpdateData[0].paidHolidayDays,
                        status:this.paidHolidayUpdateData[0].status
                    }
                    console.log(data)
                }
                else if(this.paidHolidayUpdateData[0].status=='2'){
                        data={
                        companyCode:this.paidHolidayUpdateData[0].companyCode,
                        userId:this.paidHolidayUpdateData[0].userId,
                        changeDate:this.multipleSelection[0].changeDate,
                        subNumber:this.multipleSelection[0].subNumber,
                        correctDate:this.paidHolidayUpdateData[0].targetDate,
                        correctKbn:this.multipleSelection[0].correctKbn,
                        correctDays:this.paidHolidayUpdateData[0].paidHolidayDays,
                        correctReason:this.paidHolidayUpdateData[0].reason,
                        correctRemark:this.paidHolidayUpdateData[0].remark,      
                        registrationUser:getUserInfo().userId,
                        updateUser:getUserInfo().userId,
                        dateYm:this.paidHolidayUpdateData[0].targetMonth,
                        assignmentYear:null,
                        updateDays:this.paidHolidayUpdateData[0].paidHolidayDays,
                        status:this.paidHolidayUpdateData[0].status                       
                    }
                }
                else if(this.paidHolidayUpdateData[0].status=='3'){
                    data={
                        companyCode:this.multipleSelection[0].companyCode,
                        userId:this.multipleSelection[0].userId,
                        changeDate:this.multipleSelection[0].changeDate,
                        subNumber:this.multipleSelection[0].subNumber,
                        correctKbn:this.multipleSelection[0].correctKbn,
                        status:this.paidHolidayUpdateData[0].status
                    }
                }        
            paidHolidayCorrect(data).then(res => { 
                     console.log(res) 
                     if(res.status==200){
                        Message({
                            message: res.msg,
                            type: 'success',
                            duration: 4 * 1000
                        })}
                    else {
                        Message({
                            message: res.msg,
                            type: 'error',
                            duration: 4 * 1000
                        })}    
                        
                }).catch(res => {
                        Message({
                        message: res,
                        type: 'error',
                        duration: 4 * 1000
                    })                 
                }) 
            },
        //有休残メンテナンステーブル取得
        async getPaidHolidayMaintenanceList(){           
            let data = {  
                companyCode:getUserInfo().companyCode , 
                dateY:this.dateY(this.setYear)  ,                 
            } 
            
            let returnData= await getPaidHolidayMaintenance(data).then(res => {
                    console.log(res)
                    return res.data
                }).catch(res => {
                    console.log(res)     
                })
            return returnData    
        },
        //今月末有休数取得
        getPaidHolidayRemainingList(){
             let data={
                companyCode:this.selectRowData.companyCode,
                userId:this.selectRowData.userId,
                dateYm:this.selectYm,
                assignmentYear:this.selectYear
             }
            getPaidHolidayRemaining(data).then(res => {   
                    console.log(res)     
                    this.selectPaidHolidayRemaining=res.data    
                    }).catch(res => {
                        console.log(res)                     
                })
            //前月末有休数取得
            let data2={
                companyCode:this.selectRowData.companyCode,
                userId:this.selectRowData.userId,
                dateYm:this.selectLastYm,
                assignmentYear:this.selectLastYm.slice(0,4)
            }
            getPaidHolidayRemaining(data2).then(res => {   
                console.log(res)     
                this.selectLastPaidHolidayRemaining=res.data    
            }).catch(res => {
                console.log(res)                     
            })
        },
        
            //テーブルYY/MM入力処理
        changeMonth(monthIndex){          
             let year
            for(let i=0;i<12;i++){
                let tag=monthIndex+i
                let tag2=tag%12+1
                tag2='0'+tag2.toString()
                tag2=tag2.slice(-2)
            if(tag<12)year=this.dateY(this.setYear)
            else if(tag>=12)year=this.dateY(this.setYear)+1
                this.tableMonth[tag].label=year+tag2
                this.tableMonth[tag].label=this.tableMonth[tag].label.slice(-4)
                this.tableMonth[tag].label=this.arrangeDate(this.tableMonth[tag].label)           
           }  
        },

        
         //有休区分取得
        getpaidHolidayKbn(){
            let data={companyCode:getUserInfo().companyCode,
            categoryClassNumber: '049'}
            console.log(data)
            getCategory(data).then(res => {
                    console.log(res)

                    this.paidHolidayKbnList = res.data
                    this.paidHolidayKbnList.shift()
                    this.paidHolidayKbnList.pop()

                    for(let i=0;i<this.paidHolidayKbnList.length;i++){
                   this.paidHolidayKbnList[i].categoryKbnName= this.paidHolidayKbnName(this.paidHolidayKbnList[i].categoryKbn,this.paidHolidayKbnList[i].categoryKbnName)
                    }
                    }).catch(res => {
                    console.log(res)
                }) 
        },
        paidHolidayKbnName(x , y){  
            if(x=='020') y='有休消化'
            else if(x=='030') y='有休付与'            
            return y
        },
       
        
        
        //選択した日付をデータに変換
        dateY(date){
            let yyyy = ''
            yyyy = date.getFullYear()
            return yyyy           
        },
        dateYm(date){
            let yyyymm = ''
            yyyymm = date.getFullYear() +('0' + (date.getMonth()+1)).slice(-2)
            return yyyymm            
        },
        dateYmd(date){
            let yyyymmdd = ''
            yyyymmdd = date.getFullYear() +('0' + (date.getMonth()+1)).slice(-2)+('0' + (date.getDate())).slice(-2)
            return yyyymmdd            
        },
        //セルをクリックしたときの処理
        cellClick(row,column,cell,event){
            //有休日数のセルの場合に実行される
            if(column.property.slice(3)=='Crt'||column.property.slice(3)=='Next'){  
                this.dialogIn(row,column)
            }
        },
        
               
        //yyyymmdd:yy/mm/dd
        arrangeYmd(date){
            if(date==null){
                return date
            }
            date=date.slice(2,4)+'/'+date.slice(4,6)+'/'+date.slice(6,8)
            return date
        },
        //スラッシュ挿入(年月日)
        arrangeDate(date){
            if(date==null){
                return date
            }
            if(date.length==4){
                date=date.slice(0,2)+'/'+date.slice(2,4)               
                return date
            }
            if(date.length==6){
                date=date.slice(0,2)+'/'+date.slice(2,4)+'/'+date.slice(4,6)
                return date
            }
        },  
    }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';
.holidayList-area {
    height: 100%;
    width: 100%;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 30px 30px;
    border-radius: 6px;

    .holidayList-header {
        padding: 0px 30px;
    }
}
.select-title {
    font-weight: bold;
    white-space: nowrap;
    font-size: 16px;
    margin-top: 8px;
}
.searchUserInfoArea {
    height: auto;
    max-height: 250px;
    overflow-y: scroll;
}
.searchUserInfo-btn {
    width: 440px;
    height: 60px;
    box-shadow: 0 2px 10px 0 rgba(92, 92, 92, 0.2);
    background-color: #fff;
    border-radius: 6px;
    padding: 10px 20px;
    margin: 6px 10px 14px;
   
    border: 2px #fff solid;

    i {
        font-size: 18px;
        color: #888;
    }
}
.el-table .focus-cell{
background:rgb(255, 0, 0) ;

}
.searchUserInfo-btn-largeVer {
    width: 440px;
    height: 150px;
    box-shadow: 0 2px 10px 0 rgba(92, 92, 92, 0.2);
    background-color: #fff;
    border-radius: 6px;
    padding: 10px 20px;
    margin: 6px 10px 14px;
   
    border: 2px #fff solid;
    i {
        font-size: 18px;
        color: #888;
    }

}
// .el-table .cellFunc{

//     text-decoration: underline;
//     color:#409EFF;
//     cursor: hand; 
//     cursor:pointer;
// }
</style>