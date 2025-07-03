<template>
    <div>
       <commonHeader></commonHeader>
       <div class="top-home">
         <div class="top-home-main">
            <div class="recognize-area">
                <div class="recognize-header">
                    <el-row>
                        <span class="card-title-big">承認一覧</span>
                    </el-row>
                    <div style="margin-top: 40px" class="flex">
                        <div class="flex">
                            <div class="option-title">承認状態</div>
                            <div style="width:150px">
                                <el-select v-model="selectStatus" placeholder="">
                                    <!-- 区分マスタ（区分分類033）でデータを取得して表示 -->
                                    <!-- <el-option label="承認完了以外" value="99"></el-option> -->
                                    <el-option
                                        v-for="item in StatusKbnList"
                                        :key="item.categoryKbn"
                                        :label="item.categoryKbnName"
                                        :value="item.categoryKbn"
                                        >
                                        {{item.categoryKbnName}}
                                    </el-option>
                                    <el-option label="全て" value=""></el-option>
                                </el-select>
                            </div>
                        </div>
                        <div class="flex">
                            <div class="option-title">申請種類</div>
                            <div style="width:150px">
                                <el-select v-model="applySelect" placeholder="">
                                    <el-option
                                        v-for="item in selectKbn"
                                        :key="item.categoryKbn"
                                        :label="item.categoryKbnName"
                                        :value="item.categoryKbn"
                                        >
                                        {{item.categoryKbnName}}
                                    </el-option>
                                </el-select>
                            </div>
                        </div>
                        <div class="flex">
                            <div class="option-title">期間</div>
                            <div >
                                <el-date-picker
                                    v-model="term"
                                    type="daterange"
                                    range-separator="～"
                                    start-placeholder="YYYY/MM/DD"
                                    end-placeholder="YYYY/MM/DD"
                                    value-format="yyyy/MM/dd"
                                    format="yyyy/MM/dd">
                                </el-date-picker>
                                <!-- {{term}} -->
                            </div>
                        </div>
                        <div class="flex">
                            <el-form ref="formSearch" :model="formSearch" label-width="0px" @submit.native.prevent>
                                <!-- <el-form-item prop="search_id" style="205px"></el-form-item> -->
                                <el-input
                                    ref="searchInput"
                                    v-model="formSearch.search_id"
                                    placeholder="社員番号or社員名" 
                                    @keyup.enter.native="search()"
                                    clearable>
                                </el-input>
                            </el-form>
                        </div>
                        <div style="margin-top:5px; margin-left:10px;">
                            <div class="green-btn" @click="serchBtn()">検索</div>
                        </div>
                    </div>
                </div>
                <el-table
                    :data="applyList"
                    :header-cell-style="{color:'#888',background: '#f5f5f5',fontSize:'14px', height: '40px'}"
                    :cell-class-name="cellStyle"
                    style="width: 100%;margin-top: 20px"
                    height="570px">
                    <el-table-column
                        prop="requestUserId"
                        width="20">
                        <template slot-scope="scope">
                            <span style="display: none">{{ scope.row.requestUserId}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="workDateYm"
                        width="1">
                        <template slot-scope="scope">
                            <span style="display: none">{{ scope.row.workDateYm}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="requestDate"
                        label="申請日"
                        width="160">
                        <template slot-scope="scope">
                            <!-- {{ scope.row.registrationDatetime.split(" ")[0]}} -->
                             {{ scope.row.requestDate.substring(0,4) + '年' +　scope.row.requestDate.substring(4,6) + '月' +　scope.row.requestDate.substring(6,8) + '日'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="recognizeKbnName"
                        label="種類"
                        width="150">
                    </el-table-column>
                    <el-table-column
                        prop="userName"
                        label="申請者"
                        width="160">
                        <template slot-scope="scope">
                            {{ scope.row.userName == null ? '' : scope.row.userName}}
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="requestOverview"
                        label="題名"
                        width="400">
                    </el-table-column>
                    <el-table-column
                        prop="wfStatusKbn"
                        width="1"
                    >
                    <template slot-scope="scope">
                        <div style="display: none">
                            {{ scope.row.wfStatusKbn }}
                        </div>
                    </template>
                    </el-table-column>
                    <el-table-column
                        prop="requestKbnName"
                        label="状態"
                        width="100">
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.wfStatusKbn == '020'">{{ scope.row.requestKbnName }}</el-tag>
                            <el-tag v-if="scope.row.wfStatusKbn == '025'" type="success">{{ scope.row.requestKbnName }}</el-tag>
                            <el-tag v-if="scope.row.wfStatusKbn == '900'" type="info">{{ scope.row.requestKbnName }}</el-tag>
                            <el-tag v-if="scope.row.wfStatusKbn == '021'" type="danger">{{ scope.row.requestKbnName }}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="requestStatusKbn"
                        label="最終状態"
                        width="90"
                        align="center">
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.requestStatusKbn == '999'" type="info">{{ scope.row.categoryKbnName = "済"}}</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="ルート"
                        width="100"
                        align="center">
                        <template slot-scope="scope">
                            <el-button circle @click="dialogRouterOpen(scope.row.requestNumber)"><i class="fas fa-sitemap" style="width: 14px;font-size: 11px"></i></el-button>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="詳細"
                        align="center">
                        <template slot-scope="scope">
                            <el-button icon="el-icon-more" circle @click="detailClick(scope.row)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="back-btn" style="margin: 20px" @click="backToToppage()">戻る</div>
            </div>
         </div>
       </div>
       <commonFootter></commonFootter>
       <el-dialog
            title="ルート"
            :visible.sync="dialogRouter"
            width="500px"
            :close-on-click-modal="true"
            >
            <div class="block approval_route">
                <el-timeline :reverse='true'>
                    <el-timeline-item v-for="(item,index) in routerList" :key='index' :timestamp='item.reqAppDatetime' placement="top" :color='routerColor(item.wfStatusKbn)'>
                        <el-card>
                            <div class="flex-between"><h4>{{item.userName}}</h4><h4 :class="routerStatusColor(item.categoryKbnName)">{{item.categoryKbnName}}</h4></div>
                            <p>{{item.reqAppComment}}</p>
                        </el-card>
                    </el-timeline-item>
                </el-timeline>
            </div>
            <!-- <span slot="footer" class="dialog-footer">
                <el-button @click="dialogRouter = false">キャンセル</el-button>
            </span> -->
        </el-dialog>
    </div>
</template>

<script>
import commonHeader from "@/layout/commonHeader.vue";
import commonFootter from "@/layout/commonFootter.vue";
import { getCategory } from '@/api/category'
import { getSelectWf, getWorkFlowList } from '@/api/workFlow'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
export default {
 name: 'Recognize',
 components: {
   commonHeader,
   commonFootter
 },
 async created() {
     // 申請状態のオプション
    await this.getSerchData()
    this.getRequest()
    let statusKbn = {
        companyCode: getUserInfo().companyCode,
        categoryClassNumber: '033'
    }
    getCategory(statusKbn).then(res => {
        console.log(res)
        this.StatusKbnList = res.data.filter((e=>{return e.categoryKbn!=='011' && e.categoryKbn!=='012'}))
        // if(this.StatusKbnList) {
        //     let blockArray = { categoryKbn: '', categoryKbnName: '全て'}
        //     this.StatusKbnList.unshift(blockArray)
        // }
        console.log(this.StatusKbnList)
        }).catch(error => {
            this.tableLoading = false
            console.log(error)
        })
    // 申請種類のオプション
    this.getRequest()
    let data = {
        companyCode: getUserInfo().companyCode,
        categoryClassNumber: '031'
    }
    getCategory(data).then(res => {
        this.selectKbn = res.data
        if(this.selectKbn) {
            let blockArr = { categoryKbn: '', categoryKbnName: '全て'}
            this.selectKbn.unshift(blockArr)
        }
        console.log(this.selectKbn)
    }).catch(error => {
        console.log(error)
    })

 },
 data () {
   return {
    selectStatus: '020',
    applySelect: '',
    StatusKbnList:[],
    selectKbn: [],
    applyList: [],
    routerList: [],
    dialogRouter: false,
    term:'',
    //社員番号＆社員名
    formSearch: {
        search_id: ''
    },
    startDate: '',
    endDate: '',
    //各詳細画面から申請一覧に戻るときに検索条件部にセットする用
    startDateDsp: '',
    endDateDsp: ''
   }
 },
 methods: {
    //  戻るボタン
    backToToppage () {
        this.$router.push({
            path: '/toppage'
        })
    },
    // 詳細ボタン
    detailClick (rowData) {
        console.log(rowData)
        sessionStorage.setItem("row-data",JSON.stringify(rowData));
        sessionStorage.setItem("changeFlg",'3');
        console.log(rowData)
        if(rowData.requestTypeKbn == '010'){
            this.$router.push({
                path: '/attendance'
            })
        }else{
            this.$router.push({
                path: '/applyAll'
            })
        }
    },
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
    // 未承認、承認済み、差戻、承認完了
    cellStyle({row, column, rowIndex, columnIndex}){
        switch(row.requestKbnName) {
            case '未承認':
                if(columnIndex == 6) {
                    return 'blue-text'
                }else {
                    return ''
                }
            case '承認済':
                if(columnIndex == 6) {
                    return 'orange-text'
                }else {
                    return ''
                }
            case '差戻':
                if(columnIndex == 6) {
                    return 'red-text'
                }else {
                    return ''
                }
            case '承認完了':
                if(columnIndex == 6) {
                    return 'black-text'
                }else {
                    return ''
                }
        }

    },
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
    getRequest() {
        this.applyList = []
        if(this.term){
            this.startDate = this.term[0].replaceAll('/', '')
            this.endDate = this.term[1].replaceAll('/', '')
        }else{
            this.startDate = ''
            this.endDate = ''
        }
        let data = {
            companyCode: getUserInfo().companyCode,
            reqAppUserId: getUserInfo().userId,
            wfStatusKbn: this.selectStatus,
            requestTypeKbn: this.applySelect,
            startDate: this.startDate,
            endDate: this.endDate,
            userId: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', '')
        }
        console.log('data', data)
        getSelectWf(data).then(res => {
            console.log(res)
            this.applyList = res.data
            // console.log(this.applyList)
        }).catch(error => {

        })
    },
    //検索ボタン押下時処理
    serchBtn() {
        // let serchData={
        //     wfStatusKbn: this.selectStatus,
        //     requestTypeKbn: this.applySelect
        // }
        if(this.term){
            this.startDate = this.term[0].replaceAll('/', '')
            this.endDate = this.term[1].replaceAll('/', '')
            this.startDateDsp = this.term[0]
            this.endDateDsp = this.term[1]
        }else{
            this.startDate = ''
            this.endDate = ''
            this.startDateDsp = ''
            this.endDateDsp = ''
        }
        let serchData={
            wfStatusKbn: this.selectStatus,
            requestTypeKbn: this.applySelect,
            startDateDsp:this.startDateDsp,
            endDateDsp:this.endDateDsp,
            userId: this.$refs.searchInput.value.replaceAll(' ', '').replaceAll('  ', '')
        }
        console.log('serchData', serchData)
        sessionStorage.setItem("recognize-serch-data",JSON.stringify(serchData))
        this.getRequest()
    },
    //画面読み込み時に検索条件がセッションストレージに存在するかを確認し存在した場合に検索条件をセットする
    async getSerchData(){
        let serchData = await JSON.parse(sessionStorage.getItem("recognize-serch-data"))
        if(serchData !== null){
            this.selectStatus = serchData.wfStatusKbn
            this.applySelect = serchData.requestTypeKbn
            this.term = [serchData.startDateDsp, serchData.endDateDsp]
            this.formSearch.search_id = serchData.userId
        }
    }
 }
}
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.recognize-area {
    height: 760px;
    width: 100%;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 0px;
    border-radius: 6px;

    .recognize-header {
        padding: 0px 30px;
    }
}

.approval_route {
    padding: 20px;
    height: 350px;
    overflow-y: scroll
 }

 .option-title {
     line-height: 40px;
     font-size: 14px;
     margin-right: 10px;
 }
 
 .search-box{
    display: flex;
 }
 .flex {
    display: flex;
    margin-right: 22px;
 }
</style>
