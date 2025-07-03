<template>
    <div class="apply-area">
        <!-- <apply-header title="休日出勤申請"></apply-header> -->
        <applyHeader title="休日出勤申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <div class="apply-leave">
            <el-form ref="form" :rules="rules" :model="form" label-width="120px">
                <div class="leave-option">
                    <el-form-item label="申請種類">
                        <el-select  v-model="form.selectHolidayWork" :disabled="true" style="width: 300px">
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
                    <el-radio-group v-model="form.holidayWorkTime" class="work-time" :disabled="inputOff">
                        <el-radio-button
                            v-for="(item , index) in WorkTimeList"
                            :key="index"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-radio-button>
                    </el-radio-group>
                </div>
                <el-form-item prop="holidayWorkDay" label="休日出勤予定">
                    <el-date-picker
                        v-model="form.holidayWorkDay"
                        type="date"
                        placeholder="YYYY/MM/DD"
                        format="yyyy/MM/dd"
                        style="width: 300px"
                        :disabled="inputOff">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="休日出勤理由" prop="holidayWorkReason">
                    <el-select v-model="form.holidayWorkReason" placeholder="休日出勤理由区分" style="width: 300px" :disabled="inputOff">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in holidayWorkKbnList"
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
                        v-model="form.holidayWorkColumn"
                        maxlength="200"
                        class="textarea-box"
                        :disabled="inputOff">
                    </el-input>
                </el-form-item>
            </el-form>
            <div class="leave-btn">
                <el-row>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 10: 8" class="flex-start">
                        <div class="gray-btn" @click="onDeleteConfirmClick()" v-if="delFlg">削除</div>
                        <div class="itirann-btn" @click="closeA()" v-if="changebyFlg == '2'">申請一覧に戻る</div>
                        <div class="itirann-btn" @click="closeB()" v-if="changebyFlg == '3'">承認一覧に戻る</div>
                    </el-col>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 12 : 16" class="flex-end">
                        <!-- <div class="green-btn" @click="save()" v-if="modifyFlgsave" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div> -->
                        <div class="blue-btn" @click="OpenGetBackDialog()" v-if="getBack">取戻</div>
                        <div class="red-btn" @click="remandDialog()" v-if="changebyFlgwf"  style='margin-right: 30px'>差戻</div>
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
                <el-button type="primary" @click="deleteHolidayWork()">はい</el-button>
                <el-button @click="deleteConfirmDialogVisible = false">いいえ</el-button>
            </div>
        </el-dialog>
        <approval v-bind:userData="userData" :requestData="requestData" ref="approvalDialog" @dataRegistration='approvalSave'></approval>
        <remand v-bind:dateList="form.holidayWorkDay" ref="onRemandDialog"></remand>
        <getBackDoalog ref="ongetBackDoalog"></getBackDoalog>
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import approval from "../Approval.vue";
    import remand from "../Remand.vue";
    import getBackDoalog from "../GetBackDialog.vue";
    import { getCategory } from '@/api/category';
    import { insertWorkDayOff, selectWorkDayOff, deleteWorkDayOff, getWorkDayOffInfo } from '@/api/workDayOff';
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest } from '@/api/requestList';
    import { Message } from 'element-ui';
    import { selectTgtMonth } from '@/api/monthCloseHistory';

    export default {
        props: ['userData'],
        name: "HolidayWork",
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
            // 休日出勤理由区分のオプション
            let holidayWorkKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '046',
            }
            getCategory(holidayWorkKbnData).then(res => {
                console.log(res)
                this.holidayWorkKbnList=res.data
            }).catch(error => {
                console.log(error)
            })
            // 全日・午前・午後のオプション
             let WorkTimeData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '048'
            }
            getCategory(WorkTimeData).then(res => {
                console.log(res)
                this.WorkTimeList = res.data
                if(this.changebyFlg=="2"||this.changebyFlg=="3"){
                    this.statusSetHolidayWork()
                }
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            this.statusSetBtn()
        },

        data() {
            return {
                form: {
                    holidayWorkTime: '全日',
                    //休日出勤
                    selectHolidayWork: '021',
                    //休日出勤日
                    holidayWorkDay: '',
                    //休日出勤理由
                    holidayWorkReason: '',
                    //休日出勤備考
                    holidayWorkColumn: '',
                },
                rules: {
                    holidayWorkDay: [{ required: true, message: '休日出勤予定日は入力必須です。', trigger: 'blur' }],
                    holidayWorkReason: [{ required: true, message: '休日出勤理由は入力必須です。', trigger: 'change' }],
                },
                // 詳細:申請番号
                reqNumber: '',
                ApplyKbnList: {index: '',categoryKbnName: '' },
                holidayWorkKbnList: {index: '',categoryKbnName: '' },
                WorkTimeList: {index: '',categoryKbnName: '' },
                rowdata: [],
                requestData: [],
                reqNum: '',
                reqSubNum: '1',
                aTime: '',
                ymd: '',
                inputOff: false,
                changebyFlg: true,
                delFlg: true,
                modifyFlgsave: true,
                reqFlg: true,
                getBack: false,
                changebyFlgwf: true,
                changebyFlgapp: true,
                deleteConfirmDialogVisible: false,
                // 重複データ確認
                workDayOffInfo: ''
            }
        },

        computed: {

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
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime +  this.userData.companyCode +  this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            statusSetHolidayWork(){
                let data ={
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                console.log(data)
                selectWorkDayOff(data).then(res => {
                    console.log(res)
                    //申請番号
                    this.reqNumber = res.data[0].requestNumber
                    console.log('reqNumber',this.reqNumber)
                    this.form.holidayWorkTime=this.numSearchTime(res.data[0].workTimeKbn)
                    this.form.holidayWorkDay=new Date(res.data[0].scheduledWorkDay.substr(0,4)+"/"+res.data[0].scheduledWorkDay.substr(4,2)+"/"+res.data[0].scheduledWorkDay.substr(6,2))
                    this.form.holidayWorkReason=res.data[0].workReasonKbn
                    this.form.holidayWorkColumn=res.data[0].remark
                    console.log(this.form.holidayWorkDay)
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
            // 削除のポップアップ
            onDeleteConfirmClick () {
                this.deleteConfirmDialogVisible = true
            },
            save () {
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        //ステータス区分：保存
                        this.getCurrentTime()
                        this.workDayOffSave()
                        this.requestSaveData()
                        this.requestSave(this.requestData)
                        setTimeout(()=>{
                            this.$router.go({path: this.$router.currentRoute.path, force: true})
                        },200)
                    }
                })
            },
            approvalSave(number) {
                //ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                console.log(this.reqNum)
                this.workDayOffSave()
            },
            //申請・承認画面のポップアップ
            applyDialog(flg){
                
                // this.$alert('<div style="width:400px">休日出勤当日にも打刻をお忘れなきよう、ご注意ください。<br />打刻忘れがあると月末の締処理に間に合わない場合もあります。</div>', 'ご注意', {
                //     confirmButtonText: '了解',
                //     dangerouslyUseHTMLString:true,
                //     callback: action => {
                //         this.$message({
                //         type: 'info',
                //         message: `action: ${ action }`
                //         });
                //     }
                //     });
                
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
                                console.log('月締有', this.acceptedDate)
                            }
                            console.log('acceptedDate',this.acceptedDate)
                            // 月締完了月を超えているかの真偽判定
                            console.log('休日出勤予定日', this.form.holidayWorkDay)
                            let year = this.form.holidayWorkDay.getFullYear()
                            let month =  this.form.holidayWorkDay.getMonth()+1
                            let day =  this.form.holidayWorkDay.getDate()
                            console.log('chosenDay', year, month, day)
                            this.chosenDay = year + '-' + month + '-' + day
                            console.log('this.chosenDay', this.chosenDay)
                            let checkDays = []
                            checkDays.push(new Date(this.chosenDay.replace(/-/g , "/")) >= new Date(this.acceptedDate.replace(/-/g , "/")))
                            console.log(checkDays)
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
                                // 重複する日時で既に休日出勤の承認完了を得ているか否かの判定
                                let data = {
                                    companyCode: this.userData.companyCode,
                                    requestUserId: this.userData.userId,
                                    scheduledWorkDay: this.setDate(this.form.holidayWorkDay)
                                }
                                console.log('data', data)
                                getWorkDayOffInfo(data).then(res => {
                                    console.log('res.data', res.data)
                                    this.workDayOffInfo = res.data
                                    if(this.workDayOffInfo.length >= 1){
                                        let existData = []
                                        for(let i = 0; i < this.workDayOffInfo.length;i++){
                                            if(this.workDayOffInfo[i].requestStatusKbn == '999'){
                                                let data = {
                                                    companyCode: this.workDayOffInfo[i].companyCode,
                                                    requestNumber: this.workDayOffInfo[i].requestNumber,
                                                    requestStatusKbn: this.workDayOffInfo[i].requestStatusKbn,
                                                    requestTypeKbn: this.workDayOffInfo[i].requestTypeKbn,
                                                    requestUserId: this.workDayOffInfo[i].requestUserId,
                                                    scheduledWorkDay: this.workDayOffInfo[i].scheduledWorkDay,
                                                    workTimeKbn: this.workDayOffInfo[i].workTimeKbn
                                                }
                                                existData.push(data)
                                            }
                                        }
                                        console.log('existData', existData)
                                        // 承認完了のデータが1つのみ存在する場合
                                        if(existData.length == '1'){
                                            for(let i = 0; i < existData.length;i++){
                                                // 承認完了のデータの時間区分と選択したデータの時間区分が一致する場合
                                                if(existData[i].workTimeKbn == this.nameSearch(this.form.holidayWorkTime)){
                                                    Message({
                                                        message: '同じ日時で既に休日出勤の承認済を得ています。',
                                                        type: 'error',
                                                        duration: 4000
                                                    })
                                                    return
                                                }else{
                                                    // 承認完了のデータの時間区分と選択した時間区分が一致しない場合
                                                    console.log('this.nameSearch(this.form.holidayWorkTime', this.nameSearch(this.form.holidayWorkTime))
                                                    // 選択した時間区分が全日の場合
                                                    if(this.nameSearch(this.form.holidayWorkTime) == '010'){
                                                        Message({
                                                            message: '同じ日時で既に休日出勤の承認済を得ています。',
                                                            type: 'error',
                                                            duration: 4000
                                                        })
                                                        return
                                                    // 選択した時間区分が半日の場合
                                                    }else if(this.nameSearch(this.form.holidayWorkTime) == '020' || this.nameSearch(this.form.holidayWorkTime) == '021'){
                                                        // 承認完了のデータの時間区分が全日の場合
                                                        if(existData[i].workTimeKbn == '010'){
                                                            Message({
                                                                message: '同じ日時で既に休日出勤の承認済を得ています。',
                                                                type: 'error',
                                                                duration: 4000
                                                            })
                                                            return
                                                         // 承認完了のデータの時間区分が半日の場合
                                                        }else if(existData[i].workTimeKbn == '020' || existData[i].workTimeKbn == '021'){
                                                            // 承認完了のデータの時間区分と選択した時間区分が一致しない場合
                                                            if(existData[i].workTimeKbn !== this.nameSearch(this.form.holidayWorkTime)){
                                                                this.requestSaveData()
                                                                console.log(this.requestData)
                                                                console.log(flg)
                                                                this.$refs.approvalDialog.applyDialogOpen(flg)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        // 承認完了のデータが2つ存在する場合
                                        }else if(existData.length == '2'){
                                            Message({
                                                message: '同じ日時で既に休日出勤の承認済を得ています。',
                                                type: 'error',
                                                duration: 4000
                                            })
                                            return
                                        // 承認完了のデータが存在しない場合
                                        }else if(existData.length == '0'){
                                            this.requestSaveData()
                                            console.log(this.requestData)
                                            console.log('fig',flg)
                                            this.$refs.approvalDialog.applyDialogOpen(flg)
                                            }
                                    } else {
                                        this.requestSaveData()
                                        console.log(this.requestData)
                                        console.log('fig',flg)
                                        this.$refs.approvalDialog.applyDialogOpen(flg)
                                    }
                                }).catch(error => {
                                    console.log(error) 
                                })
                            }
                            // 月締めの最後
                        }).catch(error => {
                        console.log(error) 
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            //差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            //取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            //休日出勤テーブル保存処理
            workDayOffSave(){
                let data = {
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId:  this.userData.userId,
                    workTypeKbn: this.form.selectHolidayWork,
                    scheduledWorkDay: this.setDate(this.form.holidayWorkDay),
                    workTimeKbn: this.nameSearch(this.form.holidayWorkTime),
                    workReasonKbn: this.form.holidayWorkReason,
                    remark: this.form.holidayWorkColumn
                }
                console.log(data)
                insertWorkDayOff(data).then(res => {
                    console.log(res)
                    Message({
                        message: '休日出勤申請を保存しました。',
                        type: 'success',
                        duration: 4 * 1000
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            deleteHolidayWork(){
                this.deleteReq()
                let data ={
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                    requestUserId: this.rowdata.requestUserId
                }
                deleteWorkDayOff(data).then(res => {
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
            //申請テーブルの処理
            requestSaveData(){
                this.requestData = {
                    companyCode:  this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId : this.userData.userId,
                    requestDate: this.yMd,
                    requestTypeKbn: this.form.selectHolidayWork,
                    requestOverview: this.numSearch(this.form.selectHolidayWork)+'（'+(this.form.holidayWorkTime+'）'),
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
            numSearch(number) {
                for(let i = 0; i < this.ApplyKbnList.length;i++) {
                    if(this.ApplyKbnList[i].categoryKbn == number) {
                        return this.ApplyKbnList[i].categoryKbnName
                    }
                }
            },
            numSearchTime(number) {
                let num = Number(number)
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbn == num) {
                        return this.WorkTimeList[i].categoryKbnName
                    }
                }
            },
            nameSearch(name) {
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbnName == name) {
                        return this.WorkTimeList[i].categoryKbn
                    }
                }
            },
            setDate(date){
                //yyyyMMdd
                let selectDate =date.getFullYear()+("0"+(date.getMonth()+1)).slice(-2)+("0"+date.getDate()).slice(-2)
                return selectDate
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