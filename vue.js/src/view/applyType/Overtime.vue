<template>
    <div class="apply-area">
        <!-- <apply-header title="深夜残業申請"></apply-header> -->
        <applyHeader title="深夜残業申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <div class="apply-leave">
            <el-form ref="form" :rules="rules" :model="form" label-width="120px">
                <el-form-item label="申請種類">
                    <el-select  v-model="form.selectOvertime" :disabled="true" style="width: 310px">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in ApplyKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="残業予定日" prop="overtimeDay">
                    <el-date-picker
                        v-model="form.overtimeDay"
                        type="date"
                        placeholder="YYYY/MM/DD"
                        format="yyyy/MM/dd"
                        style="width: 310px"
                        :disabled="inputOff">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="予定時間" required>
                    <el-col :span="11">
                        <el-form-item prop="startTime">
                            <el-time-select
                                v-model="form.startTime"
                                format="HH:mm"
                                value-format="HH:mm:ss"
                                placeholder="開始時間"
                                style="width: 144px"
                                :disabled="inputOff"
                                :picker-options="{
                                    start: '22:00',
                                    step: '00:15',
                                    end: form.endTime == null || form.endTime == '' ? '28:45': floorTime(form.endTime)
                                }">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                    <el-col class="wavy-line" :span="2">~</el-col>
                    <el-col :span="11">
                        <el-form-item prop="endTime" class="end-time">
                            <el-time-select
                                v-model="form.endTime"
                                format="HH:mm"
                                value-format="HH:mm:ss"
                                placeholder="終了時間"
                                style="width: 144px"
                                :disabled="inputOff"
                                :picker-options="{
                                    start: form.startTime == null || form.startTime == '' ? '22:15': ceilTime(form.startTime),
                                    step: '00:15',
                                    end: '29:00'
                                }">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <div style="display: flex;">
                    <div v-if="'24:00' < form.startTime" class="start-tomorrow-text">翌日 {{ tomorrowTime(form.startTime) }}</div>
                    <div v-if="'24:00' < form.endTime" class="end-tomorrow-text">翌日 {{ tomorrowTime(form.endTime) }}</div>
                </div>
                <el-form-item label="予定時間数">
                    <span>{{ timeDiff() }}</span>
                </el-form-item>
                <el-form-item label="残業理由" prop="overtimeReason">
                    <el-select v-model="form.overtimeReason" placeholder="残業理由区分" style="width: 310px" :disabled="inputOff">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in overtimeKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="備考">
                    <el-input
                        type="textarea"
                        v-model="form.overtimeColumn"
                        maxlength="200"
                        class="textarea-box"
                        :disabled="inputOff">
                    </el-input>
                </el-form-item>
            </el-form>

            <div class="leave-btn">
                <el-row>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 10 : 6" class="flex-start">
                        <div class="gray-btn" @click="onDeleteConfirmClick()" v-if="delFlg">削除</div>
                        <div class="itirann-btn" @click="closeA()" v-if="changebyFlg == '2'">申請一覧に戻る</div>
                        <div class="itirann-btn" @click="closeB()" v-if="changebyFlg == '3'">承認一覧に戻る</div>
                    </el-col>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 12 : 18" class="flex-end">
                        <!-- <div class="green-btn" @click="save()" v-if="modifyFlgsave" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div> -->
                        <div class="blue-btn" @click="OpenGetBackDialog()" v-if="getBack">取戻</div>
                        <div class="red-btn" @click="remandDialog()" v-if="changebyFlgwf" style='margin-right: 30px'>差戻</div>
                        <div class="green-btn" @click="applyDialog(1)" v-if="changebyFlgapp">承認</div>
                    </el-col>    
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 14 : 24" class="flex-end">
                        <div class="green-btn" @click="save()" v-if="modifyFlgsave" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 削除のポップアップ -->
        <el-dialog title="申請を削除しますか。"
            :visible.sync="deleteConfirmDialogVisible"
            width="30%"
            center>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteOvertimeDate()">はい</el-button>
                <el-button @click="deleteConfirmDialogVisible = false">いいえ</el-button>
            </div>
        </el-dialog>
        <approval v-bind:userData="userData" :requestData="requestData" ref="approvalDialog" @dataRegistration='approvalSave'></approval>
        <remand v-bind:dateList="form.overtimeDay" ref="onRemandDialog"></remand>
        <getBackDoalog ref="ongetBackDoalog"></getBackDoalog>
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import approval from "../Approval.vue";
    import remand from "../Remand.vue";
    import getBackDoalog from "../GetBackDialog.vue";
    import { getCategory } from '@/api/category'
    import { insertData,getRequestOvertime,deleteOvertime,overtimeDayConfirm } from '@/api/overTime'
    import { getSelectWf, getWorkFlowList } from '@/api/workFlow'
    import { Message } from 'element-ui';
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest } from '@/api/requestList'
    import { selectTgtMonth } from '@/api/monthCloseHistory';

    export default {
        props: ['userData'],
        name: "Overtime",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog,
        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.getCurrentTime()
            this.setReqNum()
            // 申請状態のオプション
            let StatusKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '030'
            }
            getCategory(StatusKbnData).then(res => {
                console.log(res)
                this.StatusKbnList = res.data
            }).catch(error => {
                console.log(error)
            })
            // 申請種類のオプション
            let ApplyKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '031'
            }
            getCategory(ApplyKbnData).then(res => {
                console.log(res)
                this.ApplyKbnList = res.data
            }).catch(error => {
                console.log(error)
            })
            // 残業理由区分のオプション
            let overtimeKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '046',
            }
            getCategory(overtimeKbnData).then(res => {
                console.log(res)
                this.overtimeKbnList=res.data
            }).catch(error => {
                console.log(error)
            })
            this.statusSetBtn()
            if(this.changebyFlg=="2"||this.changebyFlg=="3"){
                this.statusSetOvertime()
            }
        },

        data() {
            return {
                form: {
                    //深夜残業
                    selectOvertime: '022',
                    // 残業日付選択
                    overtimeDay: '',
                    // 残業開始時間
                    startTime: '',
                    // 残業終了時間
                    endTime: '',
                    // 残業予定時間
                    //workingOvertime: '',
                    //残業理由
                    overtimeReason: '',
                    // 残業備考
                    overtimeColumn: '',
                },
                rules: {
                    overtimeDay: [{ required: true, message: '残業予定日は入力必須です。', trigger: 'blur' }],
                    startTime: [{ required: true, message: '開始時間は入力必須です。', trigger: 'blur' }],
                    endTime: [{ required: true, message: '終了時間は入力必須です。', trigger: 'blur' }],
                    overtimeReason: [{ required: true, message: '残業理由は入力必須です。', trigger: 'change' }],
                },
                // 詳細:申請番号
                reqNumber: '',
                loading: false,
                reqNum: '',
                changebyFlg: '',
                rowdata: [],
                aTime: '',
                yMd: '',
                reSaveOvertimeDay: '',
                ApplyKbnList: {index: '',categoryKbnName: '' },
                // 残業理由区分
                overtimeKbnList: {index: '',categoryKbnName: '' },
                deleteConfirmDialogVisible: false,
                requestData: [],
                reqFlg: true,
                delFlg: true,
                getBack: false,
                modifyFlgsave: true,
                changebyFlgapp: true,
                changebyFlgwf: true,
                inputOff: false,
            }
        },

        methods: {
            getCurrentTime() {
                let yy = String(new Date().getFullYear())
                let mm = String((new Date().getMonth()+1) <10 ? '0' + (new Date().getMonth()+1) : (new Date().getMonth()+1))
                let dd = String(new Date().getDate() <10 ? '0' + new Date().getDate() : new Date().getDate());
                let hh = String(new Date().getHours() <10 ? '0' + new Date().getHours() : new Date().getHours());
                let mf = String(new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes());
                let ss = String(new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds());
                let ms = String(new Date().getMilliseconds()<10 ? '00'+new Date().getMilliseconds() : new Date().getMilliseconds()<100 ? '0'+new Date().getMilliseconds() : new Date().getMilliseconds());
                //this.aTime = yy+mm+dd+hh+mf+ss;
                this.aTime = yy+mm+dd+hh+mf+ss+ms;
                this.yMd = yy+mm+dd
            },
            //深夜残業時間用の時間フォーマット
            setTime(date){
                let data = new Date(date)
                //hhmmss
                let formatDate = 22 <=  data.getHours() ? ("0"+data.getHours()).slice(-2)+":"+("0"+data.getMinutes()).slice(-2) : ("0"+(data.getHours()+24)).slice(-2)+":"+("0"+data.getMinutes()).slice(-2) ;
                return formatDate
            },
            //深夜残業時間用の日付フォーマット
            setDate(date){
                //yyyyMMdd
                let selectDate =date.getFullYear()+("0"+(date.getMonth()+1)).slice(-2)+("0"+date.getDate()).slice(-2)
                return selectDate
            },
            //深夜残業日付時間用の日付フォーマット
            setDateTime(date,time){
                //yyyy/MM/dd hh:mm:ss
                let selectDate = date.getFullYear()+"/"+("0"+(date.getMonth()+1)).slice(-2)+"/"+("0"+date.getDate()).slice(-2)
                let dateTime = '24:00' >= time ? new Date(selectDate+" "+time) : this.tomorrowDate(selectDate,time)
                console.log(dateTime)
                return dateTime
            },
            statusSetOvertime(){
                let data ={
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                getRequestOvertime(data).then(res => {
                    console.log(res)
                    console.log(res.data)
                    //申請番号
                    this.reqNumber = res.data[0].requestNumber
                    console.log('reqNumber',this.reqNumber)
                    //残業予定日
                    this.form.overtimeDay = new Date(res.data[0].scheduledOvertimeDay.substr(0,4)+"/"+res.data[0].scheduledOvertimeDay.substr(4,2)+"/"+res.data[0].scheduledOvertimeDay.substr(6,2))
                    //再保存チェック用の残業予定日
                    this.reSaveOvertimeDay = res.data[0].scheduledOvertimeDay
                    //開始時間
                    this.form.startTime = this.setTime(res.data[0].startTime)
                    //終了時間
                    this.form.endTime = this.setTime(res.data[0].endTime)
                    //残業理由
                    this.form.overtimeReason = res.data[0].overtimeReasonKbn
                    //備考
                    this.form.overtimeColumn = res.data[0].remark
                    console.log(this.form.overtimeDay)
                }).catch(error => {
                    console.log(error)
                })
            },
            statusSetBtn(){
                if(this.changebyFlg == "1"){
                    this.delFlg = false
                    this.getBack = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.inputOff = false

                }else if(this.changebyFlg == "2"){
                    if(this.rowdata.requestStatusKbn == "003"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                    }
                    if(this.rowdata.requestStatusKbn == "003"&&this.userData.userId==this.rowdata.requestUserId){
                        this.getBack = true
                    }
                    if(this.rowdata.requestStatusKbn == "999"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                    }
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                }else if(this.changebyFlg == "3"){
                    if(this.rowdata.wfStatusKbn =="020"){
                        this.changebyFlgwf = true
                        this.changebyFlgapp = true
                    }else if (this.rowdata.wfStatusKbn == "021") {
                        this.changebyFlgwf = false
                        this.changebyFlgapp = false
                    }else if (this.rowdata.wfStatusKbn =="025"|| this.rowdata.wfStatusKbn =="900") {
                        this.changebyFlgapp = false
                    }
                    this.delFlg = false
                    this.modifyFlgsave = false
                    this.reqFlg = false
                    this.getBack = false
                    this.inputOff = true
                }
            },
            save () {
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        //ステータス区分：保存
                        console.log(this.ApplyKbnList)
                        this.getCurrentTime()
                        this.overlappingConfirm()
                    }
                })
            },
            approvalSave(number) {
                //ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                console.log(this.reqNum)
                this.overtimeSave()
            },
            //深夜残業テーブル保存処理
            overtimeSave(){
                console.log(this.timeDiff())
                let data = {
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId:  this.userData.userId,
                    workTypeKbn: this.form.selectOvertime,
                    scheduledOvertimeDay: this.setDate(this.form.overtimeDay),
                    startTime: this.setDateTime(this.form.overtimeDay,this.form.startTime),
                    endTime: this.setDateTime(this.form.overtimeDay,this.form.endTime),
                    estimatedWorkHours: new Date("2000/01/01 "+this.timeDiff()),
                    overtimeReasonKbn: this.form.overtimeReason,
                    remark: this.form.overtimeColumn
                }
                console.log(data)
                insertData(data).then(res => {
                    console.log(res)
                    Message({
                        message: '深夜残業申請を保存しました。',
                        type: 'success',
                        duration: 4 * 1000
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            overlappingConfirm(){
                let data = {
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId:  this.userData.userId,
                    workTypeKbn: this.form.selectOvertime,
                    scheduledOvertimeDay: this.setDate(this.form.overtimeDay),
                    startTime: this.setDateTime(this.form.overtimeDay,this.form.startTime),
                    endTime: this.setDateTime(this.form.overtimeDay,this.form.endTime),
                    estimatedWorkHours: new Date("2000/01/01 "+this.timeDiff()),
                    overtimeReasonKbn: this.form.overtimeReason,
                    remark: this.form.overtimeColumn
                }
                console.log(data)
                overtimeDayConfirm(data).then(res => {
                    console.log(res)
                    console.log(this.rowdata)
                    //日付が重複する休暇申請、深夜残業申請がないかをチェックするフラグ
                    let registFlg = 0 >= Number(res.data) ? true : false;
                    //日付を変更せずに再保存を行う時のフラグ
                    let sameDayFlg = false
                    if(this.changebyFlg >= '2'){
                        sameDayFlg = (this.userData.userId==this.rowdata.requestUserId&&this.setDate(this.form.overtimeDay)==this.reSaveOvertimeDay)
                    }
                    if(registFlg||sameDayFlg){
                        this.overtimeSave()
                        this.requestSaveData()
                        console.log(this.requestData)
                        this.requestSave(this.requestData)
                        setTimeout(()=>{
                            this.$router.go({path: this.$router.currentRoute.path, force: true})
                        },200)
                    }else{
                        Message({
                            message: '登録を行う残業予定日と重複したデータが存在します。',
                            type: 'error',
                            duration: 4 * 1000
                        })
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            //申請テーブルの処理
            requestSaveData(){
                this.requestData = {
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId : this.userData.userId,
                    requestDate: this.yMd,
                    requestTypeKbn: this.form.selectOvertime,
                    requestOverview: this.numSearch(this.form.selectOvertime),
                    requestStatusKbn: '001'
                }
            },
            requestSave(data){
                insertLeaveRequest(data).then(res => {
                console.log(data)
                console.log(res)
                }).catch(error => {
                })
            },
            //深夜残業申請内容テーブルのレコード削除
            deleteOvertimeDate(){
                this.deleteReq()
                let data ={
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                    requestUserId: this.rowdata.requestUserId
                }
                deleteOvertime(data).then(res => {
                    console.log(res)
                }).catch(error => {
                    console.log(error)
                })
                Message({
                    message: '申請を削除しました。',
                    type: 'success',
                    duration: 1500
                })
                this.deleteConfirmDialogVisible = false
                setTimeout(()=>{
                    this.$router.go({path: this.$router.currentRoute.path, force: true})
                },200)
            },
            //申請テーブルのレコード削除
            deleteReq(){
                let datadeletereq = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                };
                deleteRequest(datadeletereq)
                    .then((res) => {
                    console.log(res);
                })
                .catch((error) => {});
            },
            floorTime(time) {
                if(time == '') {
                    return ''
                }
                let timeSum = this.minuteChange(time) - 15
                if(timeSum % 15 == 0) {
                   return this.minuteFormat(timeSum)
                }else {
                  return this.minuteFormat(timeSum - (timeSum % 15))
                }
            },
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime +  this.userData.companyCode +  this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            numSearch(number) {
                for(let i = 0; i < this.ApplyKbnList.length;i++) {
                    if(this.ApplyKbnList[i].categoryKbn == number) {
                        return this.ApplyKbnList[i].categoryKbnName
                    }
                }
            },
            // 时间字符串转化为数字类型分钟
            minuteChange(time) {
                var time_h;
                var time_m;
              var minute;
                if(time == '' || time == null) {
                    return 0
                }
                time_h = time.split(':')[0];
                time_m = time.split(':')[1];
                if(time_h.substring(0,1) == '0' && time_h.length == 2) {
                    time_h = time_h.substring(1,2);
                }

                minute = Number(time_h) * 60 + Number(time_m);
                return minute;
            },

            ceilTime(time) {
                if(time == '') {
                    return ''
                }
                let timeSum = this.minuteChange(time) + 15
                if(timeSum % 15 == 0) {
                   return this.minuteFormat(timeSum)
                }else {
                   return this.minuteFormat(timeSum + 15 - (timeSum % 15))
                }
            },
            // 数字类型分钟转化为时间字符串
            minuteFormat(time) {
                if(time == 0) {
                    return ""
                }
                let time_h = String(Math.floor(time / 60));
                let time_m = String(time % 60);
                let minute;
                if(isNaN(time_h) || isNaN(time_m)) {
                    minute = "";
                }else {
                    if(time_h.substring(0,1) == "-" || time_h.substring(1,2) == "-") {
                        minute = "0:00"
                    }else {
                        if(time_h.length == 1) {
                            time_h = "0" + time_h;
                        }
                        if(time_m.length == 1) {
                            time_m = "0" + time_m;
                        }
                        minute = time_h + ":" + time_m;
                    }
                }
                return minute;
            },
            timeDiff() {
                return this.minuteFormat(this.minuteChange(this.form.endTime) - this.minuteChange(this.form.startTime))
            },
            //日付を超えた時の時間の処理
            tomorrowTime(judge_time) {
                return this.minuteFormat(this.minuteChange(judge_time) - this.minuteChange("24:00"))
            },
            //日付を超えた時の日付の処理
            tomorrowDate(date,time){
                let nowDate = new Date(date)
                let nowTime = this.tomorrowTime(time)
                nowDate.setDate( nowDate.getDate() + 1)
                let year = nowDate.getFullYear()
                let month = nowDate.getMonth() + 1
                let day = nowDate.getDate()
                let DateTime = new Date(year+"-"+month+"-"+day+" "+nowTime)
                console.log(DateTime)
                return DateTime
            },
            // 削除のポップアップ
            onDeleteConfirmClick () {
                this.deleteConfirmDialogVisible = true
            },
            //申請・承認画面のポップアップ
            applyDialog(flg){
                // 申請時
                if (flg == 0) {
                    this.userData.userId = this.userData.userId
                // 承認時
                }else if (flg == 1) {
                    this.userData.userId = this.rowdata.requestUserId
                }
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        // 月締完了月の翌月1日
                        let data = {
                            companyCode: this.userData.companyCode,
                            userId: this.userData.userId
                        }
                        console.log(data)
                        selectTgtMonth(data).then(res => {
                            console.log(res)
                            this.tgtMonth = res.data
                            console.log(this.tgtMonth)
                            // 月締処理履歴テーブルにデータがない場合（申請日の月からを可とする）
                            if(this.tgtMonth == 0){
                                this.acceptedDate = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-01'
                                console.log('月締無', this.acceptedDate)
                            // 月締処理履歴テーブルにデータがある場合
                            }else{
                                let tgtDate = this.tgtMonth.substring(0,4) + '-' + this.tgtMonth.substring(4,6) + '-01'
                                console.log(tgtDate)
                                let tgtDay = new Date(tgtDate)
                                let ajustDate = tgtDay.setMonth(tgtDay.getMonth() + 1)
                                let acceptedDay = new Date(ajustDate)
                                console.log(acceptedDay)
                                let year = acceptedDay.getFullYear()
                                let month = acceptedDay.getMonth()+1
                                let day = acceptedDay.getDate()
                                console.log('today', year, month, day)
                                this.acceptedDate = year + '-' + month + '-' + day
                                console.log('月締有',this.acceptedDate)
                            }
                            console.log('acceptedDate',this.acceptedDate)
                            // 月締完了月を超えているかの真偽判定
                            console.log('残業予定日', this.form.overtimeDay)
                            let year = this.form.overtimeDay.getFullYear()
                            let month =  this.form.overtimeDay.getMonth()+1
                            let day =  this.form.overtimeDay.getDate()
                            console.log('chosenDay', year, month, day)
                            this.chosenDay = year + '-' + month + '-' + day
                            console.log('this.chosenDay', this.chosenDay)
                            let checkDays = []
                            checkDays.push(new Date(this.chosenDay.replace(/-/g , "/")) >= new Date(this.acceptedDate.replace(/-/g , "/")))
                            console.log('checkDays', checkDays)
                            let count = checkDays.filter(checkDays => checkDays == false)
                            this.falseCount = count.length
                            console.log(this.falseCount)
                            if(this.falseCount >= 1){
                                Message({
                                    message: '選択不可の日付が含まれております。',
                                    type: 'error',
                                    duration: 4000
                                })
                                return
                            }else{
                                //日付が重複チェック
                                let data = {
                                    companyCode:  this.userData.companyCode,
                                    requestNumber: this.reqNum,
                                    requestUserId:  this.userData.userId,
                                    workTypeKbn: this.form.selectOvertime,
                                    scheduledOvertimeDay: this.setDate(this.form.overtimeDay),
                                    startTime: this.setDateTime(this.form.overtimeDay,this.form.startTime),
                                    endTime: this.setDateTime(this.form.overtimeDay,this.form.endTime),
                                    estimatedWorkHours: new Date("2000/01/01 "+this.timeDiff()),
                                    overtimeReasonKbn: this.form.overtimeReason,
                                    remark: this.form.overtimeColumn
                                }
                                console.log(data)
                                overtimeDayConfirm(data).then(res => {
                                    console.log(res)
                                    //日付が重複する休暇申請、深夜残業申請がないかをチェックするフラグ
                                    let registFlg = 0 >= Number(res.data) ? true : false;
                                    //日付を変更せずに申請を行う時のフラグ
                                    let sameDayFlg = false
                                    if(this.changebyFlg >= '2'){
                                        sameDayFlg = (this.userData.userId==this.rowdata.requestUserId&&this.setDate(this.form.overtimeDay)==this.reSaveOvertimeDay)
                                    }
                                    if(registFlg||sameDayFlg){
                                        this.requestSaveData()
                                        console.log(this.requestData)
                                        console.log(flg)
                                        this.$refs.approvalDialog.applyDialogOpen(flg)
                                    }else{
                                        Message({
                                            message: '申請を行う残業予定日と重複したデータが存在します。',
                                            type: 'error',
                                            duration: 4 * 1000
                                        })
                                    }
                                }).catch(error => {
                                    console.log(error)
                                })
                            }
                            // 月締めの最後
                        }).catch(error => {
                        console.log(error) 
                        })
                    }
                })
            },
            //差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            //取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            //戻るボタン（申請一覧）
            closeA() {
                this.$router.go({path: this.$router.currentRoute.path, force: true})
            },
            //戻るボタン（承認一覧）
            closeB() {
                this.$router.go(-1);
            },
        }
    }
</script>

<style lang="scss" scope>
.end-time {
    position: absolute;
    margin-left:-269px;
}
.wavy-line {
    position: absolute;
    margin-left:150px;
}
.start-tomorrow-text {
    position: absolute;
    margin-left:121px;
    line-height: 0px;
    font-size: 12px;
    top:455px;
}
.end-tomorrow-text {
    position: absolute;
    margin-left:287px;
    line-height: 0px;
    font-size: 12px;
    top:455px;
}
.leave-btn {
    padding-right: 350px;
}
//「申請一覧」、「承認一覧」に戻るボタン
.itirann-btn {
    transition: 0.5s;
    width: 150px;
    height: 26px;
    background-color: #fff;
    font-size: 14px;
    line-height: 26px;
    text-align: center;
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.10);
    border: 2px #444 solid;
    border-radius: 30px;
    cursor: pointer;
    color: #444;
    font-weight: bold;
}
// 削除ボタン（共通）
.gray-btn {
    transition: 0.5s;
    width: 80px;
    height: 30px;
    background-color: #4e4c4c;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 8px 0 rgba(54, 51, 51, 0.4);
    border-radius: 30px;
    cursor: pointer;
    color: #fff;
}
</style>