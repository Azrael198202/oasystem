<template>
    <div class="apply-area">
        <!-- computedを使ってpropsから申請名を決定できるようにしたい -->
        <!-- <apply-header title="夏季休暇申請"></apply-header> -->
        <applyHeader title="夏季休暇申請" v-bind:reqNumber="reqNumber"></applyHeader>
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
                </div>
                <div class='summmer-radio-btn'>
                    <el-radio v-model="radio" @change="onRadioClear()" label="1" :disabled="radioDays">期間選択</el-radio>
                    <el-radio v-model="radio" @change="onRadioClear()" label="2" :disabled="radioDays">日毎選択</el-radio>
                </div>
                <el-form-item prop="selectDate" label="休暇期間" v-if="radio == '1'">
                    <el-date-picker
                        @blur="onBlur()"
                        @change="onClear()"
                        v-model="form.selectDate"
                        type="daterange"
                        range-separator="～"
                        start-placeholder="YYYY/MM/DD"
                        end-placeholder="YYYY/MM/DD"
                        value-format="yyyy/MM/dd"
                        format="yyyy/MM/dd"
                        key='1'
                        style="width: 300px"
                        :disabled="inputOff">
                    </el-date-picker>
                    <!-- {{form.selectDate}} -->
                </el-form-item>
                <div>
                    <el-form-item prop="planOne" label="休暇予定日1" v-if="radio == '2'">
                        <el-date-picker
                            @change="getDateOne()"
                            v-model="form.planOne"
                            type="date"
                            placeholder="休暇予定日"
                            format="yyyy/MM/dd"
                            value-format="yyyy/MM/dd"
                            key='2'
                            style="width: 300px"
                            :disabled="inputOff">
                        </el-date-picker>
                        <!-- {{form.planOne}} -->
                    </el-form-item>
                    <el-form-item prop="planTwo" label="休暇予定日2" v-if="radio == '2'">
                        <el-date-picker
                            @change="getDateTwo()"
                            v-model="form.planTwo"
                            type="date"
                            placeholder="休暇予定日"
                            format="yyyy/MM/dd"
                            value-format="yyyy/MM/dd"
                            key='2'
                            style="width: 300px"
                            :disabled="inputOff">
                        </el-date-picker>
                        <!-- {{form.planTwo}} -->
                    </el-form-item>
                    <el-form-item prop="planThree" label="休暇予定日3" v-if="radio == '2'">
                        <el-date-picker
                            @change="getDateThree()"
                            v-model="form.planThree"
                            type="date"
                            placeholder="休暇予定日"
                            format="yyyy/MM/dd"
                            value-format="yyyy/MM/dd"
                            key='2'
                            style="width: 300px"
                            :disabled="inputOff">
                        </el-date-picker>
                        <!-- {{form.planThree}} -->
                    </el-form-item>
                </div>
                <div class="cal-days">
                    <span>日数&nbsp;&#058;&nbsp;</span>{{countDate}}<span>&nbsp;日</span>
                </div>
                <el-form-item prop="remarkreq" label="休暇理由">
                    <el-select v-model="form.remarkreq" placeholder="休暇理由区分" class="reason-option" :disabled="inputOff">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in SummerKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                    </el-select>
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
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import approval from "../Approval.vue";
    import remand from "../Remand.vue";
    import getBackDoalog from "../GetBackDialog.vue";
    import { getCategory, getHoliday } from '@/api/category'
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest, updateReturnRequest } from '@/api/requestList'
    import { getRequestHolidayList,  insertRequestHolidayList, deleteRequestHoliday, updateRequestHolidayList, leaveDayConfirm} from '@/api/requestHoliday'
    import { selectTgtMonth } from '@/api/monthCloseHistory'
    import { deleteAllWf } from '@/api/wfList'
    import { Message } from 'element-ui';

    export default {
        props: ['userData'],
        name: "SummerHoliday",
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
            // 夏季休暇理由区分のオプション
            let SummerKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '042',
            }
            getCategory(SummerKbnData).then(res => {
                console.log(res)
                this.SummerKbnList=res.data.filter((e=>{return e.categoryKbn=='010'}))
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
                    this.setSummerHoliday()
                }
                }).catch(error => {
                console.log(error)
            })
            this.statusSetBtn()
        },

        data() {
            return {
                // 休暇予定日1.2.3
                radio: '1',
                // 期間選択・日毎選択のラジオボタンの活性・非活性
                radioDays: false,
                inputOff: false,
                loading: false,
                countDate: '',
                mYear: '',
                term: '',
                dialogVisibleA: false,
                dialogVisibleB: false,
                changebyFlg: '',
                rowdata: [],
                requestData: [],
                aTime: '',
                yMd: '',
                // 詳細：申請番号
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
                // 夏季休暇
                holidayType: '040',
                // 休暇種類
                LeavekbnList: {index: '',categoryKbnName: '', categoryKbn: '' },
                // 夏季休暇理由
                SummerKbnList: {index: '',categoryKbnName: '' },
                // 全日・午前・午後
                holidayTimeApply: '全日',
                WorkTimeList: {index: '',categoryKbnName: '' },
                // 備考
                comment: '',
                // 全日=1,午前・午後=0.5
                holidayDays: '',
                form: {
                    // 休暇期間選択
                    selectDate: '',
                    // 休暇日毎選択1
                    planOne: '',
                    // 休暇日毎選択2
                    planTwo: '',
                    // 休暇日毎選択3
                    planThree: '',
                    // 休暇理由申請概要
                    remarkreq: '',
                },
                rules: {
                    selectDate: [
                            { required: true, message: '休暇期間は入力必須です。', trigger: 'blur' }
                    ],
                    // planOne: [
                    //         { required: true, message: '休暇日は入力必須です。', trigger: 'blur' }
                    // ],
                    // planTwo: [
                    //         { required: true, message: '休暇期間は入力必須です。', trigger: 'blur' }
                    // ],
                    // planThree: [
                    //         { required: true, message: '休暇期間は入力必須です。', trigger: 'blur' }
                    // ],
                    remarkreq: [
                            { required: true, message: '休暇理由は入力必須です。', trigger: 'change' }
                    ]
                },
                selectedDayList: '',
            }
        },

        methods: {
            // 休暇期間選択の開始日と終了日の日数分配列(ハイフン有)
            dayList (startDay,endDay) {
            let dayL = [];
            let ys=startDay.split('/')[0]
            let ms=startDay.split('/')[1]
            let ds=startDay.split('/')[2]
            let ye=endDay.split('/')[0]
            let me=endDay.split('/')[1]
            let de=endDay.split('/')[2]

            if (Number(ye) == Number(ys)) {
                if (Number(me) == Number(ms)) {
                    if(Number(ds) == Number(de)) {
                        dayL.push(startDay)
                        return dayL
                    } else {
                        for (let i = Number(ds); i <= Number(de); i++) {
                            dayL.push(ys+'/'+ms+'/'+this.stringAddMD(i))
                        }
                        return dayL
                    }
                } else {
                    for (let i = Number(ds); i <= new Date(ys,Number(ms),0).getDate(); i++) {
                        dayL.push(ys+'/'+ms+'/'+this.stringAddMD(i))
                    }
                    for (let l = 1; l <= Number(de); l++) {
                        dayL.push(ye+'/'+me+'/'+this.stringAddMD(l))
                    }
                    return dayL
                }
            }else {
                if (Number(me) == Number(ms)) {
                    if(Number(ds) == Number(de)) {
                        dayL.push(startDay)
                        return dayL
                    } else {
                        for (let i = Number(ds); i <= Number(de); i++) {
                            dayL.push(ys+'/'+ms+'/'+this.stringAddMD(i))
                        }
                        return dayL
                    }
                } else {
                    for (let i = Number(ds); i <= new Date(ys,Number(ms),0).getDate(); i++) {
                        dayL.push(ys+'/'+ms+'/'+this.stringAddMD(i))
                    }
                    for (let l = 1; l <= Number(de); l++) {
                        dayL.push(ye+'/'+me+'/'+this.stringAddMD(l))
                    }
                    return dayL
                }
            }
            
            },
            stringAddMD (num) {
                if(String(num).length < 2) {
                    return '0' + num
                } else {
                    return String(num)
                }
            },
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
            // 期間日数計算
            onBlur() {
                if (this.form.selectDate == '' || this.form.selectDate == null) {
                    return
                }else {
                    let days = []
                    this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                    console.log('this.term', this.term)
                        for(let i = 0; i < this.term.length; i++) {
                            console.log("holiday",this.holiday)
                            let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))
                            days.push(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool)
                        }
                    if (this.changebyFlg == "1"){      
                        console.log(days)
                        let count = days.filter(days => days == true)
                        this.countDate = count.length
                        if(this.countDate >= 4){
                            Message({
                                message: '最大3日までの選択が可能です。',
                                type: 'error',
                                duration: 4 * 1000
                            })
                            this.form.selectDate = ''
                            this.countDate = ''
                        }
                    }else if (this.changebyFlg == "2" || this.changebyFlg == "3") {
                        console.log(days)
                        let count2 = days.filter(days => days == true)
                        this.countDate = count2.length
                    }   
                } 
            },
            // 日毎日数計算
            getDateOne(){
                if(this.form.planOne == null){
                    this.form.planOne = ''
                }
                if(this.form.planTwo == null){
                    this.form.planTwo = ''
                }
                if(this.form.planThree == null){
                    this.form.planThree = ''
                }
                if(this.form.planOne !== ''){
                    let  bool =  this.holiday.find( item => item.holidayDate == this.form.planOne.replaceAll('/', ''))
                    if(new Date(this.form.planOne).getDay() == 6 || new Date(this.form.planOne).getDay() == 0 || bool) {
                        this.form.planOne = ''
                        Message({
                            message: '土日、祝日以外で選択してください。',
                            type: 'error',
                            duration: 4000
                        })
                        if(this.form.planTwo && this.form.planThree == ''){
                            this.countDate = '1'
                            return
                        }else if(this.form.planTwo && this.form.planThree){
                            this.countDate = '2'
                            return
                        }
                    }else if(this.form.planTwo || this.form.planThree){
                        if(this.form.planOne == this.form.planTwo){
                            this.form.planOne = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planTwo && this.form.planThree == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planTwo && this.form.planThree){
                                this.countDate = '2'
                                return
                            }
                        }else if(this.form.planOne == this.form.planThree){
                            this.form.planOne = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planThree && this.form.planTwo == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planThree && this.form.planTwo){
                                this.countDate = '2'
                                return
                            }
                        }else{
                            this.selectedDayList  = []
                            if(this.form.planTwo && this.form.planThree){
                                this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                            }else if (this.form.planTwo && this.form.planThree == ''){
                                this.selectedDayList = [this.form.planOne, this.form.planTwo]
                            }else if (this.form.planTwo == '' && this.form.planThree){
                                this.selectedDayList = [this.form.planOne, this.form.planThree]
                            }else if (this.form.planTwo == '' && this.form.planThree == ''){
                                this.selectedDayList = [this.form.planOne]
                            }
                        }
                    }else{
                        this.selectedDayList  = []
                        if(this.form.planTwo && this.form.planThree){
                            this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                        }else if (this.form.planTwo && this.form.planThree == ''){
                            this.selectedDayList = [this.form.planOne, this.form.planTwo]
                        }else if (this.form.planTwo == '' && this.form.planThree){
                            this.selectedDayList = [this.form.planOne, this.form.planThree]
                        }else if (this.form.planTwo == '' && this.form.planThree == ''){
                            this.selectedDayList = [this.form.planOne]
                        }
                    }
                    console.log('this.selectedDayList', this.selectedDayList)
                    this.countDate = this.selectedDayList.length
                }else{
                    if(this.form.planTwo && this.form.planThree){
                        this.selectedDayList = [this.form.planTwo, this.form.planThree]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planTwo && this.form.planThree == ''){
                        this.selectedDayList = [this.form.planTwo]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planTwo == '' && this.form.planThree){
                        this.selectedDayList = [this.form.planThree]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planTwo == '' && this.form.planThree == ''){
                        this.selectedDayList = []
                        this.countDate = ''
                    }
                }     
            },
            getDateTwo(){
                if(this.form.planOne == null){
                    this.form.planOne = ''
                }
                if(this.form.planTwo == null){
                    this.form.planTwo = ''
                }
                if(this.form.planThree == null){
                    this.form.planThree = ''
                }
                if(this.form.planTwo !== ''){
                    let bool =  this.holiday.find( item => item.holidayDate == this.form.planTwo.replaceAll('/', ''))
                    if(new Date(this.form.planTwo).getDay() == 6 || new Date(this.form.planTwo).getDay() == 0 || bool) {
                        this.form.planTwo = ''
                        Message({
                            message: '土日、祝日以外で選択してください。',
                            type: 'error',
                            duration: 4000
                        })
                        if(this.form.planOne && this.form.planThree == ''){
                            this.countDate = '1'
                            return
                        }else if(this.form.planOne && this.form.planThree){
                            this.countDate = '2'
                            return
                        }
                    }else if(this.form.planOne || this.form.planThree){
                        if(this.form.planOne == this.form.planTwo){
                            this.form.planTwo = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planOne && this.form.planThree == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planOne && this.form.planThree){
                                this.countDate = '2'
                                return
                            }
                        }else if(this.form.planTwo == this.form.planThree){
                            this.form.planTwo = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planThree && this.form.planOne == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planOne && this.form.planThree){
                                this.countDate = '2'
                                return
                            }
                        }else{
                            this.selectedDayList  = []
                            if(this.form.planOne && this.form.planThree){
                                this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                            }else if (this.form.planOne && this.form.planThree == ''){
                                this.selectedDayList = [this.form.planOne, this.form.planTwo]
                            }else if (this.form.planOne == '' && this.form.planThree){
                                this.selectedDayList = [this.form.planTwo, this.form.planThree]
                            }else if (this.form.planOne == '' && this.form.planThree == ''){
                                this.selectedDayList = [this.form.planTwo]
                            }
                        }
                    }else{
                        this.selectedDayList  = []
                        if(this.form.planOne && this.form.planThree){
                            this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                        }else if (this.form.planOne && this.form.planThree == ''){
                            this.selectedDayList = [this.form.planOne, this.form.planTwo]
                        }else if (this.form.planOne == '' && this.form.planThree){
                            this.selectedDayList = [this.form.planTwo, this.form.planThree]
                        }else if (this.form.planOne == '' && this.form.planThree == ''){
                            this.selectedDayList = [this.form.planTwo]
                        }
                    }
                    console.log('this.selectedDayList', this.selectedDayList)
                    this.countDate = this.selectedDayList.length
                }else{
                    if(this.form.planOne && this.form.planThree){
                        this.selectedDayList = [this.form.planOne, this.form.planThree]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne && this.form.planThree == ''){
                        this.selectedDayList = [this.form.planOne]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne == '' && this.form.planThree){
                        this.selectedDayList = [this.form.planThree]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne == '' && this.form.planThree == ''){
                        this.selectedDayList = []
                        this.countDate = ''
                    }
                }
            },
            getDateThree(){
                if(this.form.planOne == null){
                    this.form.planOne = ''
                }
                if(this.form.planTwo == null){
                    this.form.planTwo = ''
                }
                if(this.form.planThree == null){
                    this.form.planThree = ''
                }
                if(this.form.planThree !== ''){
                    let bool =  this.holiday.find( item => item.holidayDate == this.form.planThree.replaceAll('/', ''))
                    if(new Date(this.form.planThree).getDay() == 6 || new Date(this.form.planThree).getDay() == 0 || bool) {
                        this.form.planThree = ''
                        Message({
                            message: '土日、祝日以外で選択してください。',
                            type: 'error',
                            duration: 4000
                        })
                        if(this.form.planOne && this.form.planTwo == ''){
                            this.countDate = '1'
                            return
                        }else if(this.form.planOne && this.form.planTwo){
                            this.countDate = '2'
                            return
                        }
                    }else if(this.form.planOne || this.form.planTwo) {
                        if(this.form.planOne == this.form.planThree){
                            this.form.planThree = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planOne && this.form.planTwo == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planOne && this.form.planTwo){
                                this.countDate = '2'
                                return
                            }
                        }else if(this.form.planTwo == this.form.planThree){
                            this.form.planThree = ''
                            Message({
                                message: '既に同じ日を選択済です。',
                                type: 'error',
                                duration: 4000
                            })
                            if(this.form.planOne && this.form.planTwo == ''){
                                this.countDate = '1'
                                return
                            }else if(this.form.planOne && this.form.planTwo){
                                this.countDate = '2'
                                return
                            }
                        }else{
                            this.selectedDayList  = []
                            if(this.form.planOne && this.form.planTwo){
                                this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                            }else if (this.form.planOne && this.form.planTwo == ''){
                                this.selectedDayList = [this.form.planOne, this.form.planThree]
                            }else if (this.form.planOne == '' && this.form.planTwo){
                                this.selectedDayList = [this.form.planTwo, this.form.planThree]
                            }else if (this.form.planOne == '' && this.form.planTwo == ''){
                                this.selectedDayList = [this.form.planThree]
                            }
                        }
                    }else{
                        this.selectedDayList  = []
                        if(this.form.planOne && this.form.planTwo){
                            this.selectedDayList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                        }else if (this.form.planOne && this.form.planTwo == ''){
                            this.selectedDayList = [this.form.planOne, this.form.planThree]
                        }else if (this.form.planOne == '' && this.form.planTwo){
                            this.selectedDayList = [this.form.planTwo, this.form.planThree]
                        }else if (this.form.planOne == '' && this.form.planTwo == ''){
                            this.selectedDayList = [this.form.planThree]
                        }
                    }
                    console.log('this.selectedDayList', this.selectedDayList)
                    this.countDate = this.selectedDayList.length
                }else{
                    if(this.form.planOne && this.form.planTwo){
                        this.selectedDayList = [this.form.planOne, this.form.planTwo]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne && this.form.planTwo == ''){
                        this.selectedDayList = [this.form.planOne]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne == '' && this.form.planTwo){
                        this.selectedDayList = [this.form.planTwo]
                        this.countDate = this.selectedDayList.length
                    }else if (this.form.planOne == '' && this.form.planTwo == ''){
                        this.selectedDayList = []
                        this.countDate = ''
                    }
                }
            },
            onClear() {
                if(this.form.selectDate == null) {
                   this.countDate = 0
                }
            },
            onRadioClear(){
                this.form.selectDate = ''
                this.form.planOne = ''
                this.form.planTwo = ''
                this.form.planThree = ''
                this.countDate = ''
            },
            //表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            // 名称→区分番号
            nameSearch(name) {
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbnName == name) {
                        return this.WorkTimeList[i].categoryKbn
                    }
                }
            },
            // 区分番号→名称
            numSearch(number) {
                let num = Number(number)
                for(let i = 0; i < this.LeavekbnList.length;i++) {
                    if(this.LeavekbnList[i].categoryKbn == num) {
                        return this.LeavekbnList[i].categoryKbnName
                    } 
                }
            },
             clearStatus(){
                this.holidayTimeApplay='全日'
                this.selectDate=''
                this.remarkreq=''
                this.comment=''
            },
            // 重複データ確認
            overlappingConfirm(){
                console.log('this.selectedDayList', this.selectedDayList)
                if(this.selectedDayList){
                    this.term = this.selectedDayList.sort()
                }
                console.log('this.term', this.term)
                let flg = 0
                this.holidayDays = '1'
                let list = []
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))

                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            
                            flg++
                            let data = {
                                companyCode: this.userData.companyCode,
                                requestNumber: this.reqNum,
                                requestSubNumber: flg,
                                requestUserId: this.userData.userId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('/', ''),
                                holidayTimeKbn: "010",
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: '',
                                workDayoffRequestNumber: ''
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
            saveBtn() {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        // 土日・祝のみを選んだ時のエラーメッセージ
                        if(this.countDate == 0 || this.countDate == '') {
                            Message({
                                message: '休暇予定日が選択されていません。',
                                type: 'error',
                                duration: 4000
                            })
                            return
                        }
                        // else if(this.countDate <= 2){
                        //     Message({
                        //         message: '休暇予定日は3日分選択必須です。',
                        //         type: 'error',
                        //         duration: 4000
                        //     })
                        //     return
                        // }
                        // 重複データ確認
                        this.overlappingConfirm()
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
                
            },
            // 保存処理
            save() {
                console.log('this.selectedDayList', this.selectedDayList)
                if(this.selectedDayList){
                    this.term = this.selectedDayList.sort()
                }
                console.log('this.term', this.term)
                console.log('this.term', this.term)
                let flg = 0
                this.holidayDays = '1'
                // 新規保存
                if(this.changebyFlg == "1"){
                    for(let i = 0; i < this.term.length; i++) {
                        console.log('save_this.term', this.term)
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))

                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg++
                            let datainsertreqhol = {
                                companyCode: this.userData.companyCode,
                                requestNumber: this.reqNum,
                                requestSubNumber: flg, 
                                requestUserId: this.userData.userId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('/', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: ''
                            }
                            console.log('新規保存', datainsertreqhol)
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
                        message: '夏季休暇申請を保存しました。',
                        type: 'success',
                        duration: 4000
                    })
                   setTimeout(()=>{
                        this.$router.go({path: this.$router.currentRoute.path, force: true})
                    },200)
                // 保存→再保存
                }else if(this.changebyFlg == "2") {
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
                        remark: ''
                    }
                    list.push(data)
                    console.log('selectDate', this.form.selectDate)
                    if(this.form.selectDate){
                        this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                    }
                    if(this.selectedDayList){
                        this.term = this.selectedDayList.sort()
                    }
                    console.log('this.term', this.term)
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))
                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg++
                            let dataupdatereqhol = {
                                companyCode: this.rowdata.companyCode,
                                requestNumber: this.rowdata.requestNumber,
                                requestSubNumber: flg, 
                                requestUserId: this.rowdata.requestUserId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('/', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: ''
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
                }
                setTimeout(()=>{
                    this.$router.go({path: this.$router.currentRoute.path, force: true})
                },200)
            },
            //申請・承認画面のポップアップ
            applyDialog(applyFlg){
                // 申請時
                if (applyFlg == 0) {
                    this.userData.userId = this.userData.userId
                    this.reqNum = this.reqNum
                // 承認時
                }else if (applyFlg == 1) {
                    // this.$refs.approvalDialog.applyDialogOpen(applyFlg)
                    this.userData.userId = this.rowdata.requestUserId
                    this.reqNum = this.rowdata.requestNumber
                    this.holidayDays = this.rowdata.holidayDays
                }
                console.log(this.userData.userId)
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        // 土日・祝のみを選んだ時のエラーメッセージ
                        if(this.countDate == 0 || this.countDate == '') {
                            Message({
                                message: '休暇予定日が選択されていません。',
                                type: 'error',
                                duration: 4000
                            })
                            return
                        }
                        // else if(this.countDate <= 2){
                        //     Message({
                        //         message: '休暇予定日は3日分選択必須です。',
                        //         type: 'error',
                        //         duration: 4000
                        //     })
                        //     return
                        // }
                        // 夏季休暇予定日の配列（土日・祝除く）
                        let days = []
                        if(this.changebyFlg == '1'){
                            if(this.form.selectDate){
                                this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                            }
                            if(this.selectedDayList){
                                this.term = this.selectedDayList.sort()
                            }
                        }else if(this.changebyFlg == '2'){
                            if(this.form.selectDate){
                                this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                            }
                            // 詳細画面で日付編集後に申請
                            if(this.selectedDayList){
                                this.term = this.selectedDayList.sort()
                            }
                            // 詳細画面で日付編集せずに申請
                            if(this.form.planOne == null){
                                this.form.planOne = ''
                            }
                            if(this.form.planTwo == null){
                                this.form.planTwo = ''
                            }
                            if(this.form.planThree == null){
                                this.form.planThree = ''
                            }
                            if(this.form.planOne && this.form.planTwo == '' && this.form.planThree == ''){
                                this.term = [this.form.planOne]
                            }else if(this.form.planOne && this.form.planTwo && this.form.planThree == ''){
                                let daysList = [this.form.planOne, this.form.planTwo]
                                this.term = daysList.sort()
                            }else if(this.form.planOne && this.form.planThree && this.form.planTwo == ''){
                                let daysList = [this.form.planOne, this.form.planThree]
                                this.term = daysList.sort()
                            }else if(this.form.planTwo && this.form.planOne == '' && this.form.planThree == ''){
                                this.term = [this.form.planTwo]
                            }else if(this.form.planTwo && this.form.planThree && this.form.planOne == ''){
                                let daysList = [this.form.planTwo, this.form.planThree]
                                this.term = daysList.sort()
                            }else if(this.form.planThree && this.form.planOne == '' && this.form.planTwo == ''){
                                this.term = [this.form.planThree]
                            }else if(this.form.planOne && this.form.planTwo && this.form.planThree){
                                let daysList = [this.form.planOne, this.form.planTwo, this.form.planThree]
                                this.term = daysList.sort()
                            }
                        }
                        console.log('this.term', this.term)
                        for(let i = 0; i < this.term.length; i++) {
                            console.log("holiday",this.holiday)
                            let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))
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
                                this.acceptedDate = this.yMd.substring(0,4) + '/' + this.yMd.substring(4,6) + '/01'
                                console.log('月締無', this.acceptedDate)
                            // 月締処理履歴テーブルにデータがある場合
                            }else{
                                let tgtDate = this.tgtMonth.substring(0,4) + '/' + this.tgtMonth.substring(4,6) + '/01'
                                console.log(tgtDate)
                                let tgtDay = new Date(tgtDate)
                                let ajustDate = tgtDay.setMonth(tgtDay.getMonth() + 1)
                                let acceptedDay = new Date(ajustDate)
                                console.log(acceptedDay)
                                let year = acceptedDay.getFullYear()
                                let month = acceptedDay.getMonth()+1
                                let day = acceptedDay.getDate()
                                console.log('today', year, month, day)
                                this.acceptedDate = year + '/' + month + '/' + day
                                console.log('月締有', this.acceptedDate)
                            }
                            console.log(this.acceptedDate)
                            // 月締完了月を超えているかの真偽判定
                            console.log('QQQ', days)
                            console.log(days.length)
                            let checkDays = []
                            for(let i = 0; i < days.length; i++){
                                console.log(new Date(days[i]))
                                // console.log(new Date(this.acceptedDate.replace(/-/g , "/")))
                                // checkDays.push(new Date(days[i].replace(/-/g , "/")) >= new Date(this.acceptedDate.replace(/-/g , "/")))
                                console.log(new Date(this.acceptedDate))
                                checkDays.push(new Date(days[i]) >= new Date(this.acceptedDate))
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
                                // 重複データの確認
                                let flg = 0
                                this.holidayDays = '1'
                                let list = []
                                for(let i = 0; i < this.term.length; i++) {
                                    console.log(this.term.length)
                                    let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))

                                    if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                                        
                                        flg++
                                        let data = {
                                            companyCode: this.userData.companyCode,
                                            requestNumber: this.reqNum,
                                            requestSubNumber: flg,
                                            requestUserId: this.userData.userId,
                                            holidayTypeKbn: this.holidayType,
                                            holidayPlanDay: this.term[i].replaceAll('/', ''),
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
                                        // 申請処理
                                        this.getCurrentTime()
                                        this.setReqNum()
                                        this.requestSaveData()
                                        console.log(this.requestData)
                                        this.$refs.approvalDialog.applyDialogOpen(applyFlg)
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
            approvalSave(number) {
                this.getCurrentTime()
                this.reqNum=number
                console.log(this.reqNum)
                this.submit()
            },
            // 申請処理
            submit() {
                console.log('this.selectedDayList', this.selectedDayList)
                if(this.selectedDayList){
                    this.term = this.selectedDayList.sort()
                }
                console.log('this.term', this.term)
                this.holidayDays = '1'
                // 新規申請
                if(this.changebyFlg == "1"){
                    let flg = 0
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))

                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg++
                            let datainsertreqhol = {
                                companyCode: this.userData.companyCode,
                                requestNumber: this.reqNum,
                                requestSubNumber: flg, 
                                requestUserId: this.userData.userId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('/', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: ''
                            }

                            insertRequestHolidayList(datainsertreqhol).then(res =>{
                                console.log(res)
                                }).catch(error => {
                            })
                        }
                    }

                        Message({
                        message: '夏季休暇申請を提出しました。',
                        type: 'success',
                        duration: 1500
                    })

                // 保存→申請
                }else if(this.changebyFlg == "2") {
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
                        remark: ''
                    }
                    list.push(data)
                    console.log('selectDate', this.form.selectDate)
                    if(this.form.selectDate){
                        this.term = this.dayList(this.form.selectDate[0],this.form.selectDate[1])
                    }
                    if(this.selectedDayList){
                        this.term = this.selectedDayList.sort()
                    }
                    console.log('this.term', this.term)
                    for(let i = 0; i < this.term.length; i++) {
                        console.log(this.term.length)
                        let bool =  this.holiday.find( item => item.holidayDate == this.term[i].replaceAll('/', ''))
                        if(1 <= new Date(this.term[i]).getDay() && new Date(this.term[i]).getDay() <= 5 && !bool) {
                            flg2++
                            let dataupdatereqhol = {
                                companyCode: this.rowdata.companyCode,
                                requestNumber: this.rowdata.requestNumber,
                                requestSubNumber: flg2,
                                requestUserId: this.rowdata.requestUserId,
                                holidayTypeKbn: this.holidayType,
                                holidayPlanDay: this.term[i].replaceAll('/', ''),
                                holidayTimeKbn: this.nameSearch(this.holidayTimeApply),
                                holidayDays: this.holidayDays,
                                holidayReasonKbn: this.form.remarkreq,
                                remark: '',
                                workDayoffRequestNumber: ''
                            }
                            list.push(dataupdatereqhol)
                        }
                    }
                    console.log(list)
                    console.log(list)
                    updateRequestHolidayList(list).then(res =>{
                        console.log(res)
                    }).catch(error => {
                    })
                    Message({
                        message: '夏季休暇申請を提出しました。',
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
                    requestOverview: this.numSearch(this.holidayType)+ '申請',
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
            // 詳細ボタン
            setSummerHoliday() {
                let data = {
                        companyCode: this.rowdata.companyCode,
                        requestUserId: this.rowdata.requestUserId,
                        requestNumber: this.rowdata.requestNumber
                        }
                getRequestHolidayList(data).then(res => {
                        console.log(res.data)
                        this.detailList = res.data
                        console.log('this.detailList', this.detailList)
                        //  申請番号
                        this.reqNumber = this.detailList[0].requestNumber
                        console.log('requestNumber', this.reqNumber)
                        //  休暇理由区分
                        this.form.remarkreq = this.detailList[0].holidayReasonKbn
                        console.log(this.form.remarkreq)
                        //  備考
                        this.comment = this.detailList[0].remark
                        console.log(this.comment)
                        //  休暇予定日
                        let lastDay = this.detailList.length - 1
                            let selectDate1 = this.detailList[0].holidayPlanDay.substring(0,4) + '/' + this.detailList[0].holidayPlanDay.substring(4,6) + '/' + this.detailList[0].holidayPlanDay.substring(6,8)
                            let selectDate2 = this.detailList[lastDay].holidayPlanDay.substring(0,4) + '/' + this.detailList[lastDay].holidayPlanDay.substring(4,6) + '/' + this.detailList[lastDay].holidayPlanDay.substring(6,8)

                            let setDate1 = new Date(selectDate1)
                            let setDate2 = new Date(selectDate2)

                            let diffTime = setDate2.getTime() - setDate1.getTime();
                            let diffDay = Math.floor(diffTime / (1000 * 60 * 60 * 24));
                            console.log('diffDay', diffDay)
                            if(diffDay == 1 || diffDay == 2){
                                this.form.selectDate = [selectDate1,selectDate2]
                                this.onBlur()
                            }else if(diffDay !== 1 ||diffDay !== 2){
                                for(let i = 0; i < this.detailList.length; i++){
                                    console.log('this.radio',this.radio)
                                    this.radio = '2'
                                    console.log('this.detailList[0].holidayPlanDay',this.detailList[0].holidayPlanDay)
                                    if(this.detailList.length == '1'){
                                        this.form.planOne = this.detailList[0].holidayPlanDay.substring(0,4) + '/' + this.detailList[0].holidayPlanDay.substring(4,6) + '/' + this.detailList[0].holidayPlanDay.substring(6,8)
                                        this.countDate = '1'
                                    }else if(this.detailList.length == '2'){
                                        this.form.planOne = this.detailList[0].holidayPlanDay.substring(0,4) + '/' + this.detailList[0].holidayPlanDay.substring(4,6) + '/' + this.detailList[0].holidayPlanDay.substring(6,8)
                                        this.form.planTwo = this.detailList[1].holidayPlanDay.substring(0,4) + '/' + this.detailList[1].holidayPlanDay.substring(4,6) + '/' + this.detailList[1].holidayPlanDay.substring(6,8)
                                        this.countDate = '2'
                                    }else if(this.detailList.length == '3'){
                                        this.form.planOne = this.detailList[0].holidayPlanDay.substring(0,4) + '/' + this.detailList[0].holidayPlanDay.substring(4,6) + '/' + this.detailList[0].holidayPlanDay.substring(6,8)
                                        this.form.planTwo = this.detailList[1].holidayPlanDay.substring(0,4) + '/' + this.detailList[1].holidayPlanDay.substring(4,6) + '/' + this.detailList[1].holidayPlanDay.substring(6,8)
                                        this.form.planThree = this.detailList[2].holidayPlanDay.substring(0,4) + '/' + this.detailList[2].holidayPlanDay.substring(4,6) + '/' + this.detailList[2].holidayPlanDay.substring(6,8)
                                        this.countDate = '3'
                                    }
                                }
                            }
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
                    this.radioDays = false

                }else if(this.changebyFlg == "2"){
                    if(this.rowdata.requestStatusKbn == "003"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                        this.radioDays = true
                    }
                    if(this.rowdata.requestStatusKbn == "003"&&this.userData.userId==this.rowdata.requestUserId){
                        this.getBack = true
                    }
                    if(this.rowdata.requestStatusKbn == "999"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                        this.radioDays = true
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
                    this.radioDays = true
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
            // 削除処理
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
    margin-left: 75px;
    font-size: 14px;
    margin-bottom: 20px;
    color: rgb(83, 83, 83);
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
.summmer-radio-btn {
    margin: 10px 0 30px 50px;
}
</style>