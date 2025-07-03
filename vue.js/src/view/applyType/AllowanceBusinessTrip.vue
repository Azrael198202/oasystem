<template>
    <div class="apply-area">
        <applyHeader title="出張手当申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <!-- 空白 -->
        <el-form style="margin: 50px">
        </el-form>
        <!-- テーブル -->
        <el-form ref="form" :rules="rules" :model="form" label-width="120px">
            <div class="header1">
                <div class='apply-month1'>
                    <!-- 年月 -->
                    <el-form-item prop="yearMonth" label-width="120px" label="年月">                                                              
                        <el-date-picker
                            v-model="form.yearMonth"
                            type="month"
                            placeholder=""
                            format="yyyy年MM月"
                            value-format="yyyyMM"
                            style="width: 150px"
                            :disabled="monthOff">
                        </el-date-picker>
                        <span class="blank-month">分申請</span>
                        <el-button class="business-reference-button" size="small" round @click="onReference()" v-if="refFlg">参照</el-button>
                    </el-form-item>
                </div>
                <div>
                    <div class="pdf-button1" v-loading='exportLoading' @click="exportPdf()" v-if="pdfFlg">PDF</div>
                </div>
            </div>
            <!-- 出張日当 -->
            <el-form-item prop="businessTripAllowance" label-width="120px" label="出張日当">                                                              
                <div class='input-box'>                                                              
                    <el-input placeholder="" v-model.number="form.businessTripAllowance" maxlength="8" :disabled="inputOff" style="width:230px" clearable>                                                              
                        <template slot="append">円</template>                                                              
                    </el-input>                                                              
                </div>                                                              
            </el-form-item>
            <!-- 出張日数 -->
            <el-form-item prop="businessTripDays" label-width="120px" label="出張日数">                                                              
                <div class='input-box'>                                                              
                    <el-input placeholder="" v-model.number="form.businessTripDays" maxlength="2" :disabled="inputOff" style="width:120px" clearable>                                                              
                        <template slot="append">日</template>                                                              
                    </el-input>                                                              
                </div>                                                              
            </el-form-item>
            <!-- 合計金額 -->
            <el-form-item label="合計金額">
                <span>：{{ totalAmount() }}円</span>
            </el-form-item>
            <!-- 備考 -->
            <el-form-item prop="remarks" label="備考">
                <el-input
                    placeholder="期間や出張先などを入力してください"
                    v-model="form.remarks"
                    type="textarea"
                    maxlength="200"
                    class="textarea-box"
                    :row="10"
                    :disabled="inputOff"
                    clearable>
                </el-input>
            </el-form-item>
        </el-form>
        <div class="allowanceBusinessTrip-btn">
            <el-row>
                <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 10 : 6" class="flex-start">
                    <div class="gray-btn" @click="onDeleteConfirmClick()" v-if="delFlg">削除</div>
                    <div class="itirann-btn" @click="closeA()" v-if="changebyFlg == '2'">申請一覧に戻る</div>
                    <div class="itirann-btn" @click="closeB()" v-if="changebyFlg == '3'">承認一覧に戻る</div>
                </el-col>
                <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 12 : 18" class="flex-end">
                    <div class="blue-btn" @click="OpenGetBackDialog()" v-if="getBack">取戻</div>
                    <div class="red-btn" @click="remandDialog()" v-if="changebyFlgwf" style='margin-right: 30px'>差戻</div>
                    <div class="green-btn" @click="applyDialog(1)" v-if="changebyFlgapp">承認</div>
                </el-col>
                <el-col :span="changebyFlg == '2' && (rowdata.requestStatusKbn == '001'||rowdata.requestStatusKbn == '010') ? 14 : 24" class="flex-end">
                    <div class="green-btn" @click="save()" v-if="modifyFlgsave" :loading="loading" style='margin-right: 30px'>保存</div>
                    <div class="orange-btn" @click="applyDialog(0)" v-if="reqFlg">申請</div>
                </el-col>
            </el-row>
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
        <!-- 参照ダイアログ -->
        <el-dialog title=""
            :visible.sync="referenceDialogVisible"
            width="300px"
            center>
            <div>
                参照する年月を選択してください。
            </div>
            <div slot="footer" class="dialog-footer">
                <el-date-picker
                    v-model="referenceMonth"
                    type="month"
                    placeholder=""
                    format="yyyy年MM月"
                    value-format="yyyyMM"
                    style="width: 140px; margin-right: 25px">
                </el-date-picker>
                <el-button type="primary" @click="searchRefMonth()" style="width: 80px">確定</el-button>
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
    import { getUserInfo } from '@/utils/auth';
    import { deleteAllWf } from '@/api/wfList'
    import { insertLeaveRequest,deleteRequest } from '@/api/requestList';
    import { insertAllowance, getRequestAllowance, deleteRequestAllowance ,searchReAllowanceMonthData ,searchMonth,exportToPDF} from '@/api/requestAllowance';
    import { Message } from 'element-ui';


    export default {
        props: ['userData'],
        name: "AllowanceBusinessTrip",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog,
        },
        // 初期画面
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            // 会社コード
            this.companyCode = getUserInfo().companyCode
            // 今日の日付取得
            this.getCurrentTime()
            // 申請番号取得
            this.setReqNum()
            // 年月の初期処理
            this.getApplyYm()
            // 各ボタンの表示,非表示
            this.statusSetBtn()

            // 詳細の処理
            if(this.changebyFlg == "2" || this.changebyFlg == "3"){
                this.setAllowaneceBusinessTrip()
            }
        },
        data() {
            return {
                form:{
                    // 年月
                    yearMonth:'',
                    // 出張手当
                    businessTripAllowance:'',
                    // 出張日数
                    businessTripDays:'',
                    // 備考
                    remarks:'',
                },
                // エラーメッセージが表示
                rules:{
                    yearMonth:[{ required: true, message: '年月は入力必須です。', trigger: ['blur','change'] }],
                    businessTripAllowance:[{ type: 'number', required: true, message: '出張手当は数字で入力必須です。', trigger: ['blur','change'] }],
                    businessTripDays:[{ type: 'number', required: true, message: '出張日数は数字で入力必須です。', trigger: ['change','blur'] }],
                    remarks:[{ required: true, message: '備考は入力必須です。', trigger: ['change','blur'] }],
                },
                changebyFlg: '',
                requestData: [],
                rowdata: [],
                // 申請番号
                reqNum:'',
                // 今日の日付取得
                aTime: '',
                yMd: '',
                // 入力項目
                inputOff: false,
                // 入力項目（年月）
                monthOff: false,
                monthList:'',
                // 参照
                refDataList:'',
                // 参照ボタン
                refFlg: true,
                // 参照ダイアログ
                referenceDialogVisible: false,
                // 参照年月
                referenceMonth: '',
                // 申請年月
                applyMonth: '',
                // 詳細
                detailList: [],
                // 詳細:申請番号
                reqNumber:'',
                // 保存ボタン
                modifyFlgsave: true,
                loading: false,
                // 申請ボタン
                reqFlg: true,
                // 取戻
                getBack: false,
                // 承認
                changebyFlgapp: true,
                // 差戻
                changebyFlgwf: true,
                // 削除ボタン
                delFlg: true,
                deleteConfirmDialogVisible: false,
                pdfFlg: false,
                exportLoading: '',
            }
        },
        methods: {
            // 今日の日付取得
            getCurrentTime(){
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
                this.mYear = yy
            },
            // 表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            // 合計金額
            totalAmount(){
                return ((this.form.businessTripAllowance) * (this.form.businessTripDays))
            },
            // 申請年月を取得（承認済みのデータの内一番最新の年月の翌月を取得、データがない場合は当月をセットする）
            getApplyYm(){
                // 申請（手当申請）画面
                if(this.changebyFlg == "1"){
                    const that = this
                    let data = {
                        companyCode: this.companyCode,
                        requestUserId:  this.userData.userId,
                        requestTypeKbn: '043'
                    }
                    // 手当申請：年月取得
                    searchMonth(data).then(res => {
                        that.monthList = res.data
                        // データがない場合は当月をセット
                        if(this.monthList.length == "0"){
                            let nowDate = new Date();
                            let month = nowDate.getMonth() + 1 + ""
                            this.form.yearMonth = nowDate.getFullYear() + "" + (month.length == 1 ? ("0"+month) : month)
                        // 承認済みのデータの内一番最新の年月の翌月を取得
                        }else{
                            // 年月
                            this.form.yearMonth = that.monthList[0].applyYm
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            // 各ボタンの表示,非表示
            statusSetBtn(){
                // 申請（手当申請）画面
                if(this.changebyFlg == "1"){
                    this.delFlg = false
                    this.getBack = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.inputOff = false
                    this.monthOff = false
                    this.refFlg = true
                    this.pdfFlg = false
                // 申請一覧画面
                }else if(this.changebyFlg == "2"){
                    if(this.rowdata.requestStatusKbn == "001"){
                        this.monthOff = true
                        this.refFlg = false
                    }
                    if(this.rowdata.requestStatusKbn == "003"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                        this.monthOff = true
                        this.refFlg = false
                    }
                    if(this.rowdata.requestStatusKbn == "003"&&this.userData.userId==this.rowdata.requestUserId){
                        this.getBack = true
                    }
                    if(this.rowdata.requestStatusKbn == "010"){
                        this.monthOff = true
                        this.refFlg = false
                    }
                    if(this.rowdata.requestStatusKbn == "999"){
                        this.delFlg = false
                        this.modifyFlgsave = false
                        this.reqFlg = false
                        this.inputOff = true
                        this.monthOff = true
                        this.refFlg = false
                    }
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.pdfFlg = false
                // 承認一覧画面
                }else if(this.changebyFlg == "3"){
                    if(this.rowdata.wfStatusKbn =="020"){
                        this.changebyFlgwf = true
                        this.changebyFlgapp = true
                    }else if(this.rowdata.wfStatusKbn == "021"){
                        this.changebyFlgwf = false
                        this.changebyFlgapp = false
                    }else if(this.rowdata.wfStatusKbn =="025"|| this.rowdata.wfStatusKbn =="900"){
                        this.changebyFlgapp = false
                    }
                    this.delFlg = false
                    this.modifyFlgsave = false
                    this.reqFlg = false
                    this.getBack = false
                    this.refFlg = false
                    this.inputOff = true
                    this.monthOff = true
                    this.pdfFlg = true
                }
            },
            // 詳細ボタン押下時の処理（出張手当）
            setAllowaneceBusinessTrip(){
                const that = this
                let data = {
                    companyCode:   this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                // 手当申請：詳細処理
                getRequestAllowance(data).then(res => {
                    that.detailList = res.data
                    // 申請番号
                    this.reqNumber = that.detailList[0].requestNumber
                    // 年月
                    this.form.yearMonth = that.detailList[0].applyYm
                    // 出張日当
                    this.form.businessTripAllowance = that.detailList[0].allowance
                    // 出張日数
                    this.form.businessTripDays = that.detailList[0].quantity
                    // 備考1
                    this.form.remarks = that.detailList[0].remark1
                }).catch(error => {
                    console.log(error)
                })
            },
            // 参照ダイアログ
            onReference(){
                this.referenceDialogVisible = true
                this.referenceMonth = ''
            },
            // 参照年月検索
            searchRefMonth(){
                let refData = { 
                    companyCode: this.userData.companyCode,
                    applyYm:     this.referenceMonth,
                    requestUserId: this.userData.userId,
                    requestTypeKbn: '043'
                }
                // 手当申請：参照処理
                searchReAllowanceMonthData(refData).then(res =>{
                    this.refDataList = res.data
                    if (this.changebyFlg == "1"){
                        this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                    }else if (this.changebyFlg == "2"){
                        this.reqNum = this.rowdata.requestNumber
                    }
                    if(this.refDataList.length >= 1){
                        // 年月
                        this.form.yearMonth = this.refDataList[0].applyYm
                        // 出張日当
                        this.form.businessTripAllowance = this.refDataList[0].allowance
                        // 出張日数
                        this.form.businessTripDays = this.refDataList[0].quantity
                        // 備考1
                        this.form.remarks = this.refDataList[0].remark1
                    }else{
                        Message({
                            message: '選択年月のデータは存在しません。',
                            type: 'error',
                            duration: 4000
                        })
                    }
                    this.referenceDialogVisible = false
                }).catch(error => {
                    console.log(error)
                })
            },
            // 新しく申請番号取得&保存する
            approvalSave(number) {
                // ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                this.requestAllowanceSave()
            },
            // 保存ボタン押下時の処理
            save(){
                this.$refs['form'].validate(valid => {
                    if(valid){
                        // ステータス区分：保存
                        this.requestAllowanceSave()
                        this.requestSaveData()
                        this.requestSave(this.requestData)
                        setTimeout(()=>{
                            this.$router.go({path: this.$router.currentRoute.path, force: true})
                        },200)
                    }
                })
            },
            // 保存処理
            requestAllowanceSave(){
                let data = {
                    companyCode:   this.userData.companyCode,
                    applyYm:       this.form.yearMonth,
                    requestNumber: this.reqNum,
                    requestUserId: this.userData.userId,
                    requestTypeKbn: '043',
                    allowance:     this.form.businessTripAllowance,
                    quantity:      this.form.businessTripDays,
                    total:         this.totalAmount(),
                    remark1:       this.form.remarks,
                }
                // 手当申請：出張手当の保存処理
                insertAllowance(data).then(res => {
                    Message({
                        message: '手当申請（出張）を保存しました。',
                        type: 'success',
                        duration: 4 * 1000
                    })
                }).catch(error => {
                    console.log(error)
                })
            },
            // 申請ボタン・承認ボタン押下時の処理
            applyDialog(flg){
                // 申請時
                if(flg == 0){
                    this.userData.userId = this.userData.userId
                // 承認時
                }else if(flg == 1){
                    this.userData.userId = this.rowdata.requestUserId
                }
                this.$refs['form'].validate(valid => {
                    if(valid){
                        this.requestSaveData()
                        this.$refs.approvalDialog.applyDialogOpen(flg)
                    }
                })
            },
            // 申請テーブルに格納するデータ（保存、申請）
            requestSaveData(){
                this.requestData = {
                    companyCode:     this.userData.companyCode,
                    requestNumber:   this.reqNum,
                    requestUserId:   this.userData.userId,
                    requestDate:     this.yMd,
                    requestTypeKbn:  '043',
                    requestOverview: '手当申請（出張）' + this.form.yearMonth.substring(0,4) + '年' + this.form.yearMonth.substring(4,6) + '月分',
                    requestStatusKbn:'001'
                }
            },
            // 申請処理
            requestSave(data){
                // 申請テーブル：レコード登録
                insertLeaveRequest(data).then(res => {
                }).catch(error => {
                    console.log(error)
                })
            },
            // 削除のポップアップ
            onDeleteConfirmClick(){
                this.deleteConfirmDialogVisible = true
            },
            // 削除ボタン押下時の処理
            deleteApplication(){
                // 申請状態 = 保存
                if(this.rowdata.requestStatusKbn == '001'){
                    this.deleteReq()
                    this.deleteRequestA()
                // 申請状態 = 差戻
                }else if(this.rowdata.requestStatusKbn == '010'){
                    this.deleteReq()
                    this.deleteRequestA()
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
            // 申請テーブルのレコード削除
            deleteReq(){
                let datadeletereq = {
                    companyCode:   this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                // 申請テーブル：レコード削除
                deleteRequest(datadeletereq).then(res => {
                }).catch(error => {
                    console.log(error)
                })
            },
            // 手当申請テーブルのレコード削除処理
            deleteRequestA(){
                let data = {
                    companyCode:   this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                // 手当申請：レコード削除処理
                deleteRequestAllowance(data).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
            },
            // ワークフローテーブルのレコード削除
            deleteAllW(){
                let data = {
                    companyCode:   this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                // ワークフローテーブル：レコード削除処理
                deleteAllWf(data).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
            },
            // 取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            // 差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            // 戻るボタン（申請一覧）
            closeA(){
                this.$router.go({path: this.$router.currentRoute.path, force: true})
            },
            // 戻るボタン（承認一覧）
            closeB(){
                this.$router.go(-1);
            },
            exportPdf(){
                if(this.exportLoading == true){
                    return
                }
                this.exportLoading = true

                let data = {
                    companyCode:   this.userData.companyCode,
                    applyYm:       this.form.yearMonth,
                    requestNumber: this.rowdata.requestNumber,
                    requestUserId: this.rowdata.requestUserId,
                    allowance:     this.form.businessTripAllowance,
                    quantity:      this.form.businessTripDays,
                    total:         this.totalAmount(),
                    remark1:       this.form.remarks,
                }
                console.log('data',data)
                exportToPDF(data).then(res => {
                    console.log('年月',this.applyMonth);
                    let blob = new Blob([res.data], {type: "application/pdf"})
                    const href = URL.createObjectURL(blob) 
                    // window.open(href);    IEでプレビュー表示
                    const a = document.createElement('a') 
                    a.style.display = 'none'
                    a.href = href
                    console.log('this.rowdata', this.rowdata)
                    a.download = this.rowdata.requestUserId + "_" + this.rowdata.requestNumber + "_" + this.form.yearMonth + "_出張手当.pdf" //指定下载文件名
                    a.click() 
                    console.log(res.data)
                    this.exportLoading = false
                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            },
        }

    }
</script>

<style lang="scss" scope>
// 参照ボタン
.business-reference-button {
    font-weight: bold;
    font-size: 14px;
    width: 80px;
}
// 年月と参照ボタンの間の空白
.blank-month {
    font-size: 14px;
    margin-right: 50px;
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
// ボタンの修正（一部）
.allowanceBusinessTrip-btn {
    padding: 20px 120px 0 120px;
}
//PDFボタン
.pdf-button1 {
    margin-right: 100px;
    transition: 0.5s;
    height: 30px;
    background-color: #fff;
    border: 2px #ff0000 solid;
    width: 80px;
    font-size: 14px;
    line-height: 30px;
    text-align: center;
    box-shadow: 0 4px 6px 0 rgba(250, 0, 0, 0.2);
    border-radius: 30px;
    cursor: pointer;
    color: #ff0000;
    font-weight: bold;
}
.pdf-button1:hover {
    transition: 0.5s;
    border: 2px #fbd4d4 solid;
}
.header1 {
    display: flex;
    justify-content: space-between;
}
</style>