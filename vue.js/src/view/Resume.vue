<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="noticeMaintenance-area">
                    <div class="noticeMaintenance-header">
                        <el-row>
                            <el-col :span="10">
                                <span class="card-title-big">履歴経歴書一覧</span>
                            </el-col>
                        </el-row>
                        <!-- 履歴経歴書一覧（上部） -->
                        <el-row style="margin-top: 40px">

                            <!-- テーブル -->
                            <el-col style="margin-top:20px"></el-col>
                            <el-table
                                ref="singleTable"
                                :data="resumeList"
                                :header-cell-style="{textAlign: 'center',color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                                highlight-current-row
                                :row-style="{height: '62px'}"
                                style="width: 100%"
                                @row-click="handleClick"
                                height="550px"
                                border>
                                <el-table-column
                                    prop="userId"
                                    label="社員番号"
                                    width="200"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="userName"
                                    label="社員名"
                                    width="200"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="resumeVerKbn"
                                    label="Ver"
                                    width="200"
                                    sortable
                                    align="center">
                                    
                                </el-table-column>
                                <el-table-column
                                    prop="updTime"
                                    label="更新日時"
                                    width="220"
                                    sortable
                                    align="center">
                                </el-table-column>
                                <el-table-column
                                    prop="remarks"
                                    label="備考欄"
                                    sortable
                                    align="center">
                                </el-table-column>
                            </el-table>
                        </el-row>
                        <!-- 下部ボタン -->
                        <el-row class="btn-area">
                            <el-col :span="12" class="flex-start" style="margin-top: 20px">
                                <div class="back-btn" @click="closeA()" >戻る</div>
                            </el-col>
                            <el-col :span="12" class="flex-end" style="margin-top: 20px">
                                <div class="detail-btn" @click="detail()" v-if="(this.currentResumeVerKbn != '')">詳細</div>
                                <div class="noticeAdd-btn" @click="editBtn()" v-if="(this.currentResumeVerKbn != '')">編集</div>
                                <div class="new-btn" @click="addBtn()" >新規</div>
                                <div class="pdf-btn" @click="exportPDF()" v-loading='exportPdfLoading' v-if="(this.currentResumeVerKbn != '')">PDF</div>
                                <div class="delete-btn" @click="deleteRow()" v-if="(this.currentResumeVerKbn != '')">削除</div>
                            </el-col>
                        </el-row>
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
    import { searchResume, deleteResume, exportPDF  } from "@/api/resume.js";
    import { Avatar, DatePicker } from "element-ui";

    export default {
        name: "Resume",
        components: {
            commonHeader,
            commonFootter
        },
        
        data() {
            return {
                resumeList: [],
                currentResumeVerKbn: '',
                currentUserId:'',
                currentUserName:'',
                resumeVerKbn: '',
                resumeVerName: '',
                updTime: '',
                remark:'',
                // Pdfボタン　ダウンロード表現　表示/非表示
                exportPdfLoading: false,
                companyCode: getUserInfo().companyCode,
                userId:  ''
            }
        },

        computed: {
        },
        created(){
            if (getUserInfo().permissionKbn != 100){
                this.userId= this.$route.query.userId
                if (this.$route.query.userName !== undefined) {
                    this.userName = this.$route.query.userName   
                }
            } else {
                this.userId= getUserInfo().userId
                
            }
            this.searchResumeList()

        },
        methods: {
            //データが選択されたとき
            handleClick(val) {
                this.currentResumeVerKbn = val.resumeVerKbn
                this.currentUserId = val.userId
                this.currentUserName = val.userName
            },
            searchResumeList(){
                const that = this
                let data = {
                    companyCode: this.companyCode,
                    userId:  this.userId
                }
                searchResume(data).then(res => {
                    console.log(res)
                    that.resumeList = []
                    that.resumeList = res.data
                }).catch(error => {
                    console.log(error)
                })
            },
            //　新規ボタン押下
            addBtn(){
                // 新規画面open
                this.$router.push({
                    path: '/resumeDetail',
                    // query: { userId: this.userId,userName: this.userName,mode: 1 },
                    query: { userId: this.userId, userName: this.userName, mode: 1},
                })  
            },

            //　編集ボタン押下
            editBtn(){
                
                // 編集画面open
                this.$router.push({
                    path: '/resumeDetail',
                    query: { resumeVerKbn: this.currentResumeVerKbn, userId: this.currentUserId,userName: this.currentUserName, mode: 2 },
                })  
            },

            //　詳細ボタン押下
            detail(){
                // 編集画面open
                this.$router.push({
                    path: '/resumeDetail',
                    query: { resumeVerKbn: this.currentResumeVerKbn, userId: this.currentUserId,userName: this.currentUserName, mode: 3 },
                })  
            },
            deleteRow(){
                this.$confirm('履歴経歴書を削除しますか？', '注意', {
                    confirmButtonText: '削除',
                    cancelButtonText: 'キャンセル',
                    type: 'warning'
                }).then(() => {
                    let data = {
                        companyCode: getUserInfo().companyCode,
                        userId:  this.currentUserId,
                        resumeVerKbn: this.currentResumeVerKbn
                    }
                    deleteResume(data).then(res => {
                        this.currentResumeVerKbn = '';
                        this.searchResumeList()
                    }).catch(error => {
                        console.log(error)
                    })
                    this.deleteDialog = false
                    this.$message({
                        type: 'success',
                        message: '履歴経歴書を削除しました。'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '削除をキャンセルしました。'
                    });          
                });
            },

            exportPDF(){
                this.exportPdfLoading = true
                let data = {
                    companyCode: getUserInfo().companyCode,
                    resumeVerKbn: this.currentResumeVerKbn,
                    userId: this.currentUserId
                }
                // Pdf出力
                exportPDF(data).then(res => {
                    let blob = new Blob([res.data], {type: "application/pdf"})
                    const href = URL.createObjectURL(blob) 
                    const a = document.createElement('a') 
                    a.style.display = 'none'
                    a.href = href 
                    a.download = this.currentUserId + "_" + this.currentUserName + "_"+ "技術者経歴書.pdf"// 指定下载文件名（ダウンロードファイル名を指定します）
                    a.click() 
                    this.exportPdfLoading = false
                }).catch(error => {
                    this.exportPdfLoading = false
                    console.log(error)
                });
            },

            //  戻るボタン押下
            closeA() {
                if (getUserInfo().permissionKbn != 100){
                    this.$router.push({
                     path: '/ResumeMaster'                  
                    });
                } else {
                    this.$router.push({
                        path: '/toppage'                  
                    });
                }
            
            }
        }
    }
</script>

<style lang="scss" scope>
    @import '../style/index.scss';
    .noticeMaintenance-area {
        height: 100%;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 30px 30px;
        border-radius: 6px;

        .noticeMaintenance-header {
            padding: 0px 30px;
        }
    }
    .select-title {
        font-weight: bold;
        white-space: nowrap;
        font-size: 16px;
        margin-top: 8px;
    }
    // 編集ボタン
    .noticeAdd-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #4685ff;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(70, 133, 255, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .noticeAdd-btn:hover {
        opacity: 0.6;
    }
    // 新規ボタン
    .new-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 80px;
        height: 30px;
        background-color: #54c336;
        font-size: 14px;
        line-height: 30px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(84, 195, 54, 0.4);
        border-radius: 30px;
        cursor: pointer;
        color: #fff;
    }
    .new-btn:hover {
        opacity: 0.6;
    }
    // PDFボタン
    .pdf-btn {
        margin-left: 12px;
        transition: 0.5s;
        width: 76px;
        height: 26px;
        background-color: #fff;
        border: 2px #ff0000 solid;
        font-size: 14px;
        line-height: 26px;
        text-align: center;
        box-shadow: 0 4px 6px 0 rgba(250, 0, 0, 0.2);
        border-radius: 30px;
        cursor: pointer;
        color: #ff0000;
    }
    .pdf-btn:hover {
        transition: 0.5s;
        background-color: #fbd4d4;
    }
    // 削除ボタン
    .delete-btn {
        margin-right: 40px;
        margin-left: 12px;
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
    .delete-btn:hover {
        opacity: 0.6;
    }
    // 詳細ボタン
    .detail-btn {
        margin-right: 0px;
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
    .detail-btn:hover {
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
</style>