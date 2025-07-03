<template>
    <div class="apply-area">
        <applyHeader title="資格手当申請" v-bind:reqNumber="reqNumber"></applyHeader>
        <!-- 空白 -->
        <el-form style="margin: 50px">
        </el-form>
        <!-- テーブル -->
        <el-form ref="form" :rules="rules" :model="form" label-width="120px">
            <!-- 受検日 -->
            <el-form-item prop="qualificationExamDate" label="受検日" required>                                                              
                <el-date-picker
                    v-model="form.qualificationExamDate"
                    type="date"
                    placeholder=""
                    format="yyyy年MM月dd日"
                    value-format="yyyyMMdd"
                    :disabled="inputOff">
                </el-date-picker>                                                            
            </el-form-item>  
            <!-- 資格選択 -->
            <el-form-item prop="qualificationId" label="資格選択 " required>
                <el-select :disabled="inputOff" v-model="form.qualificationId" @change="qualificationChange(form.qualificationId)" placeholder="資格選択区分" clearable>
                    <el-option
                        v-for="item in Category"
                        :key="item.categoryKbn"
                        :label="item.categoryKbnName"
                        :value="item.categoryKbn">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="allowance" label="手当金額 " >
                <span>：{{ this.form.allowance }} 円</span>
            </el-form-item>
            <!-- 添付 -->
            <el-form-item label="添付" class="image-label" prop="image" required>
                <div class='image-upload'>
                    <el-upload
                        :class="{disabled:uploadDisabled}"
                        class='appended-uploader'
                        action='#'
                        list-type='picture-card'
                        :file-list="fileList"
                        :auto-upload='false'
                        :on-change='imageLoading'
                        :limit='1'
                        :disabled="inputOff">
                        <i slot='default' class='el-icon-plus appended-uploader-icon'></i>
                        <div slot="file" slot-scope="{file}">
                            <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                            <span class="el-upload-list__item-actions image-upload">
                                <span
                                    class="el-upload-list__item-preview"
                                    @click="handlePictureCardPreview(file)">
                                    <i class="el-icon-zoom-in"></i>
                                </span>
                                <span
                                    v-if="!inputOff"
                                    class="el-upload-list__item-delete"
                                    @click="removeFunction(file)">
                                    <i class="el-icon-delete"></i>
                                </span>
                            </span>
                        </div>
                    </el-upload>
                </div>
            </el-form-item>
            <el-dialog :visible.sync="picDialogVisible">
                <img width="100%" :src="this.pic64Data" alt="Bear">
            </el-dialog>
        </el-form>
        <div class="allowanceQualification-btn">
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
    import { insertQualificationAllowance, getRequestAllowance, deleteRequestAllowance ,qualificationAllowanceCheck} from '@/api/requestAllowance';
    import { Message } from 'element-ui';
    import { getCategory } from '@/api/category'


    export default {
        props: ['userData'],
        name: "AllowanceQualification",
        components: {
            applyHeader,
            approval,
            remand,
            getBackDoalog,
        },
        // 初期画面
        created() {

            let data = {
                companyCode: getUserInfo().companyCode,
                categoryClassNumber: '054'
            }
            getCategory(data).then(res => {
                this.Category = res.data
            }).catch(error => {
            })

            this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            // 会社コード
            this.companyCode = getUserInfo().companyCode
            // 今日の日付取得
            this.getCurrentTime()
            // 年月の初期処理
            let nowDate = new Date();
            let month = nowDate.getMonth() + 1 + ""
            this.form.applyYm = nowDate.getFullYear() + "" + (month.length == 1 ? ("0"+month) : month)
            // 申請番号
            this.setReqNum()
            // 各ボタンの表示,非表示
            this.statusSetBtn()

            // 詳細の処理
            if(this.changebyFlg == "2" || this.changebyFlg == "3"){
                this.setAllowaneceQualification()
            }
        },
        data() {
            return {
                status:'200',
                Category: {index: '',categoryKbnName: '' ,categoryKbn: '' },
                fileList:[],
                image:'',
                picDialogVisible:false,
                contentType:'',
                disabled: false,
                form:{
                    contentType:'',
                    image:'',
                    applyYm:'',
                    qualificationExamDate:'',
                    qualificationId:'',
                    allowance:'0'
                },
                // エラーメッセージが表示
                rules:{
                    qualificationExamDate: [ { required: true, message: '受験日は入力必須です。', trigger: ['blur','change']}],
                    qualificationId: [ { required: true, message: '資格選択は入力必須です。', trigger: ['blur','change'] }],
                    image: [ { required: true, message: '添付は必須です。'}],
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
                pic64Data:''
            }
        },
        computed: {
            uploadDisabled:function(){
                return this.fileList.length > 0
            },
        },
        methods: {
            qualificationChange(categoryKbn){
               for(let i = 0; i < this.Category.length; i++){
                    if(this.Category[i].categoryKbn === categoryKbn){
                       this.form.allowance = this.Category[i].categoryKbnOption1
                    }
               }
            },
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
            
            // 各ボタンの表示,非表示
            statusSetBtn(){
                // 申請（手当申請）画面
                if(this.changebyFlg == "1"){
                    this.delFlg = false
                    this.getBack = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                    this.inputOff = false
                // 申請一覧画面
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
                    this.inputOff = true
                }
            },

            // 詳細ボタン押下時の処理（資格手当）
            setAllowaneceQualification(){
                const that = this
                let data = {
                    companyCode:   this.rowdata.companyCode,
                    requestNumber: this.rowdata.requestNumber
                }
                // 手当申請：詳細処理
                getRequestAllowance(data).then(res => {
                    that.detailList = res.data[0]   
                    this.form = res.data[0]   
                    // 申請番号
                    this.reqNumber = this.form.requestNumber
                    this.fileList = []                                               
                    if (this.form.image != null && this.form.image != '') {
                        this.pic64Data=this.form.image
                        this.fileList.push({ name:'picture', url: this.form.image })
                    } 
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
                this.$refs['form'].validate(async valid => {
                    if(valid){
                        // ステータス区分：保存
                        await this.requestAllowanceSave()
                        if(this.status === '200'){
                            this.requestSaveData()
                            this.requestSave(this.requestData)
                            setTimeout(()=>{
                                this.$router.go({path: this.$router.currentRoute.path, force: true})
                            },200)
                        }
                    }
                })
            },
            // 保存処理
            async requestAllowanceSave(){
                let data = {
                    companyCode:           this.userData.companyCode,
                    applyYm:               this.form.applyYm,
                    requestNumber:         this.reqNum,
                    requestUserId:         this.userData.userId,
                    qualificationExamDate: this.form.qualificationExamDate,
                    qualificationId:       this.form.qualificationId,
                    image:                 this.form.image,
                    contentType:           this.form.contentType,
                    allowance:             this.form.allowance,
                    total:                 this.form.allowance
                }
                // 手当申請：資格手当の保存処理
                await insertQualificationAllowance(data).then(res => {
                    if(res.status !== 400){
                        Message({
                            message: '手当申請（資格）を保存しました。',
                            type: 'success',
                            duration: 4 * 1000
                        })
                        this.status = '200';
                    }else{
                        Message({
                            message: res.msg,
                            type: 'error',
                            duration: 4 * 1000
                        })
                        this.status = '400';
                    }
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
                        let data = {
                            companyCode:     this.userData.companyCode,
                            applyYm:         this.form.applyYm,
                            requestNumber:   this.reqNum,
                            requestUserId:   this.userData.userId,
                            qualificationId: this.form.qualificationId,
                        }
                        // 手当申請：資格手当のチェック処理
                        qualificationAllowanceCheck(data).then(res => {
                            if(res.status !== 400 || flg == 1){
                                this.requestSaveData()
                                this.$refs.approvalDialog.applyDialogOpen(flg)
                            }else{
                                Message({
                                    message: res.msg,
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                })
            },
            // 申請テーブルに格納するデータ（保存、申請）
            requestSaveData(){
                this.requestData = {
                    companyCode:      this.userData.companyCode,
                    requestNumber:    this.reqNum,
                    requestUserId :   this.userData.userId,
                    requestDate:      this.yMd,
                    requestTypeKbn:   '041',
                    requestOverview:  '手当申請（資格）',
                    requestStatusKbn: '001'
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
            // 手当申請テーブルのレコード削除
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
            // 画像アップロード
            //TransportationExpensesの記述参考
            async imageLoading(file,fileList){
                const fileSizeLimit = await file.size / 1024 / 1024 < 3;
                const imageJudge = file.raw.type.substring(0,5) == 'image'
                if(fileSizeLimit && imageJudge){
                let pictureRaw = await window.URL.createObjectURL(file.raw)
                await this.changeImage(pictureRaw)
                this.fileList = fileList
                }else{
                    this.fileList = []
                }
                this.$refs['form'].validateField('image');

            },
            async changeImage(data){
                const response = await fetch(data)
                let contentTypeArr = response.headers.get('content-type').split(";")
                let contentType = contentTypeArr[0]
                const arrayBuffer = await response.arrayBuffer()
                const APPLY_MAX = 1024
                let encodedStr = ''
                for(var i = 0; i < arrayBuffer.byteLength; i += APPLY_MAX){
                    encodedStr += String.fromCharCode.apply(
                        null,
                        new Uint8Array(arrayBuffer.slice(i, i + APPLY_MAX))
                    )
                }
                this.form.image = 'base:' + btoa(encodedStr)
                this.pic64Data = 'data:'+ contentType + ';base64,' + btoa(encodedStr)
                this.form.contentType = contentType.slice(6)
            },
            handlePictureCardPreview(file){
                this.picDialogVisible = true;
                this.image= file.url
            },
            async removeFunction(file){
                this.form.image = '' 
                this.form.contentType = ''  
                this.pic64Data = ''
                this.fileList = []
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

// 添付画像ボックスサイズ（共通）
.image-upload {
    width:150px;
    height:150px;
}
.disabled .el-upload--picture-card {
    display: none;
}

// ボタンの修正（一部）
.allowanceQualification-btn {
    padding: 20px 120px 0 120px;
}
</style>