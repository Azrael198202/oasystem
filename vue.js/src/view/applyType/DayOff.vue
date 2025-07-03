<template>
    <div class="apply-area">
        <!-- computedを使ってpropsから申請名を決定できるようにしたい -->
        <!-- <apply-header title="代休申請"></apply-header> -->
        <applyHeader title="代休申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <div class="leave-num">
            <div class='white-btn leave-info' @click="OpenPaidLeaveHistory()" style="width: 100px">有休情報</div>
            <div class='white-btn dayoff-info' @click="OpenDayOffHistory()" style="width: 100px">代休情報</div>
        </div>
        <div class="apply-leave">
            <el-form ref="form" :rules="rules" :model="form" label-width="120px">
                <div class="leave-option">
                    <el-form-item label="休暇種類">
                        <el-select :disabled="true" v-model="holidayType" class="option-box">
                            <el-option label="" value=""></el-option>
                            <el-option
                                v-for="item in LeavekbnList"
                                :key="item.categoryKbn"
                                :label="item.categoryKbnName"
                                :value="item.categoryKbn">
                                {{item.categoryKbnName}}
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-radio-group v-model="holidayTimeApply" class="work-time" :disabled="inputOff">
                        <el-radio-button
                            v-for="(item , index) in WorkTimeList"
                            :key="index"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-radio-button>
                    </el-radio-group>
                </div>
                <el-form-item prop="exchanedWorkDays" label="休日出勤日">
                    <el-select v-model="form.exchanedWorkDays" class="dayoff-work" :disabled="inputOff" style="width: 300px" @change="setWorkTime()">
                        <el-option
                            v-for="item in exchanedWorkDaysList"
                            :key="item.holidayWorkDay"
                            :label=" item.holidayWorkDay == '' || item.holidayWorkDay == null ? '' : item.holidayWorkDay.substring(0,4) + '/' + item.holidayWorkDay.substring(4,6) + '/' + item.holidayWorkDay.substring(6,8)"
                            :value="item.holidayWorkDay">
                            {{item.holidayWorkDay == ''  || item.holidayWorkDay == null ? '' : item.holidayWorkDay.substring(0,4) + '/' + item.holidayWorkDay.substring(4,6) + '/' + item.holidayWorkDay.substring(6,8)}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="selectHalf" label="休暇予定日">
                    <el-date-picker
                        v-model="form.selectHalf"
                        type="date"
                        placeholder="休暇予定日"
                        format="yyyy/MM/dd"
                        value-format="yyyy-MM-dd"
                        key='2'
                        style="width: 300px"
                        :disabled="inputOff">
                    </el-date-picker>
                </el-form-item>
                <el-form-item prop="remarkreq" label="休暇理由">
                    <el-select v-model="form.remarkreq" placeholder="休暇理由区分" class="reason-option" :disabled="inputOff">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in LeaveRkbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="備考">
                    <el-input
                    ref="textareaInput"
                    type="textarea"
                    v-model="comment"
                    maxlength="200"
                    class="textarea-box"
                    :row="10"
                    :disabled="inputOff">
                    </el-input>
                </el-form-item>
            </el-form>
            <div class="leave-btn">
                <el-row>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 10 : 8" class="flex-start">
                        <div class="gray-btn" @click="onDeleteConfirmClick()" v-if="delFlg">削除</div>
                        <div class="itirann-btn" @click="closeA()" v-if="changebyFlg == '2'">申請一覧に戻る</div>
                        <div class="itirann-btn" @click="closeB()" v-if="changebyFlg == '3'">承認一覧に戻る</div>
                    </el-col>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 12 : 16" class="flex-end">
                        <!-- <div class="green-btn" @click="saveBtn()" v-if="modifyFlgsave" :loading="loading" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div> -->
                        <div class="blue-btn" @click="OpenGetBackDialog()" v-if="getBack">取戻</div>
                        <div class="red-btn" @click="remandDialog()" v-if="changebyFlgwf" style='margin-right: 30px'>差戻</div>
                        <div class="green-btn" @click="applyDialog(1)" v-if="changebyFlgapp">承認</div>
                    </el-col>
                    <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 14 : 24" class="flex-end">
                        <div class="green-btn" @click="saveBtn()" v-if="modifyFlgsave" :loading="loading" style='margin-right: 30px'>保存</div>
                        <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 削除のポップアップ -->
        <el-dialog title="申請を削除しますか。"
            :visible.sync="deleteConfirmDialogVisible"
            width="300px"
            center>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteApplication()" style="width: 80px">はい</el-button>
                <el-button @click="deleteConfirmDialogVisible = false" style="width: 80px">いいえ</el-button>
            </div>
        </el-dialog>
        <approval v-bind:userData="userData" :requestData="requestData" ref="approvalDialog" @dataRegistration='approvalSave'></approval>
        <remand v-bind:dateList="detailList" ref="onRemandDialog"></remand>
        <getBackDoalog ref="ongetBackDoalog"></getBackDoalog>
        <paidLeaveHistory v-bind:userData="userData" ref="onPaidLeaveHistory"></paidLeaveHistory>
        <dayOffHistory v-bind:userData="userData" ref="onDayOffHistory"></dayOffHistory>
        
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import approval from "../Approval.vue";
    import remand from "../Remand.vue";
    import getBackDoalog from "../GetBackDialog.vue";
    import paidLeaveHistory from "../PaidLeaveHistory.vue";
    import dayOffHistory from "../DayOffHistory.vue";
    import { getCategory, getHoliday } from '@/api/category'
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest, updateReturnRequest } from '@/api/requestList'
    import { getRequestHolidayList,  insertRequestHolidayList, deleteRequestHoliday, updateRequestHolidayList, leaveDayConfirm, countSubHoliday} from '@/api/requestHoliday'
    import { deleteAllWf } from '@/api/wfList'
    import { selectTgtMonth } from '@/api/monthCloseHistory'
    import { checkNum, getScheduledWorkDays } from '@/api/subHoliday'
    import { Message } from 'element-ui';

    export default {
        props: ['userData'],
        name: "DayOff",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog,
            paidLeaveHistory,
            dayOffHistory,
        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.getCurrentTime()
            this.setReqNum()
            // 全日・午前・午後のオプション
             let WorkTimeData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '048'
            }
            getCategory(WorkTimeData).then(res => {
                console.log(res)
                this.WorkTimeList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 休暇種類のオプション
            let LeaveKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '040',
            }
            getCategory(LeaveKbnData).then(res => {
                console.log(res)
                this.LeavekbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 休暇理由区分のオプション
            let LeaveRKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '041',
            }
            getCategory(LeaveRKbnData).then(res => {
                console.log(res)
                this.LeaveRkbnList=res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 祝日取得
            let hDate = {
                companyCode: this.userData.companyCode,
                holidayYear: this.mYear
            }
            console.log(hDate)
            getHoliday(hDate).then(res => {
                this.holiday = res.data
                console.log(this.holiday)
                if(this.changebyFlg == "2" || this.changebyFlg == "3"){
                    this.setDayOff()
                }
                }).catch(error => {
                console.log(error)
            })
            this.statusSetBtn()
            
            // 休日出勤日
            let exchanedWorkDays = {
                companyCode: this.userData.companyCode,
                userId: this.userData.userId,
            }
            getScheduledWorkDays(exchanedWorkDays).then(res => {
                console.log(res)
                this.exchanedWorkDaysList=res.data
                console.log(res.data)
            }).catch(error => {
                // this.tableLoading = false
                console.log(error)
            })
        },

        data() {
            return {
                wDayOffRequestNumber: '',
                cehckHolidayDays: '',
                inputOff: false,
                loading: false,
                countDate: '',
                mYear: '',
                term: '',
                changebyFlg: '',
                rowdata: [],
                requestData: [],
                aTime: '',
                yMd: '',
                // 詳細:申請番号
                reqNumber:'',
                // 詳細ボタン
                detailList: [],
                // 保存ボタン
                modifyFlgsave: true,
                // 削除ボタン
                delFlg: true,
                deleteConfirmDialogVisible: false,
                // 申請ボタン
                reqFlg: true,
                // 取戻
                getBack: false,
                // opende: false,
                // 承認
                changebyFlgapp: true,
                // 差戻
                changebyFlgwf: true,
                // 申請状態区分
                reqStatus: '',
                // 申請番号
                reqNum: '',
                // 休暇種類
                LeavekbnList: {index: '',categoryKbnName: '', categoryKbn: '' },
                // 休暇理由
                LeaveRkbnList: {index: '',categoryKbnName: '' },
                // 休日出勤
                exchanedWorkDaysList: {index: '',holidayWorkDay: '' },
                // 全日・午前・午後
                holidayTimeApply: '全日',
                WorkTimeList: {index: '',categoryKbnName: '' },
                // 備考
                comment: '',
                // 全日=1,午前・午後=0.5
                holidayDays: '',
                form: {
                    // 休暇全日選択(複数日)
                    // selectDate: '',
                    // 休暇半日選択(1日のみ)
                    selectHalf: '',
                    // 休暇理由申請概要
                    remarkreq: '',
                    // 休日出勤日
                    exchanedWorkDays: '',
                },
                rules: {
                    // selectDate: [
                    //         { required: true, message: '休暇期間は入力必須です。', trigger: 'blur' }
                    // ],
                    selectHalf: [
                            { required: true, message: '休暇予定日は入力必須です。', trigger: 'blur' }
                    ],
                    remarkreq: [
                            { required: true, message: '休暇理由は入力必須です。', trigger: 'change' }
                    ],
                    exchanedWorkDays: [
                            { required: true, message: '休日出勤日は入力必須です。', trigger: 'change' }
                    ]
                },
            }
        },

        computed: {
            //区分値を全日・午前・午後のラジオボタンから取得する処理
            holidayTimeKbn: function() {
                return this.nameSearch(this.holidayTimeApply)
            },
            //表示している画面から休暇種類の値を決定する処理
            holidayType: function() {
                return this.holidayTimeKbn=='020'||this.holidayTimeKbn=='021' ? "021" : "020"
            },
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
                console.log(this.aTime)
                this.mYear = yy
            },
            // 休日出勤日のデータ取得
            setWorkTime(){
                let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                    console.log('出勤日の時間', workDayList.workTimeKbn)
                if(workDayList.workTimeKbn == '010') {
                    this.holidayTimeApply = '全日'
                }else if (workDayList.workTimeKbn == '020'){
                    this.holidayTimeApply = '午前'
                }else if (workDayList.workTimeKbn == '021'){
                    this.holidayTimeApply = '午後'
                }
            },                
            //表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            // 休暇時刻区分名称→休暇時刻区分番号
            nameSearch(name) {
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbnName == name) {
                        return this.WorkTimeList[i].categoryKbn
                    }
                }
            },
            // 休暇時刻区分番号→休暇時刻区分名称
            numSearchD(number) {
                let num = Number(number)
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbn == num) {
                        return this.WorkTimeList[i].categoryKbnName
                    }
                }
            },
            // 休暇種類区分番号→休暇種類名称
            numSearch(number) {
                let num = Number(number)
                for(let i = 0; i < this.LeavekbnList.length;i++) {
                    if(this.LeavekbnList[i].categoryKbn == num) {
                        return this.LeavekbnList[i].categoryKbnName
                    }
                }
            },
            clearStatus(){
                this.holidayTimeApply='全日'
                // this.selectDate=''
                this.selectHalf=''
                this.remarkreq=''
                this.comment=''
                this.exchanedWorkDays=''
            },
            // 重複データ確認
            overlappingConfirm(){
                let flg = 0
                let list = []
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))

                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {

                            flg++
                            let data = {
                                companyCode: this.userData.companyCode,
                                requestNumber: this.reqNum,
                                requestSubNumber: flg,
                                requestUserId: this.userData.userId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('-', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: this.comment
                            }
                            list.push(data)
                            console.log(data)
                        }
                    }
                    console.log(list)
                    leaveDayConfirm(list).then(res => {
                        console.log(res)
                        console.log(this.rowdata)
                        //日付が重複する休暇申請がないかをチェックするフラグ
                        let registFlg = 0 >= Number(res.data) ? true : false;
                        if(registFlg){
                            console.log(registFlg)
                            // 保存処理
                            this.save()
                            // 
                        }else{
                            Message({
                                message: '登録を行う休暇予定日と重複したデータが存在します。',
                                type: 'error',
                                duration: 4 * 1000
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
            },
            // 保存処理/バリデーション
            saveBtn () {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        // 代休（全日）
                        if (this.holidayTimeKbn == "010") {
                            this.holidayDays = 1
                        // 代休（午前・午後）
                        } else if (this.holidayTimeKbn == "020" || this.holidayTimeKbn == "021") {
                            this.holidayDays = 0.5
                        }
                        this.term = [this.form.selectHalf]
                            console.log([this.form.selectHalf])
                            let bool =  this.holiday.find( item => item.holidayDate == this.term[0].replaceAll('-', ''))
                            // 土日を選んだ時のエラーメッセージ
                            if(new Date(this.term).getDay() == 6 || new Date(this.term).getDay() == 0 || bool) {
                                    Message({
                                        message: '土日、祝日以外で選択してください。',
                                        type: 'error',
                                        duration: 4000
                                    })
                                    return
                            }
                        // 休日出勤日のデータ取得
                        let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                        console.log(workDayList)
                        console.log('detailList',this.detailList)
                        if(workDayList === undefined){
                            this.cehckHolidayDays = this.detailList[0].holidayDays
                        }else {
                            this.cehckHolidayDays = workDayList.subHolidayDays
                        }
                        // 休日出勤と休暇の時間数チェック
                        console.log(this.cehckHolidayDays)
                        if(this.cehckHolidayDays !== this.holidayDays){
                            Message({
                                message: '休日出勤日と休暇予定日の時間数が一致していません。',
                                type: 'error',
                                duration: 4000
                            })
                        } else {
                        // 重複データ確認
                        this.overlappingConfirm()
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
            save() {
                // 新規保存
                if (this.changebyFlg == "1") {

                    // 休日出勤日のデータ取得
                    let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                    console.log(workDayList)
                    let flg = 0
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))

                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg++
                            let datainsertreqhol = {
                                companyCode: this.userData.companyCode,
                                requestNumber: this.reqNum,
                                requestSubNumber: flg,
                                requestUserId: this.userData.userId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('-', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: this.comment,
                                workDayoffRequestNumber: workDayList.requestNumber
                            }
                            console.log(datainsertreqhol)
                            insertRequestHolidayList(datainsertreqhol).then(res =>{
                                console.log(res)
                                }).catch(error => {
                            })

                        }
                    }
                    //ステータス区分：保存
                    this.reqStatus = '001'
                    //
                    //申請内容テーブルの保存処理
                    this.requestSaveData()
                    this.requestSave(this.requestData)
                        Message({
                            message: '代休申請を保存しました。',
                            type: 'success',
                            duration: 4000
                        })
                        setTimeout(()=>{
                            this.$router.go({path: this.$router.currentRoute.path, force: true})
                        },200)
                
                // 保存→再保存
                } else if (this.changebyFlg == "2") {

                    // 休日出勤日のデータ取得
                    let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]

                    if (workDayList === undefined) {
                        this.wDayOffRequestNumber = this.detailList[0].workDayoffRequestNumber
                    }else{
                        this.wDayOffRequestNumber = workDayList.requestNumber
                    }
                    console.log('workDayoffRequestNumber', this.wDayOffRequestNumber)

                    let flg2 = 0
                    let list = []
                    let data = {
                        companyCode: this.rowdata.companyCode,
                        requestNumber: this.rowdata.requestNumber,
                        requestSubNumber: '',
                        requestUserId: this.rowdata.requestUserId,
                        holidayTypeKbn: '',
                        holidayPlanDay: '',
                        holidayTimeKbn: '',
                        holidayDays: '',
                        holidayReasonKbn: '',
                        remark: '',
                        workDayoffRequestNumber: ''
                    }
                    list.push(data)
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))
                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg2++
                            let dataupdatereqhol = {
                                companyCode: this.rowdata.companyCode,
                                requestNumber: this.rowdata.requestNumber,
                                requestSubNumber: flg2,
                                requestUserId: this.rowdata.requestUserId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('-', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: this.comment,
                                workDayoffRequestNumber: this.wDayOffRequestNumber
                            }
                            list.push(dataupdatereqhol)
                        }
                    }
                    console.log(list)

                    updateRequestHolidayList(list).then(res =>{
                        console.log(res)
                    }).catch(error => {
                    })
                    //ステータス区分：保存
                    this.reqStatus = '001'
                    //
                    //申請内容テーブルの保存処理
                    this.requestSaveData()
                    this.requestSave(this.requestData)
                    Message({
                        message: '休暇申請の内容を更新しました。',
                        type: 'success',
                        duration: 4000
                    })
                    setTimeout(()=>{
                        this.$router.go({path: this.$router.currentRoute.path, force: true})
                    },200)
                }
                
            },
            // 本日から2か月後の日付取得
            afterTwoMonths(){
                let date = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-' + this.yMd.substring(6,8)
                let today = new Date(date)
                let TwoMonths = today.setMonth(today.getMonth() + 2)
                let limitDate = new Date(TwoMonths)
                let year = limitDate.getFullYear()
                let month = limitDate.getMonth()+1
                let day = limitDate.getDate()
                console.log('today', year, month, day)
                this.limitedDate = year + '-' + month + '-' + day
                console.log(this.limitedDate)
            },
            //申請・承認画面のポップアップ
            applyDialog(applyFlg){
                console.log(applyFlg)
                // 申請
                if (applyFlg == 0) {
                    this.userData.userId = this.userData.userId
                    this.reqNum = this.reqNum  
                // 承認
                }else if (applyFlg == 1) {
                    this.userData.userId = this.rowdata.requestUserId
                    this.reqNum = this.rowdata.requestNumber
                    this.holidayDays = this.rowdata.holidayDays
                    // this.$refs.approvalDialog.applyDialogOpen(applyFlg)
                }
                console.log(this.userData.userId)
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        this.term = [this.form.selectHalf]
                            console.log([this.form.selectHalf])
                            let bool =  this.holiday.find( item => item.holidayDate == this.term[0].replaceAll('-', ''))
                            // 土日を選んだ時のエラーメッセージ
                            if(new Date(this.term).getDay() == 6 || new Date(this.term).getDay() == 0 || bool) {
                                    Message({
                                        message: '土日、祝日以外で選択してください。',
                                        type: 'error',
                                        duration: 4000
                                    })
                                    return
                            }
                        this.term = [this.form.selectHalf]
                            console.log([this.form.selectHalf])
                        
                        // 全日の日付
                        if (this.holidayTimeKbn == "010"){
                            this.holidayDays = 1
                        // 午前・午後の日付 
                        }else if (this.holidayTimeKbn == "020" || this.holidayTimeKbn == "021"){
                            this.holidayDays = 0.5
                        }
                        // 代休予定日の配列（土日・祝除く）
                        let days = []
                        for(let i = 0; i < this.term.length; i++) {
                            console.log("holiday",this.holiday)
                            let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))
                            if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool){
                                days.push(this.term[i])
                            }
                        }

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
                            console.log(this.acceptedDate)
                            // 月締完了月を超えているかの真偽判定
                            console.log('days', days)
                            console.log('days.length',days.length)
                            let checkDays = []
                            for(let i = 0; i < days.length; i++){
                                console.log(new Date(days[i]))
                                console.log(new Date(this.acceptedDate.replace(/-/g , "/")))
                                checkDays.push(new Date(days[i].replace(/-/g , "/")) >= new Date(this.acceptedDate.replace(/-/g , "/")))
                            }
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
                                // 2か月以内チェック//
                                this.afterTwoMonths()
                                console.log(this.limitedDate)
                                // 代休予定日が本日から2か月超えているかの真偽判定
                                console.log(days)
                                let checkDays = []
                                for(let i = 0; i < days.length; i++){
                                    console.log(new Date(days[i].replace(/-/g , "/")))
                                    console.log(new Date(this.limitedDate.replace(/-/g , "/")))
                                    checkDays.push(new Date(days[i].replace(/-/g , "/")) < new Date(this.limitedDate.replace(/-/g , "/")))
                                }
                                console.log(checkDays)
                                let count = checkDays.filter(checkDays => checkDays == false)
                                this.falseCount = count.length
                                console.log(this.falseCount)
                                if(this.falseCount >= 1){
                                    Message({
                                        message: '本日から2か月を超えて代休を取得できません。',
                                        type: 'error',
                                        duration: 4000
                                    })
                                    return
                                }else{
                                    // 休日出勤日のデータ取得
                                    let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                                    console.log(workDayList)
                                    console.log('detailList',this.detailList)
                                    if(workDayList === undefined){
                                        this.cehckHolidayDays = this.detailList[0].holidayDays
                                    }else {
                                        this.cehckHolidayDays = workDayList.subHolidayDays
                                    }
                                    // 休日出勤と休暇の時間数チェック
                                    console.log(this.cehckHolidayDays)
                                    if(this.cehckHolidayDays !== this.holidayDays){
                                        Message({
                                            message: '休日出勤日と休暇予定日の時間数が一致していません。',
                                            type: 'error',
                                            duration: 4000
                                        })
                                        return
                                    }else {
                                        // 重複データ確認
                                        let flg = 0
                                        let list = []
                                            for(let i = 0; i < this.term.length; i++) {
                                                console.log(this.term.length)
                                                let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))

                                                if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                                                    
                                                    flg++
                                                    let data = {
                                                        companyCode: this.userData.companyCode,
                                                        requestNumber: this.reqNum,
                                                        requestSubNumber: flg,
                                                        requestUserId: this.userData.userId,
                                                        holidayTypeKbn: this.holidayType,
                                                        holidayPlanDay: this.term[i].replaceAll('-', ''),
                                                        holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                                        holidayDays: this.holidayDays,
                                                        holidayReasonKbn: this.form.remarkreq,
                                                        remark: this.comment
                                                    }
                                                    list.push(data)
                                                }
                                            }
                                        console.log(list)
                                        leaveDayConfirm(list).then(res => {
                                            console.log(res)
                                            console.log(this.rowdata)
                                            //日付が重複する休暇申請がないかをチェックするフラグ
                                            let registFlg = 0 >= Number(res.data) ? true : false;
                                            if(registFlg){
                                                console.log(registFlg)
                                                // 申請処理
                                                this.getCurrentTime()
                                                this.setReqNum()
                                                this.requestSaveData()
                                                console.log(this.requestData)
                                                console.log(applyFlg)
                                                this.$refs.approvalDialog.applyDialogOpen(applyFlg)
                                            }else{
                                                Message({
                                                    message: '選択された休暇予定日と重複したデータが既に存在します。',
                                                    type: 'error',
                                                    duration: 4 * 1000
                                                })
                                            }
                                        }).catch(error => {
                                            console.log(error)
                                        })
                                        //重複データ確認おわり 
                                    }
                                }
                            
                            }
                        // 月締めの最後
                        }).catch(error => {
                        console.log(error) 
                        })
                        //

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                    });
            },
            approvalSave(number) {
                //ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                console.log(this.reqNum)
                this.submit()
            },
            submit() {
                // 代休（全日）
                if (this.holidayTimeKbn == "010") {
                    this.holidayDays = 1
                    // this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                // 代休（午前・午後）
                } else if (this.holidayTimeKbn == "020" || this.holidayTimeKbn == "021") {
                    this.holidayDays = 0.5
                    // this.term = [this.form.selectHalf]
                }
                this.term = [this.form.selectHalf]
                // 新規申請
                if (this.changebyFlg == "1") {

                    // 休日出勤日のデータ取得
                    let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                    console.log(workDayList)

                    let flg = 0
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))

                            if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                                flg++
                                let datainsertreqhol = {
                                    companyCode: this.userData.companyCode,
                                    requestNumber: this.reqNum,
                                    requestSubNumber: flg,
                                    requestUserId: this.userData.userId,
                                    holidayTypeKbn: this.holidayType,
                                    holidayPlanDay: this.term[i].replaceAll('-', ''),
                                    holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                    holidayDays: this.holidayDays,
                                    holidayReasonKbn: this.form.remarkreq,
                                    remark: this.comment,
                                    workDayoffRequestNumber: workDayList.requestNumber
                                }
                                console.log(datainsertreqhol)
                                insertRequestHolidayList(datainsertreqhol).then(res =>{
                                    console.log(res)
                                    }).catch(error => {
                                })

                            }
                    }
                    Message({
                        message: '代休申請を提出しました。',
                        type: 'success',
                        duration: 1500
                    })
                // 保存→申請
                } else if (this.changebyFlg == "2") {
                    // 休日出勤日のデータ取得
                    let workDayList = this.exchanedWorkDaysList.filter(item => item.holidayWorkDay == this.form.exchanedWorkDays)[0]
                    console.log(workDayList)
                    if (workDayList === undefined) {
                        this.wDayOffRequestNumber = this.detailList[0].workDayoffRequestNumber
                    }else{
                        this.wDayOffRequestNumber = workDayList.requestNumber
                    }
                    console.log('workDayoffRequestNumber', this.wDayOffRequestNumber)

                    let flg2 = 0
                    let list = []
                    let data = {
                        companyCode: this.rowdata.companyCode,
                        requestNumber: this.rowdata.requestNumber,
                        requestSubNumber: '',
                        requestUserId: this.rowdata.requestUserId,
                        holidayTypeKbn: '',
                        holidayPlanDay: '',
                        holidayTimeKbn: '',
                        holidayDays: '',
                        holidayReasonKbn: '',
                        remark: '',
                        workDayoffRequestNumber: ''
                    }
                    list.push(data)
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('-', ''))
                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg2++
                            let dataupdatereqhol = {
                                companyCode: this.rowdata.companyCode,
                                requestNumber: this.rowdata.requestNumber,
                                requestSubNumber: flg2,
                                requestUserId: this.rowdata.requestUserId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('-', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: this.comment,
                                workDayoffRequestNumber: this.wDayOffRequestNumber
                            }
                            list.push(dataupdatereqhol)
                        }
                    }
                    console.log('再申請',list)
                    console.log(list)
                    updateRequestHolidayList(list).then(res =>{
                        console.log(res)
                    }).catch(error => {
                    })
                    Message({
                        message: '代休申請を提出しました。',
                        type: 'success',
                        duration: 4000
                    })

                }
            },
            //申請テーブルに格納するデータ（保存、申請）
            requestSaveData(){
                this.requestData = {
                    companyCode: this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId :this.userData.userId,
                    requestDate: this.yMd,
                    requestTypeKbn: '020',
                    requestOverview: this.numSearch(this.holidayType) + '申請',
                    requestStatusKbn: this.reqStatus
                }
            },
            requestSave(data){
                insertLeaveRequest(data).then(res => {
                console.log(data)
                console.log(res)
                }).catch(error => {
                })
            },
            // 詳細ボタン押下時のデータ取得
            setDayOff() {
                let data = {
                            companyCode: this.rowdata.companyCode,
                            requestUserId: this.rowdata.requestUserId,
                            requestNumber: this.rowdata.requestNumber
                        }
                getRequestHolidayList(data).then(res => {
                    console.log(res.data)
                    console.log(res.data)
                     this.detailList = res.data
                     //  申請番号
                     this.reqNumber = this.detailList[0].requestNumber
                     console.log('requestNumber', this.reqNumber)
                    //  console.log(this.numSearch(this.detailList[0].holidayTypeKbn))
                     //  休暇種類区分
                    //  this.holidayType = this.detailList[0].holidayTypeKbn
                    //  console.log(this.detailList[0].holidayTypeKbn)
                    //  休日出勤日
                    //  this.form.exchanedWorkDays = this.detailList[0].scheduledWorkDay
                    // this.form.exchanedWorkDays以下に修正2022/0418時点
                     this.form.exchanedWorkDays = this.detailList[0].scheduledWorkDay.substring(0,4) + '/' + this.detailList[0].scheduledWorkDay.substring(4,6) + '/' + this.detailList[0].scheduledWorkDay.substring(6,8)
                     console.log(this.form.exchanedWorkDays)

                     //  休暇時刻区分
                     this.holidayTimeApply = this.numSearchD(this.detailList[0].holidayTimeKbn)
                     console.log(this.holidayTimeApply)
                     //  休暇理由区分
                     this.form.remarkreq = this.detailList[0].holidayReasonKbn
                     console.log(this.form.remarkreq)
                     //  備考
                     this.comment = this.detailList[0].remark
                     console.log(this.comment)
                    this.form.selectHalf = this.detailList[0].holidayPlanDay.substring(0,4) + '-' + this.detailList[0].holidayPlanDay.substring(4,6) + '-' + this.detailList[0].holidayPlanDay.substring(6,8)
                    console.log(this.form.selectHalf)
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
            //差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            //取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            OpenPaidLeaveHistory(){
                this.$refs.onPaidLeaveHistory.paidLeaveHistoryOpen()
            },
            OpenDayOffHistory(){
                this.$refs.onDayOffHistory.dayOffHistoryOpen()
            },
            // 削除のポップアップ
            onDeleteConfirmClick () {
                this.deleteConfirmDialogVisible = true
            },
            // 削除処理
            deleteApplication(){
                // 申請状態＝保存
                if(this.rowdata.requestStatusKbn == '001'){
                    this.deleteReq()
                    this.deleteRequestH()
                // 申請状態＝差戻
                }else if(this.rowdata.requestStatusKbn == '010'){
                    this.deleteReq()
                    this.deleteRequestH()
                    this.deleteAllW()
                }
                Message({
                    message: '申請を削除しました。',
                    type: 'success',
                    duration: 4000
                })
                this.deleteConfirmDialogVisible = false
                setTimeout(()=>{
                    this.$router.go({path: this.$router.currentRoute.path, force: true})
                },200)
            },
            //休暇申請内容テーブルのレコード削除
            deleteRequestH(){
                let data = {
                    // companyCode: getUserInfo().companyCode,
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                    requestUserId: this.rowdata.requestUserId
                }
                deleteRequestHoliday(data).then(res =>{
                    console.log(res.data)
                }).catch(error => {
                    console.log(error)
                })
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
            //ワークフローテーブルのレコード削除
            deleteAllW(){
                let data = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                deleteAllWf(data).then(res =>{
                    console.log(res.data)
                }).catch(error => {
                    console.log(error)
                })
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
.cal-days {
    margin-left: 90px;
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