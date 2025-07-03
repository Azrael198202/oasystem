<template>
    <div class="apply-area">
        <applyHeader title="リーダー手当申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <!--  空白 -->
        <el-form style="margin: 50px"> 
        </el-form>
        <!-- <el-form ref="form" :rules="rules" :model="form" label-width="120px"> -->
        <el-form ref="form" :rules="rules" :model="form" label-width="150px">
            <el-form-item prop="yearMonth" label-width="150px" label="年月" required >                                                              
                <el-date-picker
                    v-model="form.yearMonth"
                    type="month"
                    placeholder=""
                    format="yyyy年MM月"
                    value-format="yyyyMM"
                    style="width: 150px"
                    :disabled="monthOff">
                </el-date-picker>
                <span class="leader-blank-month">分申請</span>
                <el-button class="reference-leader-button" size="small" round @click="onReference()" v-if="refFlg">参照</el-button>
            </el-form-item>
            <el-form-item  prop="projectId" label-width="150px" label="プロジェクトID" required>
                <div class='text-box'>
                    <el-input  placeholder="" v-model="form.projectId" maxlength="8" :disabled="inputOff"></el-input>
                </div>
            </el-form-item>
            <el-form-item  prop="projectName" label-width="150px" label="プロジェクト名" required>
                <div class='text-box'>
                    <el-input  placeholder="" v-model="form.projectName" maxlength="255" :disabled="inputOff"></el-input>
                </div>
            </el-form-item>
            <el-form-item prop="allowanceFee" label-width="150px" label="手当額" required>                                                              
                <div class='input-box'>                                                              
                    <el-input placeholder="" v-model.number="form.allowanceFee" maxlength="8" :disabled="inputOff" style="width:230px" clearable>                                                              
                        <template slot="append">円</template>                                                              
                    </el-input>                                                              
                </div>                                                              
            </el-form-item>
            <el-form-item prop="numPerson" label-width="150px" label="人数" required>                                                              
                <div class='input-box'>                                                              
                    <el-input placeholder="" v-model.number="form.numPerson" maxlength="3" :disabled="inputOff" style="width:120px" clearable>                                                              
                        <template slot="append">人</template>                                                              
                    </el-input>                                                              
                </div>                                                              
            </el-form-item>
             <!-- 合計金額 -->
            <el-form-item label="合計金額">
                <span>：{{ totalAmount() }}円</span>
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
    import { deleteAllWf } from '@/api/wfList'
    import { insertLeaveRequest,deleteRequest } from '@/api/requestList';
    import { insertAllowance, getRequestAllowance, deleteRequestAllowance, searchReAllowanceMonthData, searchMonth } from '@/api/requestAllowance';
    import { Message } from 'element-ui';

    export default {
        props: ['userData'],
        name: "AllowanceProject",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog,
        },
        created() {
            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));         
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
                this.setAllowaneceProject()
            }
        },
        data() {
            return {
                changebyFlg: '',
                requestData: [],
                rowdata: [],
                // 申請番号
                reqNum:'',
                // 申請年月
                applyMonth: '',
                // 今日の日付取得
                aTime: '',
                yMd: '',
                // 入力項目（年月）
                monthOff: false,
                monthList:'',
                // 入力項目
                inputOff: false,
                // 詳細:申請番号
                reqNumber:'',
                // 詳細ボタン
                detailList: [],
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
                // 参照ボタン
                refFlg: true,
                deleteConfirmDialogVisible: false,
                // 参照ダイアログ
                referenceDialogVisible: false,
                // 参照年月
                referenceMonth: '',
                // 参照
                refDataList:'',
                form: {
                        yearMonth: '',
                        projectId: '',
                        projectName: '',
                        allowanceFee: '',
                        numPerson: ''
                },
                // エラーメッセージが表示
                rules:{
                    yearMonth:[{ required: true, message: '年月は入力必須です。', trigger: ['blur','change'] }],
                    projectId:[{ required: true, message: 'プロジェクトIDは入力必須です。', trigger: ['blur','change'] }],
                    projectName:[{ required: true, message: 'プロジェクト名は入力必須です。', trigger: ['blur','change'] }],
                    allowanceFee:[{ type: 'number', required: true, message: '手当額は数字で入力必須です。', trigger: ['blur','change'] }],
                    numPerson:[{ type: 'number', required: true, message: '人数は数字で入力必須です。', trigger: ['change','blur'] }]
                },

            }
        },
        computed: {
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
            // 合計金額
            totalAmount(){
                return ((this.form.allowanceFee) * (this.form.numPerson))
            },
            // 申請年月を取得（承認済みのデータの内一番最新の年月の翌月を取得、データがない場合は当月をセットする）
            getApplyYm(){
                // 申請（手当申請）画面
                if(this.changebyFlg == "1"){
                    const that = this
                    let data = {
                        companyCode: this.userData.companyCode,
                        requestUserId:  this.userData.userId,
                        requestTypeKbn: '042'
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
            // 表示している画面が新規か詳細ボタンからの呼び出しかを判定し、リクエストナンバーを新規作成または取得する
            setReqNum(){
                if(this.changebyFlg == "1"){
                    this.reqNum = this.aTime + this.userData.companyCode + this.userData.userId
                }else if(this.rowdata.requestStatusKbn == "010" || this.changebyFlg == "2"){
                    this.reqNum = this.rowdata.requestNumber
                }
            },
            // 各ボタンの表示,非表示
            statusSetBtn(){
                if(this.changebyFlg == "1"){
                    this.delFlg = false
                    this.getBack = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.inputOff = false
                    this.monthOff = false
                    this.refFlg = true

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
                    this.monthOff = true
                    this.inputOff = true
                }
            },
            // 詳細ボタン押下時の処理（リーダー手当）
            setAllowaneceProject(){
                const that = this
                let data = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                //　手当申請：詳細処理
                getRequestAllowance(data).then(res => {
                    that.detailList = res.data
                    //  申請番号
                    this.reqNumber = that.detailList[0].requestNumber
                    //  年月
                    this.form.yearMonth = that.detailList[0].applyYm
                    //　プロジェクトID
                    this.form.projectId = that.detailList[0].projectId
                    //　プロジェクト名
                    this.form.projectName = that.detailList[0].projectName
                    //　手当額
                    this.form.allowanceFee = that.detailList[0].allowance
                    //　人数
                    this.form.numPerson = that.detailList[0].quantity
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
                    requestTypeKbn: '042'
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
                        //  年月
                        this.form.yearMonth = this.refDataList[0].applyYm
                        //　プロジェクトID
                        this.form.projectId = this.refDataList[0].projectId
                        //　プロジェクト名
                        this.form.projectName = this.refDataList[0].projectName
                        //　手当額
                        this.form.allowanceFee = this.refDataList[0].allowance
                        //　人数
                        this.form.numPerson = this.refDataList[0].quantity

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
            approvalSave(number){
                //ステータス区分：保存
                this.getCurrentTime()
                this.reqNum=number
                this.requestAllowanceSave()
            },
            // 保存ボタン押下時の処理
            saveBtn(){
                this.$refs['form'].validate(valid => {
                    if(valid){
                        // ステータス区分：保存
                        this.requestAllowanceSave()
                        this.requestSaveData()
                        this.requestSave(this.requestData)
                        setTimeout(()=>{
                            this.$router.go({path: this.$router.currentRoute.path, force: true})
                        },200)
                    }else {
                        return false;
                    }
                });
            },
            // 保存処理
            requestAllowanceSave(){
                let data = {
                    companyCode:   this.userData.companyCode,
                    applyYm:       this.form.yearMonth,
                    requestNumber: this.reqNum,
                    requestUserId: this.userData.userId,
                    requestTypeKbn: '042',
                    projectId: this.form.projectId,
                    projectName: this.form.projectName,
                    allowance:     this.form.allowanceFee,
                    quantity:      this.form.numPerson,
                    total:         this.totalAmount(),
                }
                // 手当申請：リーダー手当の保存処理
                insertAllowance(data).then(res => {
                    Message({
                        message: '手当申請（リーダー）を保存しました。',
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
                    this.userData.userId = this.rowdata.userId
                }
                this.$refs['form'].validate(valid => {
                    if(valid){
                        this.requestSaveData()
                        this.$refs.approvalDialog.applyDialogOpen(flg)
                    } else {
                        return false;
                    }
                    });
            },
            //申請テーブルに格納するデータ（保存、申請）
            requestSaveData(){
                this.requestData = {
                    companyCode: this.userData.companyCode,
                    requestNumber: this.reqNum,
                    requestUserId :this.userData.userId,
                    requestDate: this.yMd,
                    requestTypeKbn: '042',
                    requestOverview: '手当申請（リーダー）' + this.form.yearMonth.substring(0,4) + '年' + this.form.yearMonth.substring(4,6) + '月分',
                    requestStatusKbn: '001'
                }
            },
            // 申請処理
            requestSave(data){
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
                // // 申請状態＝保存
                if(this.rowdata.requestStatusKbn == '001'){
                    this.deleteReq()
                    this.deleteRequestH()
                // // 申請状態＝差戻
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
            // 申請テーブルのレコード削除
            deleteReq(){
                let datadeletereq = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                deleteRequest(datadeletereq).then(res => {
                }).catch(error => {
                    console.log(error)
                })
            },
            //  手当申請テーブルのレコード削除
            deleteRequestH(){
                let data = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                deleteRequestAllowance(data).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
            },
            // ワークフローテーブルのレコード削除
            deleteAllW(){
                let data = {
                    companyCode: this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber,
                }
                deleteAllWf(data).then(res =>{
                }).catch(error => {
                    console.log(error)
                })
            },
            //  取戻画面のポップアップ
            OpenGetBackDialog(){
                this.$refs.ongetBackDoalog.applyopende()
            },
            //  差戻画面のポップアップ
            remandDialog(){
                this.$refs.onRemandDialog.remandDialogOpen()
            },
            //  戻るボタン（申請一覧）
            closeA(){
                this.$router.go({path: this.$router.currentRoute.path, force: true})
            },
            //  戻るボタン（承認一覧）
            closeB(){
                this.$router.go(-1);
            },
        }

    }
</script>

<style lang="scss" scope>
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

// 参照ボタン
.reference-leader-button {
    font-weight: bold;
    font-size: 14px;
    width: 80px;
    margin-left: 20px;
}
.leader-blank-month {

}
// インプットボックス
.input-box {
    width: 300px;
}
.text-box {
    width: 300px;
}
</style>