<template>
    <div class="home">
        <commonHeader></commonHeader>
        <div class="top-home">
            <div class="top-home-main">
                <div class="dayCheckHistoryList-area">
                    <div class="dayCheckHistoryList-header">
                        <el-row>
                            <el-col :span="10">
                                <span class="card-title-big">日別チェック処理一覧</span>
                            </el-col>
                        </el-row>
                        <el-row style="margin-top: 40px">
                                <el-col :span="5" class="flex-start">
                                    <div class="select-S-title">社員名:</div>
                                    <el-select v-model="userName" placeholder="名前">
                                        <!-- 全社員名表示 -->
                                        <el-option label="" value=""></el-option>
                                            <el-option
                                                v-for="item in allUserNameList"
                                                :key="item.firstName + ' ' + item.lastName"
                                                :label="item.firstName + ' ' + item.lastName"
                                                :value="item.firstName + ' ' + item.lastName">
                                                {{item.firstName + ' ' + item.lastName}}
                                            </el-option>
                                        <el-option label="全て" value=""></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="5" class="flex-start">
                                    <div class="select-S-title">日付:</div>
                                    <el-date-picker
                                        v-model="targetYmd"
                                        type="date"
                                        placeholder="年月日"
                                        format="yyyy/MM/dd"
                                        value-format="yyyy/MM/dd">
                                    </el-date-picker>
                                </el-col>
                            <el-col :span="14" class="flex-end" style="margin-top:5px">
                                <div class="green-btn"  @click="search()">検索</div>
                            </el-col>
                        </el-row>
                    </div>
                    <el-table
                        :data="DayCheckHistoryList.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
                        :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                        :row-style="{height:'62px'}"
                        style="width: 100%"
                        height="550px">
                        <el-table-column
                            width="20">
                        </el-table-column>
                         <el-table-column
                            prop="userName"
                            label="社員名"
                            width="140">
                        </el-table-column>
                        <el-table-column
                            prop="branchNumber"
                            label="枝番"
                            width="60">
                        </el-table-column>
                        <el-table-column
                            prop="checkDay"
                            label="チェック日"
                            width="160">
                            <template slot-scope="scope">
                                {{ scope.row.checkDay.substring(0,4) + '年' + scope.row.checkDay.substring(4,6) + '月' + scope.row.checkDay.substring(6,8) + '日'}}
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="errCd"
                            label="エラー番号"
                            width="100">
                            <template slot-scope="scope">
                                {{ '　　' + scope.row.errCd }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="errMessage"
                            label="エラーメッセージ"
                            width="400">
                        </el-table-column>
                        <el-table-column
                            prop="updateUser"
                            label="更新者"
                            width="210">
                        </el-table-column>
                        <el-table-column
                            prop="updateDatetime"
                            label="更新日時"
                            width="210">
                        </el-table-column>
                    </el-table>
                    <div class="pagenation">
                        <el-pagination
                            :page-size="50"
                            :current-page.sync="currentPage"
                            layout="prev, pager, next"
                            :total="DayCheckHistoryList.length"
                            @current-change="habdleCurrentChange">
                        </el-pagination>
                    </div>
                    <el-col :span="4" class='flex-start' style="margin-left:20px" width="200px">
                        <div class="back-btn" style="margin-left:10px" @click="backToToppage()">戻る</div>
                    </el-col>
                </div>
            </div>
        </div>
        <commonFootter></commonFootter>
    </div>
</template>

<script>
    import commonHeader from "@/layout/commonHeader.vue";
    import commonFootter from "@/layout/commonFootter.vue";
    import { getUserInfo } from '@/utils/auth'
    import { getUserDetail } from '@/api/user'
    import { getDayCheckHistory, searchDayCheckHistory } from '@/api/dayCheckHistory'


    export default {
        name: "DayCheckHistoryList",
        components: {
            commonHeader,
            commonFootter
        },
        created() {
            this.companyCode = getUserInfo().companyCode
            this.getAllUserName()
            this.dayCheckHistoryList()
        },
        data() {
            return {
                allUserNameList: [],
                userName: [],
                tableData: [],
                targetYmd: '',
                companyCode: '',
                DayCheckHistoryList: [],
                currentPage: 1,
                pageSize: 50
            }
        },
        computed: {
        },
        methods: {
            // ページネーション
            habdleCurrentChange(val) {
                this.DayCheckHistoryList = this.DayCheckHistoryList            
            },
            //全社員名取得
            getAllUserName(){
                let data ={
                    companyCode:this.companyCode,
                    userId: ''
                    }
                getUserDetail(data).then( res => {
                    this.allUserNameList = res.data
                }).catch(error => {  
                })
            },
            // 日別チェック処理履歴の取得
            dayCheckHistoryList(){
                let departmentData = {
                    companyCode: this.companyCode,
                }
                getDayCheckHistory(departmentData).then(res => {
                    this.DayCheckHistoryList = res.data
                }).catch(error => {
                    console.log(error)
                })
            },
            // 社員名または日付で検索
            search(){
                let data = {
                    companyCode: this.companyCode,
                    userName: this.userName.replaceAll(' ', ''),
                    checkDay: this.targetYmd == null ? '' : this.targetYmd.replaceAll('/', '')
                }
                searchDayCheckHistory(data).then(res => {
                    this.DayCheckHistoryList = res.data
                }).catch(error => {
                    console.log(error)
                })
            },
            //  戻るボタン
            backToToppage () {
                this.$router.push({
                    path: '/toppage'                  
                })
            },
        }
    }
</script>

<style lang="scss" scope>
@import '../style/index.scss';

.dayCheckHistoryList-area {
    height: 760px;
    width: 100%;
    background-color: #fff;
    box-shadow: 0 3px 23px 0 #95959530;
    padding: 20px 0px;
    border-radius: 6px;

    .dayCheckHistoryList-header {
        padding: 0px 30px;
        margin-bottom: 20px;
    }
}
.select-S-title {
    font-weight: bold;
    white-space: nowrap;
    font-size: 16px;
    margin-top: 8px;
}
.pagenation {
    text-align:center;
}

</style>