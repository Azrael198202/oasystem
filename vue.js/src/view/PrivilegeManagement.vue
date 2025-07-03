<template>
    <div>
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="bulkexport-area-subHolidayInput">
                    <div class="bulkexport-header">
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <span class="card-title-big">権限管理画面</span>
                            </el-col>
                        </el-row>
                    </div>
                    <!-- 上 -->
                    <el-row style="margin-top: 20px">
                        <div>
                            <el-col :span="8" style="margin-left: 324px" class="flex-start">
                                <span class="select-title-subHolidayInput">社員番号or社員名:　</span>
                                <el-form ref="formSearch" :model="formSearch"   @submit.native.prevent>
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
                            <el-col :span="2" class="search-position" width="100px">
                                <el-button type="primary" icon="el-icon-search" @click="search()">検索</el-button>
                            </el-col>
                        </div>
                    </el-row>
                    <!-- 空白 -->
                    <el-form style="margin: 50px">
                    </el-form>
                    <!-- テーブル -->
                    <el-form  ref="form" :rules="rules" :model="form" style="margin-left: 370px" label-width="120px">
                        <!-- 社員名 -->
                        <el-form-item prop="userName" style="width: 380px" label="社員名" >
                            <el-input
                                v-model="form.userName"
                                placeholder="社員名"
                                :disabled="true">
                            </el-input>
                        </el-form-item>
                        <!-- 空白 -->
                        <el-form-item style="margin: 10px" >
                        </el-form-item>
                        <!-- 権限選択 -->
                        <el-form-item prop="authority" label="権限選択">
                            <el-select  v-model="form.authority" style="width: 260px"   placeholder="権限選択" clearable>
                                <el-option
                                    v-for="item in ApplyKbnList"
                                    :key="item.categoryKbn"
                                    :label="item.categoryKbnName"
                                    :value="item.categoryKbn">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <!-- 空白 -->
                        <el-form-item style="margin: 20px" >
                        </el-form-item>
                    </el-form>
                    <!-- 下 -->
                    <el-row style="width: 1200px">
                        <!-- 戻るボタン -->
                        <el-col :span="16" class='flex-start' width="200px">
                            <div class="back-btn" style="margin-top:80px" @click="backToToppage()">戻る</div>
                        </el-col>
                        <!-- 更新ボタン -->
                        <el-col :span="5" class='flex-start' width="200px">
                            <div class="red-btn" style="margin-top:80px" @click="create()">更新</div>
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
    import { research  ,authorityCreate } from '@/api/user';
    export default {
        name: "privilegeManagement",
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
            // 権限区分取得
            let ApplyKbnListData = {
                companyCode: this.companyCode,
                categoryClassNumber: '004'
            }
            // 区分マスタ
            getCategory(ApplyKbnListData).then(res => {
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
                    // 権限選択
                    authority:'',
                },
                // エラーメッセージが表示
                rules:{
                    authority:[{ required: true, message: '権限選択は入力必須です。', trigger: ['change','blur'] }],
                },
                // 権限選択
                ApplyKbnList:{index: '',categoryKbnName: '' },
            }
        },
        methods:{
            // 検索押下時の処理
            search(){
                let data = {
                    companyCode: this.companyCode,
                    userId:      this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
                }
                // 社員番号or社員名で検索（グループメンバー取得）
                research(data).then(res =>{
                    // 社員情報が１　の場合　⇒　実行する
                    if(res.data.length == 1){
                        this.form.userName = res.data[0].userName
                        this.form.userId = res.data[0].userId
                        this.form.authority = ''
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
                this.form.authority = ''
            },
            // 更新ボタン押下時の処理
            create(){
                this.$refs['form'].validate(valid => {
                    if(valid){
                        // 1）社員名が空　の場合　⇒　エラーメッセージが表示
                        if(this.form.userName == ''){
                            Message({
                                message: '検索を行ってください。',
                                type: 'error',
                                duration: 3000
                            })
                            return
                        }
                        // 利用者マスタ（基本情報）更新
                        let saveData = {
                            companyCode:      this.companyCode,
                            userId:           this.form.userId,
                            permissionKbn:    this.form.authority,
                            updAccount:       this.getRoguin.userId,  
                        }
                        // 利用者マスタ（基本情報）テーブルの更新処理＆メッセージが表示
                        authorityCreate(saveData).then(res =>{
                            Message({
                                message: '利用者マスタの情報を更新しました。',
                                type: 'success',
                                duration: 4000   
                            })
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                })
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
    .search-position{
        margin-left: 62px;
    }
</style>
