<template>
    <div>
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="bulkexport-area-subHolidayInput">
                    <div class="bulkexport-header">
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <span class="card-title-big">代休残入力画面</span>
                            </el-col>
                        </el-row>
                    </div>
                    <!-- 上 -->
                    <el-row style="margin-top: 20px">
                        <div>
                            <el-col :span="9" style="margin-left: 324px" class="flex-start">
                                <span class="select-title-subHolidayInput">社員番号or社員名:　</span>
                                <el-form ref="formSearch" :model="formSearch"  @submit.native.prevent>
                                    <el-form-item prop="searchId" style="width: 280px">
                                        <el-input
                                            ref="searchInput"
                                            v-model="formSearch.searchId"
                                            placeholder="社員番号or社員名" 
                                            @keyup.enter.native="search()"
                                            clearable>
                                        </el-input>
                                    </el-form-item>
                                </el-form>
                            </el-col>
                            <!-- 検索ボタン -->
                            <el-col :span="2" class="search-subHoliday-position"  width="100px">
                                <el-button type="primary" icon="el-icon-search" @click="search()">検索</el-button>
                            </el-col>
                        </div>
                    </el-row>
                    <!-- 空白 -->
                    <el-form style="margin: 20px">
                    </el-form>
                    <!-- テーブル -->
                    <el-form  ref="form" :rules="rules" :model="form" style="margin-left: 370px" label-width="120px">
                        <!-- 社員名 -->
                        <el-form-item prop="userName" style="width: 380px" label="社員名">
                            <el-input
                                v-model="form.userName"
                                placeholder="社員名"
                                :disabled="true">
                            </el-input>
                        </el-form-item>
                        <!-- 休日出勤予定日 -->
                        <el-form-item prop="holidayWorkDay" label="休日出勤予定日">
                            <el-date-picker
                                v-model="form.holidayWorkDay"
                                type="date"
                                placeholder="YYYY/MM/DD"
                                format="yyyy/MM/dd"
                                value-format="yyyyMMdd"
                                style="width: 260px">
                            </el-date-picker>
                        </el-form-item>
                        <!-- 代休日数 -->
                        <el-form-item prop="subHolidayDays" label="代休日数">
                            <el-select  v-model="form.subHolidayDays" style="width: 260px" clearable  placeholder="代休日数">
                                <el-option
                                    v-for="item in ApplyKbnList"
                                    :key="item.categoryKbn"
                                    :label="item.categoryKbnName"
                                    :value="item.categoryKbn">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <!-- 下 -->
                    <el-row style="width: 1200px">
                        <!-- 戻るボタン -->
                        <el-col :span="16" class='flex-start' width="200px">
                            <div class="back-btn" style="margin-top:80px" @click="backToToppage()">戻る</div>
                        </el-col>
                        <!-- 作成ボタン -->
                        <el-col :span="5" class='flex-start' width="200px">
                            <div class="green-btn" style="margin-top:80px" @click="create()">作成</div>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
    </div>
</template>
<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";
    import { Message } from 'element-ui';
    import { getUserInfo } from '@/utils/auth';
    import { getCategory } from '@/api/category';
    import { requestNumberSubHoliday } from '@/api/workDayOff';
    import { usersearch ,checkSubHoliday ,subHolidayCreate } from '@/api/subHoliday';

    export default {
        name: "subHolidayInput",
        components: {
            commonHeader,
            commonFootter
        },
        // 初期画面
        created(){
            // 会社コード
            this.companyCode = getUserInfo().companyCode
            // ログイン者取得
            this.getRoguin = getUserInfo()
            // 全日,午後,午前
            let WorkTimeData = {
                companyCode: this.companyCode,
                categoryClassNumber: '048'
            }
            // 区分マスタ
            getCategory(WorkTimeData).then(res => {
                this.ApplyKbnList = res.data
            }).catch(error => {
                console.log(error)
            })
        },
        data(){
            return{
                // 社員番号＆社員名
                formSearch:{
                    searchId: ''
                },
                form:{
                    // 社員番号
                    userId:'',
                    // 社員名
                    userName:'',
                    // 休日出勤日
                    holidayWorkDay:'',
                    // 代休日数
                    subHolidayDays:'',
                },
                // エラーメッセージが表示
                rules:{
                    holidayWorkDay:[{ required: true, message: '休日出勤予定日は入力必須です。', trigger: ['blur','change'] }],
                    subHolidayDays:[{ required: true, message: '代休日数は入力必須です。', trigger: ['change','blur'] }],
                },
                // 代休日数選択
                ApplyKbnList:{index: '',categoryKbnName: '' },
                // 日付
                aTime: '',
                ymd: '',
            }
        },
        methods:{
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
            },
            // 検索押下時の処理
            search(){
                let data = {
                    companyCode: this.companyCode,
                    userId:      this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
                }
                // 社員番号or社員名で検索（グループメンバー取得）
                usersearch(data).then(res =>{
                    // 社員情報が１　の場合　⇒　実行する
                    if(res.data.length == 1){
                        this.form.userName = res.data[0].userName
                        this.form.userId = res.data[0].userId
                        this.form.holidayWorkDay = ''
                        this.form.subHolidayDays = ''
                    // 社員情報が１以上　の場合　⇒　エラーメッセージが表示    
                    }else if(res.data.length > 1){
                        Message({
                            message: '複数社員の情報が存在します。再検索を行ってください。',
                            type: 'error',
                            duration: 4000
                        })
                        // 情報をクリアする
                        this.clear();
                    // 社員情報がない　場合　⇒　エラーメッセージが表示, 情報をクリア   
                    }else{
                        Message({
                            message: '対象社員の情報が存在しません。',
                            type: 'warning',
                            duration: 3000
                        })
                        this.clear();
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            // 情報をクリアする
            clear(){
                this.form.userName = ''
                this.form.holidayWorkDay = ''
                this.form.subHolidayDays = ''
            },
            // 作成ボタン押下時の処理
            create(){                
                this.$refs['form'].validate(valid => {
                    if(valid){
                        // 申請番号
                        let reqNum = ''
                        // 休日出勤申請内容テーブル用（新規：1　更新：2）
                        let createOrUpdateKbn = ''
                        // 1）社員名が空　の場合　⇒　エラーメッセージが表示
                        if(this.form.userName == ''){
                            Message({
                                message: '検索を行ってください。',
                                type: 'error',
                                duration: 3000
                            })
                            return
                        }
                        // 2）既存の代休残データがあるかどうかのチェック
                        //　重複データの確認　代休残テーブルに指定したデータが存在した　場合　⇒　エラーメッセージが表示
                        let checkData = {
                            companyCode:    this.companyCode,
                            userId:         this.form.userId,
                            holidayWorkDay: this.form.holidayWorkDay
                        }
                        // 代休残テーブル（重複データ確認）
                        checkSubHoliday(checkData).then(res =>{
                            // データが存在(1)した　場合　⇒　エラーメッセージが表示
                            if(res.data == '1'){
                                Message({
                                    message: res.msg,   // メッセージ内容(代休残テーブルに同じ日付のデータが存在しています。)
                                    type: 'error',
                                    duration: 4000   
                                })
                                return
                            }else{
                                // 3）休日出勤の申請番号がある場合⇒それを活用, 休日出勤申請内容テーブルを更新　　ない場合⇒指定する(今日の日付), 休日出勤申請内容テーブルを作成(新規)
                                let setData = {
                                    companyCode:      this.companyCode,
                                    userId:           this.form.userId,
                                    scheduledWorkDay: this.form.holidayWorkDay
                                }
                                // 休日出勤テーブル申請番号取得　　休日出勤申請内容テーブル用（新規：1　更新：2）
                                requestNumberSubHoliday(setData).then(res =>{
                                    this.workDayOffInfo = res.data
                                    // 申請番号がある　場合　⇒　申請番号取得, 休日出勤申請内容テーブルを更新
                                    if(this.workDayOffInfo){
                                        reqNum = this.workDayOffInfo.requestNumber
                                        createOrUpdateKbn = '2'
                                    // 申請番号がない　場合　⇒　指定する(今日の日付), 休日出勤申請内容テーブルを作成(新規)
                                    }else{
                                        // 今日の日付取得
                                        this.getCurrentTime()
                                        reqNum = this.aTime +  this.companyCode +  this.form.userId
                                        createOrUpdateKbn = '1'
                                    }
                                    // 4）代休残更新処理
                                    let saveData = {
                                        companyCode:      this.companyCode,
                                        userId:           this.form.userId,
                                        holidayWorkDay:   this.form.holidayWorkDay,
                                        subHolidayDays:   this.calcSubHoliday(this.form.subHolidayDays),      // 代休日数(1, 0.5)
                                        workTimeKbn:      this.form.subHolidayDays,                           // 休日出勤区分(全日, 午前, 午前)
                                        requestNumber:    reqNum,
                                        registrationUser: this.getRoguin.userId,
                                        createOrUpdateKbn: createOrUpdateKbn
                                    }
                                    // 代休残テーブルに登録 ＆ 休日出勤申請内容テーブル（作成, 更新）, メッセージが表示
                                    subHolidayCreate(saveData).then(res =>{
                                        Message({
                                            message: '代休残の情報を更新しました。',
                                            type: 'success',
                                            duration: 4000   
                                        })
                                    }).catch(error => {
                                        console.log(error)
                                    })
                                }).catch(error => {
                                    console.log(error)
                                })
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                })
            }, 
            // コード変更（010,020,021）⇒（1, 0.5, 0.5）
            calcSubHoliday(pKbn){
                return (pKbn == '010') ? 1 : 0.5;
            },
            // 戻るボタン押下時の処理
            backToToppage(){
                this.$router.go(-1);
            }, 
        }
    }
</script>
<style lang="scss" scope>
    @import '../style/index.scss';
    // 全体の設定
    .bulkexport-area-subHolidayInput {
        height: 450px;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 20px 30px;
        border-radius: 6px;

        .holidayList-header {
            padding: 0px 30px;
        }
    }
    // 項目の大きさ,位置の設定
    .select-title-subHolidayInput {
        font-weight: bold;
        white-space: nowrap;
        font-size: 18px;
        margin-top: 8px;
    }
    // 検索ボタンの位置
    .search-subHoliday-position{
        margin-left: 12px;
    }
</style>
