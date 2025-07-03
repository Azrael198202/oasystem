<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="noticeMaintenance-area">
                    <div class="noticeMaintenance-header">
                        <el-row>
                            <el-col :span="10">
                                <span class="card-title-big">お知らせ管理一覧</span>
                            </el-col>
                        </el-row>
                        <!-- お知らせ一覧（上部） -->
                        <el-row style="margin-top: 40px">
                            <!-- カテゴリー選択 -->
                            <el-col :span="6" class="flex-start">
                                <span class="select-title">カテゴリー：</span>
                                <el-select v-model="select_category" clearable placeholder="" style="width:175px">
                                    <el-option
                                        v-for="item in noticeCategory"
                                        :key="item.categoryKbn"
                                        :label="item.categoryKbnName"
                                        :value="item.categoryKbn">
                                    </el-option>
                                    <el-option label="全て" value=""></el-option>
                                </el-select>
                            </el-col>
                            <!-- 状態選択 -->
                            <el-col :span="4" class="flex-start">
                                <span class="select-title">状態：</span>
                                <el-select v-model="select_status" clearable placeholder="Select" style="width:105px">
                                    <el-option
                                        v-for="item in noticeStatus"
                                        :key="item.value"
                                        :label="item.noticeStatusName"
                                        :value="item.value">
                                    </el-option>
                                    <el-option label="全て" value=""></el-option>
                                </el-select>
                            </el-col>
                            <!-- 社員名入力項目 -->
                            <el-col :span="5" class="flex-end">
                                <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                                    <el-form-item prop="search_id" class="fix_margin_bottom">
                                        <el-input
                                            ref="searchInput"
                                            v-model="formSearch.search_id"
                                            placeholder="社員番号or社員名" 
                                            @keyup.enter.native="search()"
                                            clearable>
                                        </el-input>
                                    </el-form-item>
                                </el-form>
                            </el-col>
                            <!-- 検索ボタン -->
                            <el-col :span="9" class="flex-end" style="margin-top:5px">
                                <div class="green-btn"  @click="search()">検索</div>
                            </el-col>
                            <!-- テーブル -->
                            <el-col style="margin-top:20px"></el-col>
                            <el-table
                                ref="singleTable"
                                :data="noticeAllList"
                                :header-cell-style="{textAlign: 'center',color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                                highlight-current-row
                                :row-style="{height: '62px'}"
                                style="width: 100%"
                                @row-click="handleClick"
                                height="550px"
                                border>
                                <el-table-column
                                    prop="noticeNumber"
                                    label="お知らせ番号"
                                    width="130"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="noticeCategory"
                                    label="カテゴリー"
                                    width="150"
                                    sortable
                                    align="center">
                                    <template slot-scope="scope">
                                        <el-tag v-if="scope.row.noticeCategory == '001'" effect="plain">{{ scope.row.noticeCategoryName = "アップデート情報"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeCategory == '002'" effect="plain" type="success">{{ scope.row.noticeCategoryName = "メンテナンス情報"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeCategory == '003'" effect="plain" type="danger">{{ scope.row.noticeCategoryName = "不具合"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeCategory == '004'" effect="plain" type="warning">{{ scope.row.noticeCategoryName = "修正"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeCategory == '005'" effect="plain" type="info">{{ scope.row.noticeCategoryName = "連絡事項"}}</el-tag>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="noticePublicationStartDate"
                                    label="掲載開始"
                                    width="120"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="noticePublicationEndDate"
                                    label="掲載終了"
                                    width="120"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="noticeTitle"
                                    label="タイトル"
                                    width="300"
                                    align="left">
                                        <template slot-scope="scope">
                                            <span class="title-over">{{ scope.row.noticeTitle}}</span>
                                        </template>
                                </el-table-column>
                                <el-table-column
                                    prop="noticeStatus"
                                    label="状態"
                                    width="80"
                                    sortable
                                    align="center">
                                    <template slot-scope="scope">
                                        <el-tag v-if="scope.row.noticeStatus == '01'" type="info">{{ scope.row.noticeStatusName = "未掲載"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeStatus == '02'" type="">{{ scope.row.noticeStatusName = "掲載中"}}</el-tag>
                                        <el-tag v-if="scope.row.noticeStatus == '03'" type="danger">{{ scope.row.noticeStatusName = "終了"}}</el-tag>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="registrationUserName"
                                    label="登録者"
                                    width="120"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="updateDatetime"
                                    label="更新日時"
                                    
                                    sortable
                                    align="center">
                                </el-table-column>
                            </el-table>
                        </el-row>
                        <!-- 下部ボタン -->
                        <el-row class="btn-area">
                            <el-col :span="12" class="flex-start" style="margin-top: 20px">
                                <div class="back-btn" @click="closeA()" >戻る</div>
                                <div class="detail-btn" @click="detail()" v-if="(this.currentNoticeNumber != '')">詳細</div>
                            </el-col>
                            <el-col :span="12" class="flex-end" style="margin-top: 20px">
                                <div class="delete-btn" @click="deleteRow()" v-if="(this.currentNoticeNumber != '')">削除</div>
                                <div class="edit-btn" @click="edit()" v-if="(this.currentNoticeNumber != '')">編集</div>
                                <div class="noticeAdd-btn" @click="addBtn()" >追加</div>
                            </el-col>
                        </el-row>
                        <!-- 追加・編集ダイアログ -->
                        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" label-hight="500px" class="demo-ruleForm">
                            <el-dialog
                                title="お知らせ入力"
                                :visible.sync="noticeCreateDialog"
                                width="42%"
                                center>
                                <!-- 追加/編集によってモードを切り替える、処理参考メモ　　<span>{{this.pageOpenMode == '1' ? '追加の' : '編集の'}}入力画面だよ</span> -->
                                
                                <!-- カテゴリー選択 (入力画面) -->
                                <div>
                                    <el-form-item label="カテゴリー :" prop="dialogCategory" required>
                                        <el-select v-model="ruleForm.dialogCategory" placeholder="">
                                            <el-option
                                                v-for="item in noticeCategory"
                                                :key="item.categoryKbn"
                                                :label="item.categoryKbnName"
                                                :value="item.categoryKbn">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </div>                               
                                <!-- 掲載期間 -->
                                <div style="display:inline-flex">   
                                    <el-form-item label="掲載期間 :" required>
                                        <el-col :span="12">
                                            <el-form-item prop="dialogDate">
                                                <el-date-picker
                                                    v-model="ruleForm.dialogDate"
                                                    type="daterange"
                                                    range-separator="～"
                                                    start-placeholder="掲載開始"
                                                    end-placeholder="掲載終了"
                                                    value-format="yyyy-MM-dd"
                                                    format="yyyy/MM/dd">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                    </el-form-item>
                                </div>
                                <!-- タイトル＆新規期間 -->
                                <div style="display:inline-flex">
                                    <!-- タイトル入力 (入力画面) -->
                                        <el-form-item label="タイトル :" prop="dialogTitle" >
                                             <div class="input-title">
                                                <el-input v-model="ruleForm.dialogTitle"></el-input>
                                            </div>
                                        </el-form-item>
                                    <!-- 新規期間 (入力画面) -->
                                    <el-form-item label="新規期間 :" prop="dialogNewPeriod" >
                                        <div class="new-period" style="display:inline-flex">
                                            <el-input v-model="ruleForm.dialogNewPeriod"></el-input>
                                            <span>日</span>
                                        </div>
                                    </el-form-item>
                                </div>
                                <!-- 内容 -->
                                <el-form-item style="margin-top:10px" label="内容 :" prop="dialogContents">
                                    <div class="input-contents">
                                        <el-input 
                                            type="textarea"
                                            :rows="7"
                                            v-model="ruleForm.dialogContents">
                                        </el-input>
                                    </div>
                                </el-form-item>
                                <!-- 添付画像 -->
                                <div style="display:inline-flex">
                                    <el-form-item label="添付1" class="image-label">
                                        <div>
                                            <el-upload
                                                :class="{disabled:uploadDisabled}"
                                                class='appended-uploader'
                                                action= '#'
                                                list-type='picture-card'
                                                :file-list="fileList"
                                                :auto-upload='false'
                                                :on-change='imageLoading'
                                                :limit='3'>
                                                <i slot='default' class='el-icon-plus appended-uploader-icon' ></i>
                                                <div slot="file" slot-scope="{file}">
                                                    <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                                                    <span class="el-upload-list__item-actions image-upload">
                                                        <span
                                                            class="el-upload-list__item-preview"
                                                            @click="handlePictureCardPreview(file)">
                                                            <i class="el-icon-zoom-in"></i>
                                                        </span>
                                                        <span
                                                            v-if="!disabled"
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
                                      <img width="100%" :src="image" alt="Bear">
                                    </el-dialog>
                                </div>
                                <!-- 入力画面下部ボタン -->
                                <div class="flex-end">    
                                    <el-form-item>
                                        <el-button @click="resetForm('ruleForm')">キャンセル</el-button>
                                        <el-button type="primary" @click="noticeConfirm()">確定</el-button>
                                    </el-form-item>
                                </div>
                            </el-dialog>
                        </el-form>
                    </div>
                </div>
            </div>
        </div> 
        <commonFootter></commonFootter>
    </div>
</template>

<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";
    import { getUserInfo } from '@/utils/auth';
    import { Message } from 'element-ui';
    import { searchNotice, deleteNotice,getNoticeById,updateNotice } from "@/api/notice.js";
    import { insertNotice } from "../api/notice";
    import { getCategory } from '@/api/category'

    export default {
        name: "NoticeMaintenance",
        components: {
            commonHeader,
            commonFootter
        },
        
        data() {
            var checkNumber = (rule, value, callback) => {
                if(!value){
                    return callback(new Error('入力必須項目です。'));
                }else if(value > 999){
                    return callback(new Error('1～3桁までの数字で入力してください。'));
                }else{
                    callback();
                }
            };
            return {
                // 状態記述
                noticeStatus: [{
                    value: '01',
                    noticeStatusName: '未掲載'
                }, {
                    value: '02',
                    noticeStatusName: '掲載中'
                }, {
                    value: '03',
                    noticeStatusName: '終了'
                }],

                //ルール
                ruleForm:{
                    dialogCategory: '',
                    dialogDate: '' ,
                    dialogTitle: '',
                    dialogNewPeriod: '',
                    dialogContents: '',
                },
                rules:{
                    dialogCategory: [
                        { required: true, message: 'カテゴリーを選択してください。', trigger: 'change' }
                    ],
                    dialogDate: [
                        { required: true, message: '入力必須項目です。', trigger: ['blur','change'] }
                    ],
                    dialogTitle: [
                        { required: true, message: '入力必須項目です。', trigger: 'blur' },
                        { max: 50, message: '50字以内で入力してください。', trigger: 'blur' }
                    ],                    
                    dialogNewPeriod: [
                        { validator: checkNumber, trigger: 'blur' }
                    ],
                    dialogContents: [
                        { required: true, message: '入力必須項目です。', trigger: 'blur' }
                    ]
                },
                //初期化
                noticeCategory: {index: '',categoryKbnName: '' ,categoryKbn: '' },
                select_category: '',
                select_status:'',
                dialog_category:'',
                noticeAllList:[],
                requestData: [],
                currentNoticeNumber: '',
                currentCompanyCode: '',
                currentRow: null,
                pageOpenMode:'',
                startDateTime: '',
                endDateTime: '',
                dialog_title:'',
                inputTitle:'',
                newPeriod:'',
                inputContents:'',
                fileList: [],
                // 入力項目活性・非活性
                dialogDate: '',
                disabled: false,
                showFileList:true,
                //お知らせ入力ダイアログ非表示
                noticeCreateDialog: false,
                picDialogVisible:false,
                image : '',
                noticeAttachment1 : '',
                contentType1 : '',
                noticeAttachment2 : '',
                contentType2 : '',
                noticeAttachment3 : '',
                contentType3 : '',
                formSearch: {
                    search_id: ''
                },

            }
        },
        created() {
                let noticeKbnData = {
                    companyCode: getUserInfo().companyCode,
                    categoryClassNumber: '060'
                }
                getCategory(noticeKbnData).then(res => {
                    this.noticeCategory = res.data
                }).catch(error => {
                    this.tableLoading = false
                    console.log(error)
                })

                const that = this
                let data = {
                    companyCode: getUserInfo().companyCode,
                    noticeCategory:'',
                    noticeStatus:'',
                    userIdOrName: '',
                }
                searchNotice(data).then(res => {
                    that.noticeAllList = []
                    that.noticeAllList = res.data
                }).catch(error => {})
                this.currentNoticeNumber = ''
                this.currentCompanyCode = ''
                this.pageOpenMode = ''
        },
        computed: {
            uploadDisabled:function(){
                return this.fileList.length > 2;
            },
        },
        methods: {
            //データが選択されたとき
            handleClick(val){
                this.currentNoticeNumber = val.noticeNumber
                this.currentCompanyCode = val.companyCode
            },
            // 検索ボタン押下
            search(){
                const that = this
                let data = {
                    companyCode: getUserInfo().companyCode,
                    noticeCategory:this.select_category.replaceAll(' ', ''),
                    noticeStatus:this.select_status.replaceAll(' ', ''),
                    userIdOrName: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
                }
                searchNotice(data).then(res => {
                    that.noticeAllList = []
                    that.noticeAllList = res.data
                }).catch(error => {})
                this.currentNoticeNumber = ''
                this.currentCompanyCode = ''
                this.pageOpenMode = ''
            },
            //  戻るボタン押下
            closeA(){
                this.$router.push({
                    path: '/maintenance'                  
                });
            },
            //　詳細ボタン押下
            detail(){
                this.$router.push({
                    path: '/NewsDetailed',
                    query: { companyCode: this.currentCompanyCode, noticeNumber: this.currentNoticeNumber, mode: 3 },
                })
            },
            //　削除ボタン押下
            deleteRow(){
                this.$confirm('お知らせを削除しますか？', '注意', {
                    confirmButtonText: '削除',
                    cancelButtonText: 'キャンセル',
                    type: 'warning'
                }).then(() => {
                    let deleteNoticeRow = {
                        companyCode: this.currentCompanyCode,
                        noticeNumber: this.currentNoticeNumber,
                    };
                    deleteNotice(deleteNoticeRow).then((res) => {
                        this.search();     
                    })
                    .catch(error => {});
                    this.deleteDialog = false
                    this.$message({
                        type: 'success',
                        message: 'データを削除しました。'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '削除をキャンセルしました。'
                    });          
                });
            },
            //　編集ボタン押下
            edit(){
                this.fileList=[];
                
                // 画面表示モード：お知らせ編集モード
                this.pageOpenMode = '2'
                let data = {
                    companyCode: this.currentCompanyCode,
                    noticeNumber: this.currentNoticeNumber,
                };
                getNoticeById(data)
                .then((res) => {
                    this.ruleForm.dialogDate = [res.data.noticePublicationStartDate.replaceAll('/','-'), res.data.noticePublicationEndDate.replaceAll('/','-')];
                    this.ruleForm.dialogCategory=res.data.noticeCategory;
                    this.ruleForm.dialogContents = res.data.noticeContents;
                    this.ruleForm.dialogTitle = res.data.noticeTitle;
                    this.ruleForm.dialogNewPeriod = res.data.noticeNewPeriod;
                    this.fileList = []      
                    this.noticeAttachment1 = res.data.noticeAttachment1  
                    this.noticeAttachment2 = res.data.noticeAttachment2
                    this.noticeAttachment3 = res.data.noticeAttachment3
                    this.contentType1 = res.data.contentType1
                    this.contentType2 = res.data.contentType2
                    this.contentType3 = res.data.contentType3                                                
                    if (res.data.noticeAttachment1 != null && res.data.noticeAttachment1 != ''){
                        this.fileList.push({ name:'picture', url: res.data.noticeAttachment1 })
                    } 
                    
                    if (res.data.noticeAttachment2 != null && res.data.noticeAttachment2 != ''){
                        this.fileList.push({ name:'picture', url: res.data.noticeAttachment2 })
                    }   

                    if (res.data.noticeAttachment3 != null && res.data.noticeAttachment3 != ''){
                        this.fileList.push({ name:'picture', url: res.data.noticeAttachment3 })
                    }   
                })
                .catch(error => {});
                setTimeout(() => {
                this.noticeCreateDialog = true
                }, 1000)
                    
            },
            //　追加ボタン押下
            addBtn(){
                this.fileList = [] 
                setTimeout(() => {
                    // 画面表示モード：お知らせ追加モード
                    this.noticeCreateDialog = true
                    this.pageOpenMode = '1';
                    this.$refs['ruleForm'].resetFields();
                    this.ruleForm = {
                        dialogCategory: '',
                        dialogDate: '' ,
                        dialogTitle: '',
                        dialogNewPeriod: '',
                        dialogContents: '',
                    }
                    this.fileList = [] 
                    this.noticeAttachment1 = '' 
                    this.noticeAttachment2 = '' 
                    this.noticeAttachment3 = '' 
                    this.contentType1 = '' 
                    this.contentType2 = '' 
                    this.contentType3 = ''  
                }, 1000)
            },

            submitForm(formName){
                this.$refs[formName].validate((valid) => {
                    if(valid){
                        alert('submit!');
                    }else{
                        return false;
                    }
                });
            },
            resetForm(formName){
                this.$refs[formName].resetFields();
                this.noticeCreateDialog = false;
            },
            //（追加）ダイアログ確定ボタン
            noticeConfirm(){
                this.$refs['ruleForm'].validate((valid) => {
                    if(valid){
                        if (this.pageOpenMode == '1'){
                            this.requestData = {
                                companyCode:  getUserInfo().companyCode,
                                requestUserId:  getUserInfo().userId,
                                //noticeNumber: '',
                                noticeCategory: this.ruleForm.dialogCategory,
                                noticePublicationStartDate: this.ruleForm.dialogDate[0],
                                noticePublicationEndDate: this.ruleForm.dialogDate[1],
                                noticeTitle: this.ruleForm.dialogTitle,
                                noticeNewPeriod: this.ruleForm.dialogNewPeriod,
                                noticeContents: this.ruleForm.dialogContents,
                                noticeAttachment1: this.noticeAttachment1,
                                noticeAttachment2: this.noticeAttachment2,
                                noticeAttachment3: this.noticeAttachment3,
                                contentType1 : this.contentType1,
                                contentType2 : this.contentType2,
                                contentType3 : this.contentType3
                            }
                            insertNotice(this.requestData).then(res => {
                                Message({
                                    message: 'データを追加しました。',
                                    type: 'success',
                                    duration: 4 * 1000
                                })
                                this.noticeCreateDialog = false;
                                this.search();
                            }).catch(error => {
                                Message({
                                    message: '追加されませんでした。再度、ご入力ください。',
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                            });
                        }else{
                        this.requestData = {
                                companyCode:  getUserInfo().companyCode,
                                requestUserId:  getUserInfo().userId,
                                noticeNumber: this.currentNoticeNumber,
                                noticeCategory: this.ruleForm.dialogCategory,
                                noticePublicationStartDate: this.ruleForm.dialogDate[0],
                                noticePublicationEndDate: this.ruleForm.dialogDate[1],
                                noticeTitle: this.ruleForm.dialogTitle,
                                noticeNewPeriod: this.ruleForm.dialogNewPeriod,
                                noticeAttachment1: this.noticeAttachment1,
                                noticeAttachment2: this.noticeAttachment2,
                                noticeAttachment3: this.noticeAttachment3,
                                contentType1 : this.contentType1,
                                contentType2 : this.contentType2,
                                contentType3 : this.contentType3, 
                                noticeContents: this.ruleForm.dialogContents
                            }
                            // checkNoticeNumber()
                            updateNotice(this.requestData).then(res => {
                                Message({
                                    message: 'データを更新しました。',
                                    type: 'success',
                                    duration: 4 * 1000
                                })
                                this.noticeCreateDialog = false;
                                this.search();
                            }).catch(error => {
                                Message({
                                    message: 'データが更新されませんでした。再度、ご確認ください。',
                                    type: 'error',
                                    duration: 4 * 1000
                                })
                            });
                        }
                    }else{
                        return false;
                    }
                });
                
            },
            // 画像アップロード
            //TransportationExpensesの記述参考
            async imageLoading(file,fileList){
                const fileSizeLimit = await file.size / 1024 / 1024 < 15;
                const imageJudge = file.raw.type.substring(0,5) == 'image'
                if(fileSizeLimit && imageJudge){
                let pictureRaw = await window.URL.createObjectURL(file.raw)
                await this.changeImage(pictureRaw)
                this.fileList = fileList
                }else{
                    this.fileList = []
                }
            },
            async changeImage(data){
                const response = await fetch(data)
                const contentType = response.headers.get('content-type')
                const arrayBuffer = await response.arrayBuffer()
                const APPLY_MAX = 1024
                let encodedStr = ''
                for (var i = 0; i < arrayBuffer.byteLength; i += APPLY_MAX){
                    encodedStr += String.fromCharCode.apply(
                        null,
                        new Uint8Array(arrayBuffer.slice(i, i + APPLY_MAX))
                    )
                }
                if(this.noticeAttachment1 == null  || this.noticeAttachment1 == ''){
                    this.noticeAttachment1 = 'base:' + btoa(encodedStr)
                    this.contentType1 = contentType.slice(6,9)
                }else if(this.noticeAttachment2 == null || this.noticeAttachment2 == ''){
                    this.noticeAttachment2 = 'base:' + btoa(encodedStr)
                    this.contentType2 = contentType.slice(6,9)
                }else if(this.noticeAttachment3 == null || this.noticeAttachment3 == ''){
                    this.noticeAttachment3 = 'base:' + btoa(encodedStr)
                    this.contentType3 = contentType.slice(6,9)
                }
               
            },
            handlePictureCardPreview(file){
                this.picDialogVisible = true;
                this.image= file.url
            },
            async removeFunction(file){
                let list = [];
                this.noticeAttachment1 = '' 
                this.noticeAttachment2 = '' 
                this.noticeAttachment3 = '' 
                this.contentType1 = '' 
                this.contentType2 = '' 
                this.contentType3 = ''  
                this.fileList.forEach(item => {
                    if(item.uid != file.uid){
                        list.push(item);
                        this.changeImage(item.url)
                    }
                });
                this.fileList = list;
            },
        }
    }
</script>

<style lang="scss" scope>
    @import '../style/index.scss';
    .noticeMaintenance-area{
        height: 100%;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 30px 30px;
        border-radius: 6px;

        .noticeMaintenance-header{
            padding: 0px 30px;
        }
    }
    .select-title{
        font-weight: bold;
        white-space: nowrap;
        font-size: 16px;
        margin-top: 8px;
    }
    // 追加ボタン
    .noticeAdd-btn{
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #fd6e55;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(122, 122, 122, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .noticeAdd-btn:hover{
        opacity: 0.6;
    }
    // 編集ボタン
    .edit-btn{
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #4685ff;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(122, 122, 122, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .edit-btn:hover{
        opacity: 0.6;
    }
    // 削除ボタン
    .delete-btn{
        margin-right: 40px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #4e4c4c;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(122, 122, 122, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .delete-btn:hover{
        opacity: 0.6;
    }
    // 詳細ボタン
    .detail-btn{
        margin-right: 40px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #a19fa1;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(122, 122, 122, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .detail-btn:hover{
        opacity: 0.6;
    }
    .centerPosition{
        text-align: center;
    }
    .rightPosition{
        text-align:  right;
    }
    .new-period{
        width: 130px;
    }
    .input-title{
        width: 335px;
    }
    .input-contents{
        width: 560px;
    }
    .appended-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .el-upload--picture-card{
        font-size: 16px !important;
    }
    .el-upload-list--picture-card .el-upload-list__item-thumbnail{
        font-size: 16px;
    }
    .title-over{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .fix_margin_bottom{
        margin-bottom: -22px;
    }
    .disabled .el-upload--picture-card {
    display: none;
  }
</style>