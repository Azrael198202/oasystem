<template>
    <div class="apply-area">
        <!-- computedを使ってpropsから申請名を決定できるようにしたい -->
        <apply-header v-if="holidayCategory == '010'" title="有給休暇申請"></apply-header>
        <apply-header v-if="holidayCategory == '020'" title="代休申請"></apply-header>
        <apply-header v-if="holidayCategory == '030'" title="特別休暇申請"></apply-header>
        <apply-header v-if="holidayCategory == '040'" title="夏季休暇申請"></apply-header>
        <apply-header v-if="holidayCategory == '050'" title="振替休日申請"></apply-header>
        <div class="leave-num">
            <div class='white-btn leave-info' v-if="holidayCategory=='010'||holidayCategory=='020'" @click="leaveNum()" style="width: 100px">有休情報</div>
            <div class='white-btn dayoff-info' v-if="holidayCategory=='010'||holidayCategory=='020'" @click="dayoffNum()" style="width: 100px">代休情報</div>
        </div>
        <div class="apply-leave">
            <el-form ref="form" :model="form" label-width="120px">
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
                    <el-radio-group v-model="holidayTimeApplay" v-if="holidayCategory=='010'||holidayCategory=='020'||holidayCategory=='050'" class="work-time">
                        <el-radio-button
                            v-for="(item , index) in WorkTimeList"
                            :key="index"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-radio-button>
                    </el-radio-group>
                </div>
                <el-form-item label="期間" v-if="holidayTimeKbn == '010'">
                    <el-date-picker
                        v-model="selectDate"
                        type="daterange"
                        range-separator="～"
                        start-placeholder="yyyy-mm-dd"
                        end-placeholder="yyyy-mm-dd"
                        value-format="yyyy-MM-dd"
                        key='1'
                        style="width: 300px">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="期間" v-if="holidayTimeKbn != '010'">
                    <el-date-picker
                        v-model="selectHalf"
                        type="date"
                        placeholder="休暇予定日"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        key='2'
                        style="width: 300px">
                    </el-date-picker>
                </el-form-item>
                <el-form-item v-if="holidayCategory != '050'" label="休暇理由">
                    <el-select v-model="remarkreq" placeholder="休暇理由区分" class="reason-option">
                        <el-option label="" value=""></el-option>
                        <el-option
                            v-for="item in holidayKbnList"
                            :key="item.categoryKbn"
                            :label="item.categoryKbnName"
                            :value="item.categoryKbn">
                            {{item.categoryKbnName}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="備考" v-if="holidayCategory=='010'||holidayCategory=='020'||holidayCategory=='030'">
                    <el-input
                    ref="textareaInput"
                    type="textarea"
                    v-model="comment"
                    maxlength="200"
                    class="textarea-box"
                    :row="10">
                    </el-input>
                </el-form-item>
            </el-form>
            <div class="leave-btn">
                <el-row>
                    <el-col :span="24" class="flex-start">
                        <!-- <div class="back-btn" @click="closeB()">戻る</div> -->
                        <div class="back-btn" @click="onDeleteConfirmClick()" v-if="changebyFlg == '2' && this.rowdata.requestStatusKbn == '001'">削除</div>
                        <div class="green-btn" @click="save()" v-if="modifyFlgsave">保存</div>
                        <div class="orange-btn" @click="applyopenreq($event)" v-if="reqFlg">申請</div>
                        <div class="blue-btn"  @click="applyopende()" v-if="changebyFlg == '2' && this.rowdata.requestStatusKbn == '003'">取戻</div>
                        <div class="red-btn" @click="applyopenwf()" v-if="changebyFlgwf">差戻</div>
                        <div class="green-btn" @click="applyopenapp($event)" v-if="changebyFlgapp">承認</div>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 有休情報のポップアップ -->
        <el-dialog title="工事中" :visible.sync="dialogVisibleA">
            テストA
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleA = false">Cancel</el-button>
            </div>
        </el-dialog>
        <!-- 代休情報のポップアップ -->
        <el-dialog title="工事中" :visible.sync="dialogVisibleB">
            テストB
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleB = false">Cancel</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import applyHeader from '../component/applyHeader.vue';
    import { getUserInfo, getUserDetailedInfo, setUserDetailedInfo } from '@/utils/auth'
    import { getCategory, getHoliday } from '@/api/category'
    import { insertLeaveRequest, searchRequest, insertRequest, updateLaRequest, deleteRequest } from '@/api/requestList'
    import { getRequestHolidayList,  insertRequestHolidayList, deleteRequestHoliday} from '@/api/requestHoliday'

    export default {
        name: "Holiday",
        props: ['holidayCategory'],
        components: {
            applyHeader
        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            this.getCurrentTime()
            // 全日・午前・午後のオプション
             let WorkTimeData = {
                companyCode: getUserInfo().companyCode,
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
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '040',
            }
            getCategory(LeaveKbnData).then(res => {
                console.log(res)
                this.LeavekbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 有給休暇理由区分のオプション
            let LeaveRKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '041',
            }
            getCategory(LeaveRKbnData).then(res => {
                console.log(res)
                this.LeaveRkbnList=res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 特別休暇理由区分のオプション
            let SpecialKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '042',
            }
            getCategory(SpecialKbnData).then(res => {
                console.log(res)
                this.SpecialkbnList=res.data.filter((e=>{return e.categoryKbn!=='010'}))
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 夏季休暇理由区分のオプション
            let SummerKbnData = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '042',
            }
            getCategory(SummerKbnData).then(res => {
                console.log(res)
                this.SummerKbnList=res.data.filter((e=>{return e.categoryKbn=='010'}))
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
        },

        data() {
            return {
                LeavekbnList: {index: '',categoryKbnName: '', categoryKbn: '' },
                SpecialkbnList: {index: '',categoryKbnName: '' },
                SummerKbnList: {index: '',categoryKbnName: '' },
                LeaveRkbnList: {index: '',categoryKbnName: '' },
                WorkTimeList: {index: '',categoryKbnName: '' },
                selectApply: '',
                holidayTimeApplay: '全日',
                // 休暇全日選択(複数日)
                selectDate: '',
                // 休暇半日選択(1日のみ)
                selectHalf: '',
                dialogVisibleA: false,
                dialogVisibleB: false,
                changebyFlg: '',
                changebyFlgapp: false,
                changebyFlgwf: false,
                // 申請概要
                remarkreq: '',
                comment: '',
                rowdata: [],
                aTime: '',
                yMd: '',
                // 保存ボタン
                modifyFlgsave: true,
                // 削除ボタン
                deleteConfirmDialogVisible: false,
                // 申請ボタン
                reqFlg: true,
                reqStatus: '',
                reqNum: '',
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
            }
        },

        computed: {
            //区分値を全日・午前・午後のラジオボタンから取得する処理
            holidayTimeKbn: function() {
                return this.nameSearch(this.holidayTimeApplay)
            },
            //表示している画面から休暇種類の値を決定する処理
            holidayType: function() {
                if(this.holidayTimeKbn=='020'||this.holidayTimeKbn=='021'){
                    if(this.holidayCategory=='010'||this.holidayCategory=='020'||this.holidayCategory=='050'){
                        return this.holidayCategory.slice(0,2)+'1'
                    }
                }
                return this.holidayCategory
            },
            //表示している画面から休暇理由のリストを決定する処理
            holidayKbnList: function() {
                this.clearStatus()
                if(this.holidayCategory=='010'||this.holidayCategory=='020'){
                    return this.LeaveRkbnList
                }else if(this.holidayCategory=='030'){
                    return this.SpecialkbnList
                }else if(this.holidayCategory=='040'){
                    return this.SummerKbnList
                }
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
            },
            //表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + getUserInfo().companyCode + getUserInfo().userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            //申請テーブルの保存処理
            requestSave(){
                let data = {
                    companyCode: getUserInfo().companyCode,
                    requestNumber: this.reqNum,
                    requestUserId :getUserInfo().userId,
                    requestDate: this.yMd,
                    requestTypeKbn: '020',
                    requestOverview: this.holidayType,
                    requestStatusKbn: this.reqStatus
                }
                insertLeaveRequest(data).then(res => {
                console.log(data)
                console.log(res)
                    Message({
                        message: '申請を保存しました。',
                        type: 'success',
                        duration: 1500
                    })
                }).catch(error => {
                })
            },
            nameSearch(name) {
                for(let i = 0; i < this.WorkTimeList.length;i++) {
                    if(this.WorkTimeList[i].categoryKbnName == name) {
                        return this.WorkTimeList[i].categoryKbn
                    }
                }
            },
            // 有休情報
            leaveNum () {
                this.dialogVisibleA = true
            },
            // 代休情報
            dayoffNum () {
                this.dialogVisibleB = true
            },
            save () {
                //ステータス区分：保存
                this.reqStatus = '001'
                this.getCurrentTime()
                this.setReqNum()
                //ここに休暇申請内容テーブルの保存処理を追加する
                this.requestSave()
            },
            clearStatus(){
                this.holidayTimeApplay='全日'
                this.selectDate=''
                this.selectHalf=''
                this.remarkreq=''
                this.comment=''
            }
        }
    }
</script>

<style lang="scss" scope>
</style>