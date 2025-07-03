<template>
    <div>
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="bulkexport-area">
                    <div class="bulkexport-header">
                        <el-row>
                            <el-col :span="10" class="flex-start">
                                <span class="card-title-big">月末締一括出力</span>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 20px">
                            <div>
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title">年月：</span>
                                    <el-date-picker
                                        style="width: 120px"
                                        v-model="workDateYm"
                                        type="month"
                                        format="yyyy/MM"
                                        value-format="yyyyMM"
                                        placeholder="年月">
                                    </el-date-picker>
                                </el-col> 
                                <el-col :span="3.5" class="flex-start">
                                    <span class="select-title">所属：</span>
                                    <el-select 
                                        v-model="departmentKbnStatus" 
                                        placeholder="所属"
                                        style="width: 120px"
                                        clearable>    
                                        <el-option
                                            v-for="item in departmentKbnList"
                                            :key="item.departmentCode"
                                            :label="item.departmentName"
                                            :value="item.departmentCode">
                                            {{item.departmentName}}                                              
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>                                
                                    </el-select>
                                </el-col>
                                <el-col :span="4.5" class="flex-start">
                                    <span class="select-title">勤怠申請/状態：</span>
                                    <el-select 
                                        v-model="selectStatus" 
                                        placeholder="勤怠申請/状態" 
                                        style="width: 100px"
                                        clearable>
                                        <el-option
                                            v-for="item in StatusKbnList"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="4.5" class="flex-start">
                                    <span class="select-title">月末精算申請/状態：</span>
                                    <el-select 
                                        v-model="transportationSelectStatus" 
                                        placeholder="月末精算申請/状態" 
                                        style="width: 100px"
                                        clearable>
                                        <el-option
                                            v-for="item in StatusKbnList"
                                            :key="item.categoryKbn"
                                            :label="item.categoryKbnName"
                                            :value="item.categoryKbn">
                                            {{item.categoryKbnName}}
                                        </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="5" class="flex-start">
                                    <span class="select-title"></span>
                                    <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                                        <el-form-item prop="search_id" style="205px">
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
                                <el-col :span="2.5" class="flex-end" width="100px">
                                    <div class="green-btn" style="margin-top:5px" @click="search()">検索</div>
                                </el-col>
                            </div>
                        </el-row>
                    </div>
                    <div>      
                        <el-table
                            ref="exoprtTable"
                            :data="bulkExport"
                            @selection-change="handleSelectionChange"
                            :default-sort = "{prop: 'date', order: 'descending'}"
                            :header-cell-style="{textAlign: 'center', color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                            :row-style="{height: '50px'}"
                            style="width: 100%"
                            height="570px"
                            border
                            >
                            <el-table-column
                                type="selection"
                                property="selection"
                                width="50"  
                                align="center"                
                            >
                            </el-table-column>
                            <el-table-column
                                prop="number"
                                label="NO."
                                type="index"
                                width="60"
                                align="center" 
                            >
                            </el-table-column>
                            <el-table-column
                                prop="departmentName"
                                label="所属"
                                width="130"
                                align="center"
                            >
                            </el-table-column>
                            <el-table-column
                                prop="userId"
                                label="社員番号"
                                width="110"
                                align="center"
                                sortable
                                :userIdtter="userIdtter"
                            >
                            </el-table-column>
                            <el-table-column
                                prop="userName"
                                label="社員名"
                                width="150"
                                align="center"
                            >
                            </el-table-column>
                            <el-table-column
                                prop="categoryKbnName"
                                label="勤怠表申請状態"
                                width="140"
                                align="center"
                            >
                                <template slot-scope="scope">
                                    <div class="flex-space-between">
                                        <div>
                                            <el-tag @click="dialogRouterOpen(scope.row.requestNumber)" type="success" v-if="scope.row.requestStatusKbn == null || scope.row.requestStatusKbn == '' || scope.row.requestStatusKbn == '001'">{{ scope.row.categoryKbnName = "未申請"}}</el-tag>
                                            <el-tag @click="dialogRouterOpen(scope.row.requestNumber)" type="primary" v-if="scope.row.requestStatusKbn == '003'">{{ scope.row.categoryKbnName}}</el-tag>
                                            <el-tag @click="dialogRouterOpen(scope.row.requestNumber)" type="danger" v-if="scope.row.requestStatusKbn == '010'">{{ scope.row.categoryKbnName}}</el-tag>
                                            <el-tag @click="dialogRouterOpen(scope.row.requestNumber)" type="info" v-if="scope.row.requestStatusKbn == '999'">{{ scope.row.categoryKbnName}}</el-tag>
                                        </div>
                                        <div style="margin-top: 2px" v-if="scope.row.updTime !== '' ">
                                            <el-button circle size="mini" @click="preViewAttendancePdf(scope.row)" ><i class="el-icon-view"></i></el-button>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column
                                prop="updTime"
                                label="勤怠表更新日時"
                                width="220"
                                align="center"
                                sortable
                                :formatter="formatter"
                            >
                            </el-table-column>
                             <el-table-column
                                prop="transportationName"
                                label="月末精算申請状態"
                                width="140"
                                align="center"
                            >
                            <template slot-scope="scope">
                                <div class="flex-space-between">
                                    <div>
                                        <el-tag  @click="dialogRouterOpen(scope.row.transRequestNumber)" type="success" v-if="scope.row.transportationCode == null || scope.row.transportationCode == '' || scope.row.transportationCode == '001'">{{ scope.row.transportationName = "未申請"}}</el-tag>
                                        <el-tag  @click="dialogRouterOpen(scope.row.transRequestNumber)" type="primary" v-if="scope.row.transportationCode == '003'">{{ scope.row.transportationName}}</el-tag>
                                        <el-tag  @click="dialogRouterOpen(scope.row.transRequestNumber)" type="danger" v-if="scope.row.transportationCode == '010'">{{ scope.row.transportationName}}</el-tag>
                                        <el-tag  @click="dialogRouterOpen(scope.row.transRequestNumber)" type="info" v-if="scope.row.transportationCode == '999'">{{ scope.row.transportationName}}</el-tag>
                                    </div>
                                    <div style="margin-top: 2px" v-if="scope.row.updateDatetime !== '' ">
                                        <el-button circle size="mini" @click="preViewTransExpenPdf(scope.row)" ><i class="el-icon-view"></i></el-button>
                                    </div>
                                </div>
                            </template>         
                            </el-table-column>
                            <el-table-column
                                prop="UpdateDatetime"
                                label="月末精算更新日時"
                                width="220"
                                align="center"
                                sortable
                                :datetimetter="datetimetter"
                            >
                            </el-table-column>
                            
                        </el-table>
                    </div>
                    <div>
                        <el-row style="margin-bottom:20px; width: 1200px">
                            <div> 
                                <el-col :span="4" class='flex-start' style="margin:0px" width="200px">
                                    <div class="back-btn" style="margin:10px" @click="backToToppage()">戻る</div>
                                </el-col>
                                <el-col :span="20" class='flex-end' style="margin:0px" width="1000px">
                                    <el-tooltip class="white-deep-btn tooltip-name" content="エクスポート選択" placement="top" v-if="bulkExport.length !== 0">
                                            <!-- テーブルのデータが0の時、非表示 -->
                                            <!-- v-if="bulkexport.length !== 0" -->
                                            <div  @click="downloadOpen()">勤怠表エクスポート</div>
                                        </el-tooltip>
                                    <!-- <div class="white-deep-btn" v-loading='exportLoading' @click="exportCsv()" v-if="exportBotann">
                                        エクスポート
                                    </div> -->
                                    <el-tooltip class="white-deep-btn tooltip2-name" content="エクスポート選択" placement="top" v-if="bulkExport.length !== 0">
                                            <!-- テーブルのデータが0の時、非表示 -->
                                            <!-- v-if="bulkExport.length !== 0" -->
                                            <div  @click="pdfBulkExportTransport()">月末精算エクスポート</div>
                                        </el-tooltip>
                                </el-col>
                                <!--<el-col :span="4" class='flex-start' style="margin:10px">
                                    <el-tooltip class="white-deep-btn tooltip2-name" content="エクスポート選択" placement="top" v-if="bulkExport.length !== 0">
                                            <div  @click="datetimeOpen()">月末精算エクスポート</div>
                                        </el-tooltip>
                                </el-col>-->
                            </div> 
                        </el-row>
                    </div>

                    <!-- ダイアログ -->
                    <div>
                        <el-dialog
                           title="勤怠表一括エクスポート"
                           :visible.sync="dialogSeeKintai"
                            width="530px"
                            :close-on-click-modal="true"
                            @close="userSearchClose()">
                            <div class="flex-center export-dialog">
                                <el-row  class="flex-start">
                                    <div ><img src="@/assets/images/bulkExport-pdf-100.png"   alt="" class="ma-10 hover10"  @click="pdfBulkExportAttendance()" v-loading='exportLoading'  v-loading.fullscreen = "fullscreenLoading"></div>
                                    <el-col class="flex-end">
                                        <div><img src="@/assets/images/bulkExport-excel-100.png" alt="" class="ma-11 hover10" @click="excelExportCsv()" v-loading='exportLoading'  v-loading.fullscreen = "fullscreenLoading"></div>
                                    </el-col>
                                </el-row>
                            </div>
                        </el-dialog> 
                    </div>
                    <!--
                    <div>
                        <el-dialog
                           title="月末精算一括エクスポート"
                           :visible.sync="dialogSeeGetumatu"
                            width="530px"
                            :close-on-click-modal="true"
                            @close="userSearchClose()">
                            <div class="flex-center export-dialog">
                                <el-row  class="flex-start">
                                    <div ><img src="@/assets/images/bulkExport-pdf-100.png"   alt="" class="ma-10 hover10" @click="pdfBulkExport()" v-loading='exportLoading'  ></div>
                                    <el-col class="flex-end">
                                        <div><img src="@/assets/images/bulkExport-excel-100.png" alt="" class="ma-11 hover10"  v-loading='exportLoading'  v-loading.fullscreen = "fullscreenLoading"></div>
                                    </el-col>
                                </el-row>
                            </div>
                        </el-dialog> 
                    </div>
                    -->
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
        <!-- ルートのポップアップ -->
        <el-dialog
            title="ルート"
            :visible.sync="dialogRouter"
            width="500px"
            :close-on-click-modal="true">
            <div class="block approval_route">
                <el-timeline :reverse='true'>
                    <el-timeline-item v-for="(item, index) in routerList" :key='index' :timestamp='item.reqAppDatetime' placement="top" :color='routerColor(item.wfStatusKbn)'>
                        <el-card>
                            <div class="flex-between"><h4>{{item.userName}}</h4><h4 :class="routerStatusColor(item.categoryKbnName)">{{item.categoryKbnName}}</h4></div>
                            <p>{{item.reqAppComment}}</p>
                        </el-card>
                    </el-timeline-item>
                </el-timeline>
            </div>
            <!-- キャンセルボタン -->
            <!-- <span slot="footer" class="dialog-footer">
                <el-button @click="dialogRouter = false">キャンセル</el-button>
            </span> -->
        </el-dialog>
    </div>
</template>
<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";
    import { Message } from 'element-ui';
    import { getDepartment } from '@/api/department';
    import { getUserInfo } from '@/utils/auth';
    import { getCategory } from '@/api/category'
    import {searchBulkExport, exportToProveExcel, getAttendancePdfResource,
        exportToProvePdfAttendance, exportToProvePdfTransport, getTransportationExpenses} from '@/api/attendanceExport'
    import { getWorkFlowList } from '@/api/workFlow'

    export default {
        name: "attendanceExport",
        components: {
            commonHeader,
            commonFootter
        },
        // 初期画面
        created() {
            this.companyCode = getUserInfo().companyCode
            console.log('this.companyCode', this.companyCode)
            // console.log(this.getAllUserName())
            // this.getAllUserName()

            // 年月の初期処理
            let nowDate = new Date();
            let month = nowDate.getMonth() + 1 + ""
            this.workDateYm = nowDate.getFullYear() + "" + (month.length == 1 ? ("0"+month) : month)

            // 申請状態のオプション
            let StatusKbnData = {
                companyCode: this.companyCode,
                categoryClassNumber: '030'
            }
            getCategory(StatusKbnData).then(res => {
                console.log("StatusKbnData",res)
                this.StatusKbnList = res.data
                //保存（001(null)）を未申請に変える
                for ( let i = 0; i < this.StatusKbnList.length;i++) {
                    if (this.StatusKbnList[i].categoryKbnName == '保存') {
                        this.StatusKbnList[i].categoryKbnName = '未申請'
                    }
                    this.StatusKbnList[i]
                }
                console.log("StatusKbnData",res)
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            
            // 検索を押下せずにをデータを表示
            // this.search()

            //所属の初期処理
            this.getDepartmentKbnlist()
             //現在年の初期代入処理
            this.targetYear=this.currentDate+' 00:00:00 GMT+0900 (日本標準時)'
            console.log(this.currentDate)     
        },
        data() {
            return {
                companyCode:'',
                //年月
                workDateYm: '',
                //所属
                departmentKbnList: [],
                departmentKbnStatus: '',
                //状態
                StatusKbnList: {index: '',categoryKbnName: '' },
                selectStatus:'',
                transportationSelectStatus:'',
                travelSelectStatus:'',
                //社員番号＆社員名
                formSearch: {
                    search_id: ''
                },
                input:'',
                value: '',
                //テーブル
                bulkExport:[],
                //チェックボックス
                multipleSelection: [],
                
                // エクスポートボタン（非表示）
                // exportBotann:false,
                
                // エクスポート=ダイアログ（非表示：勤怠エクスポート,月末エクスポート）
                exportLoading: false,
                dialogSeeKintai: false,
                dialogSeeGetumatu: false,
                //読み込み中のアニメーション
                fullscreenLoading: false,
                //状態のエラーメッセージ
                numFalse: '',
                // 承認ルート確認
                routerList: [],
                dialogRouter: false
            }
        },
        methods: {
            //所属リスト取得
            getDepartmentKbnlist(){
                let data ={companyCode:this.companyCode}
                getDepartment(data).then(res => {
                    console.log(res)
                    this.departmentKbnList = res.data
                        // let blockArray = {departmentCode: '',departmentName: '全て'}
                    this.departmentKbnList.unshift(blockArray)
                }).catch(error => {
                })
            },
            

            //検索ボタン押下時の処理("年月＆所属",状態,"社員番号or社員名"で検索)
            search(){
                // 一応年月欄を必須とする（ブランクだとマスタ内にある全てのデータを抽出対象とするので、表示速度落ちる）
                if(this.workDateYm==null){
                    Message({
                            message: '年月を選択してください',
                            type: 'error',
                            duration: 3000
                            
                        })
                    return 
                }
                
                const that = this
      
                let data = {
                    companyCode: this.companyCode,
                    workDateYm: this.workDateYm==null?"":this.workDateYm,
                    departmentCode: this.departmentKbnStatus.replaceAll(' ', ''),
                    requestStatusKbn: this.selectStatus,
                    transportationCode: this.transportationSelectStatus,
                    travelCode: this.travelSelectStatus,
                    userId: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', ''),
                }

                searchBulkExport(data).then(res =>{
                    that.bulkExport = []
                    that.bulkExport = res.data
                    this.multipleSelection = []
                    console.log('that.bulkExport', that.attendanbulkExportceExport)
                    console.log(that.bulkExport)
                }).catch(error => {
                    console.log(error)
                })
            },
            

            // 並び替え(社員番号,勤怠更新日時,月末更新日時)
            formatter(row, column) {
                console.log('row', row)
                return row.updTime;
            },
            userIdtter(row, column) {
                return row.userId;
            },
            datetimetter(row, column){
                 return row.datetimeUpdTime;
            },
            //チェックボックス選択時実行する
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // エクスポートボタンの処理（Excel一括ダウンロード）
            excelExportCsv(){
                if(this.exportLoading == true){

                }
                this.exportLoading = true

                let data = {
                    companyCodeList: [],
                    userIdList: [],
                    workDateYmList: [],
                }

                // ループ処理
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    if(!this.multipleSelection[index].updTime) {
                        Message({
                            message: '対象年月の勤怠情報のない明細が選択されています',
                            type: 'error',
                            duration: 3000
                        })
                        this.exportLoading = false
                        return
                    }
                }
                
                //　読み込み中のアニメーション
                const loading = this.$loading({
                    lock: true,
                    text: '読み込み中...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                // setTimeout(() => {
                //  loading.close();
                // }, 2000);

                // ループ処理
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    const element = this.multipleSelection[index]
                        data.companyCodeList.push(element.companyCode)
                        data.userIdList.push(element.userId)
                        data.workDateYmList.push(element.workDateYm)
                        // data.requestStatusKbnList.push(element.requestStatusKbn)
                }

                exportToProveExcel(data).then(res => {
                    let blob = new Blob([res.data], {type: "application/zip"})
                    const href = URL.createObjectURL(blob) //创建新的URL表示指定的blob对象
                    const a = document.createElement('a') //创建a标签
                    a.style.display = 'none'
                    a.href = href // 指定下载链接
                    a.download = "勤務表_" + this.workDateYm + ".zip" //指定下载文件名
                    a.click() //触发下载
                    console.log(res.data)
                    //読み込み中アニメーション非表示
                    setTimeout(() => {
                        loading.close();
                    }, 100);
                    this.exportLoading = false
                    this.dialogSeeKintai = false

                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            
                                
            },

            // エクスポートボタンの処理（勤務表Pdf一括ダウンロード）
             pdfBulkExportAttendance(){
                 if(this.exportLoading == true){
                     return
                }
                this.exportLoading = true

                let data = {
                    companyCodeList: [],
                    userIdList: [],
                    workDateYmList: [],
                    requestStatusKbnList: [],
                }

                // ループ処理
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    if(!this.multipleSelection[index].updTime) {
                        Message({
                            message: '対象年月の勤怠情報のない明細が選択されています',
                            type: 'error',
                            duration: 3000
                        })
                        this.exportLoading = false
                        return
                    }
                }

                //　読み込み中のアニメーション
                const loading = this.$loading({
                    lock: true,
                    text: '読み込み中...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });

                // 処理対象を取得する
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    const element = this.multipleSelection[index]
                        // 勤務表出力の場合、同一IDが入ってくる場合があるため、回避する
                        if (data.userIdList.indexOf(element.userId) > 0) {
                            continue;
                        }
                        data.companyCodeList.push(element.companyCode)
                        data.userIdList.push(element.userId)
                        data.workDateYmList.push(element.workDateYm)
                        data.requestStatusKbnList.push(element.requestStatusKbn)
                }

                exportToProvePdfAttendance(data).then(res => {
                    let blob = new Blob([res.data], {type: "application/zip"})
                    const href = URL.createObjectURL(blob) 
                    const a = document.createElement('a')
                    a.style.display = 'none'
                    a.href = href
                    a.download = "勤務表_" + this.workDateYm + ".zip"
                    a.click()
                    console.log(res.data)
                    //読み込み中アニメーション非表示
                    setTimeout(() => {
                        loading.close();
                    }, 100);
                    this.exportLoading = false
                    this.dialogSeeKintai = false
                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            },

            // エクスポートボタンの処理（月末精算Pdf一括ダウンロード）
             pdfBulkExportTransport(){
                 if(this.exportLoading == true){
                     return
                }
                this.exportLoading = true

                let data = {
                    companyCodeList: [],
                    userIdList: [],
                    applyYmList: [],
                    requestNumberIdList: [],
                }
            if(this.multipleSelection.length > 0){
                // ループ処理
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    if(!this.multipleSelection[index].updateDatetime) {
                        Message({
                            message: '対象年月の月末精算情報のない明細が選択されています',
                            type: 'error',
                            duration: 3000
                        })
                        this.exportLoading = false
                        return
                    }
                }
            }else{
                Message({
                    message: '明細より出力対象を選択してください',
                    type: 'error',
                    duration: 3000
                })
                return
            }
                //　読み込み中のアニメーション
                const loading = this.$loading({
                    lock: true,
                    text: '読み込み中...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });

                // 処理対象を取得する
                for (let index = 0; index < this.multipleSelection.length; index++) {
                    const element = this.multipleSelection[index]
                        data.companyCodeList.push(element.companyCode)
                        data.userIdList.push(element.userId)
                        data.applyYmList.push(element.applyYm)
                        data.requestNumberIdList.push(element.transRequestNumber)
                }

                exportToProvePdfTransport(data).then(res => {
                    let blob = new Blob([res.data], {type: "application/zip"})
                    const href = URL.createObjectURL(blob) 
                    const a = document.createElement('a') 
                    a.style.display = 'none'
                    a.href = href 
                    a.download = "月末精算_" + this.workDateYm + ".zip" 
                    a.click() 
                    console.log(res.data)
                    //読み込み中アニメーション非表示
                    setTimeout(() => {
                        loading.close();
                    }, 100);
                    this.exportLoading = false
                    this.dialogSeeKintai = false
                }).catch(error => {
                    this.exportLoading = false
                    console.log(error)
                });
            },


            // 勤怠表エクスポートボタン押下処理
            // 選択中の明細があれば、勤怠表一括エクスポートダイアログを表示する
            downloadOpen() {
                if(this.multipleSelection.length > 0){
                    for (let index = 0; index < this.multipleSelection.length; index++) {
                        if(!this.multipleSelection[index].updTime) {
                            Message({
                                message: '対象年月の勤怠情報のない明細が選択されています',
                                type: 'error',
                                duration: 3000
                            })
                            return
                        }
                    }
                    this.dialogSeeKintai = true
                }else{
                    Message({
                        message: '明細より出力対象を選択してください',
                        type: 'error',
                        duration: 3000
                    })
                    return
                }
            },
            // 月末精算エクスポートボタン押下処理
            // 選択中の明細があれば、月末精算一括エクスポートダイアログを表示する
            datetimeOpen(){
                if(this.multipleSelection.length > 0){
                    for (let index = 0; index < this.multipleSelection.length; index++) {
                        if(!this.multipleSelection[index].updateDatetime) {
                            Message({
                                message: '対象年月の月末精算情報のない明細が選択されています',
                                type: 'error',
                                duration: 3000
                            })
                            return
                        }
                    }
                    this.dialogSeeGetumatu = true
                }else{
                    Message({
                        message: '明細より出力対象を選択してください',
                        type: 'error',
                        duration: 3000
                    })
                    return
                }
            },

            // ダイアログキャンセル押下時（非表示）
             userSearchClose() {
                 //this.dialogSee = false
             },
            //  戻るボタン
            backToToppage () {
                this.$router.push({
                    path: '/toppage'                  
                })
            },
            // 勤務表PDFプレビュー処理
            preViewAttendancePdf(val){
                // 対象月の勤怠データがない場合、エラーメッセージを出力し、処理を中断する
                if (val.updTime == ""){
                    Message({
                            message: '選択中の社員の勤怠情報はありません',
                            type: 'error',
                            duration: 3000
                        })
                    return 
                }
                
                let data = { companyCode: val.companyCode
                           , userId: val.userId
                           , workDateYm: val.workDateYm
                           , requestStatusKbn : val.requestStatusKbn
                           , accessMode : 2
                        }
                // 勤怠表PDFプレビュー処理を呼び出す
                getAttendancePdfResource(data).then(res =>{
                    //成功の場合
                    let blob = new Blob([res.data], {type: "application/pdf"})
                    const href = URL.createObjectURL(blob) 
                    window.open(href);
                }).catch(error => {
                    //失敗の場合
                    console.log(error)
                })
            },
            //交通費PDFプレビュー処理
            preViewTransExpenPdf(val){
                // 対象月の交通費データがない場合、エラーメッセージを出力し、処理を中断する
                if(val.UpdateDatetime == "" || val.transRequestNumber == ""){
                    Message({
                            message: '選択中の社員の月末精算情報はありません',
                            type: 'error',
                            duration: 3000
                        })
                    return 
                }
                console.log('val', val)
                let data = { companyCode: val.companyCode
                           , requestUserId: val.userId
                           , requestNumber: val.transRequestNumber
                           , applyYm: val.applyYm.substring(4,6)
                        }
                        console.log('data', data)
                // 交通費PDFプレビュー処理を呼び出す
                getTransportationExpenses(data).then(res =>{
                    //成功の場合
                    let blob = new Blob([res.data], {type: "application/pdf"})
                    const href = URL.createObjectURL(blob) 
                    window.open(href);
                }).catch(error => {
                    //失敗の場合
                    console.log(error)
                })
                
            },
           // 承認ルート
           dialogRouterOpen (requestNumber) {
                this.routerList = []
                console.log(requestNumber)
                let data = {
                    companyCode: getUserInfo().companyCode,
                    requestNumber: requestNumber
                }
                getWorkFlowList(data).then(res => {
                    console.log(res)
                    this.routerList = res.data
                    this.dialogRouter = true
                }).catch(error => {

                })
            },
            // 承認ルート状態の色
            routerStatusColor (categoryKbnName) {
                switch(categoryKbnName) {
                    case '未承認':
                        return 'blue-text'
                    case '承認完了':
                        return 'black-text'
                    case '差戻':
                        return 'red-text'
                    case '申請済み':
                        return 'orange-text'
                    case '承認済':
                        return 'orange-text'
                }
            },
            routerColor (wfStatusKbn) {
                switch (wfStatusKbn) {
                    case '020':
                        return '#4685ff'
                    case '021':
                        return '#fd5555'
                    case '025':
                        return '#ffa811'
                    case '012':
                        return '#ffa811'
                    case '900':
                        return '#333333'
                }
            },

        },
        mounted () {
        }


    }
</script>

<style lang="scss" scope>
    @import '../style/index.scss';
     //全体の設定
    .bulkexport-area {
        height: 750px;
        width: 100%;
        background-color: #fff;
        box-shadow: 0 3px 23px 0 #95959530;
        padding: 20px 30px;
        border-radius: 6px;

        .holidayList-header {
            padding: 0px 30px;
        }
    }
    //項目の大きさ,位置の設定
    .select-title {
        font-weight: bold;
        white-space: nowrap;
        font-size: 14px;
        margin-top: 8px;
    }
    //社員番号＆社員名の位置設定
    .input-name{
        margin-left:20px;
    }
    //勤怠表エクスポートボタンの位置設定
    .tooltip-name{
        margin-top:10px;
        margin-right: 10px;
    }
    .tooltip2-name{
        margin-top:10px;
    }
    // ダイアログ内の位置設定
    .export-dialog{
        margin-right: 25px;
        margin-left: 65px;
        margin-top: -10px;
    }
    // PDFエクスポート位置設定
    .ma-10 {
        margin-right: 50px;
        margin-left: 35px;
        margin-top: 15px;
        margin-bottom: 50px;
        // margin: 20px 40px 40px 50px;
    }
    // EXCELエクスポート位置設定
    .ma-11 {
        margin-right: 50px;
        margin-left: 35px;
        margin-top: 15px;
        margin-bottom: 50px;
        // margin: 20px 40px 40px 50px;
    }
    //Pdf,EXCELエクスポートアクション
    .hover10 {
        // border-radius: 50%;
        transition: all 0.6s ease 0s;
    }
    .hover10:hover {
        cursor: pointer;
        transform: scale(1.1, 1.1);
    }
    .cellStyleRed {
        color:crimson;
    }

    .approval_route {
        padding: 20px;
        height: 350px;
        overflow-y: scroll;
    }

</style>
