<template>
    <div class="apply-area">
        <div class="apply-area-title">
            <el-row>
                <span class="card-title-big">申請一覧</span>
            </el-row>
            <el-row style="margin-top: 40px">
                <el-col :span="7" class="flex-start">
                    <div class="option-title">申請状態</div>
                    <div style="width:200px">
                        <el-select v-model="select_status" placeholder="未完了">
                            <!-- 区分マスタ（区分分類030）でデータを取得して表示 -->
                            <el-option label="未完了" value="0"></el-option>
                            <el-option
                                v-for="item in StatusKbnList"
                                :key="item.categoryKbn"
                                :label="item.categoryKbnName"
                                :value="item.categoryKbn">
                                {{item.categoryKbnName}}
                            </el-option>
                            <el-option label="全て" value=""></el-option>
                        </el-select>
                    </div>
                </el-col>
                <el-col :span="7" class="flex-start">
                    <div class="option-title">申請種類</div>
                    <!-- 区分マスタ（区分分類031）でデータを取得して表示 -->
                    <div style="width:200px">
                        <el-select v-model="select_type" placeholder="全て">
                            <el-option label="全て" value=""></el-option>
                            <el-option
                                v-for="item in ApplyKbnList"
                                :key="item.categoryKbn"
                                :label="item.categoryKbnName"
                                :value="item.categoryKbn">
                                {{item.categoryKbnName}}
                            </el-option>
                        </el-select>
                    </div>
                </el-col>
                <!-- <el-col :span="5" class="flex-start">
                    <div class='white-btn' @click="onClick()" style="width: 150px; height: 35px; line-height: 35px">オプション <i class="fas fa-plus-circle"></i></div>
                </el-col> -->
                <el-col :span="10" class="flex-end" style="margin-top:5px">
                    <div class="green-btn"  @click="search()">検索</div>
                </el-col>
            </el-row>
        </div>
        <el-table
            :data="searchApplyList"
            :header-cell-style="{color:'#888',borderBottom: 'none',background: '#f5f5f5',fontSize:'14px',fontWeight: 600, height: '40px'}"
            :cell-class-name="cellStyle"
            style="width: 100%"
            height="616px">
            <el-table-column
                width="20">
            </el-table-column>
            <!-- 申請番号（画面非表示） -->
            <el-table-column
                prop="requestNumber"
                label=""
                width="1">
                <template slot-scope="scope">
                    <div style="display: none">
                        {{ scope.row.requestNumber }}
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                prop="requestDate"
                label="申請日"
                width="150">
                <template slot-scope="scope">
                    {{ scope.row.requestDate.substring(0,4) + '年' + scope.row.requestDate.substring(4,6) + '月' + scope.row.requestDate.substring(6,8) + '日'}}
                </template>
            </el-table-column>
            <el-table-column
                prop="requestTypeName"
                label="種類"
                width="150">
            </el-table-column>
            <el-table-column
                prop="requestOverview"
                label="概要"
                width="450">
            </el-table-column>
            <el-table-column
                prop="requestStatusKbn"
                width="1"
            >
            <template slot-scope="scope">
                <div style="display: none">
                    {{ scope.row.requestStatusKbn }}
                </div>
            </template>
            </el-table-column>
            <!-- <el-table-column
                prop="remark"
                label="補足"
                width="240">
            </el-table-column> -->
            <el-table-column
                prop="statusKbnName"
                label="状態"
                width="100">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.requestStatusKbn == '003'">{{ scope.row.statusKbnName }}</el-tag>
                    <el-tag v-if="scope.row.requestStatusKbn == '001'" type="success">{{ scope.row.statusKbnName }}</el-tag>
                    <el-tag v-if="scope.row.requestStatusKbn == '999'" type="info">{{ scope.row.statusKbnName }}</el-tag>
                    <el-tag v-if="scope.row.requestStatusKbn == '010'" type="danger">{{ scope.row.statusKbnName }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                label="ルート"
                align="center"
                width="80">
                <template slot-scope="scope">
                    <el-button circle @click="dialogRouterOpen(scope.row.requestNumber)"><i class="fas fa-sitemap" style="width: 14px;font-size: 11px"></i></el-button>
                    <!-- <div style="height: 40px">
                        <div class='white-btn' style="width: 30px;height: 30px;margin-top: 6px;" @click="dialogRouterOpen(scope.row.requestNumber)">
                            <i class="fas fa-sitemap" style="font-size: 10px"></i>
                        </div>
                    </div> -->
                </template>
            </el-table-column>
            <el-table-column
                label="詳細"
                align="center"
                >
                <template slot-scope="scope">
                    <el-button icon="el-icon-more" circle @click="detailClick(scope.row)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- オプションのポップアップ -->
        <el-dialog title="工事中" :visible.sync="outerVisible">
            <div slot="footer" class="dialog-footer">
                <el-button @click="outerVisible = false">Cancel</el-button>
                <el-button type="primary" @click="outerVisible = false">open the inner Dialog</el-button>
            </div>
        </el-dialog>
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
    import { searchRequest } from '@/api/requestList'
    import { getCategory } from '@/api/category'
    import { getWorkFlowList } from '@/api/workFlow'

    export default {
        props: ['userData'],
        name: "ApplyList",
        components: {
        },
        created() {
            // 申請状態のオプション
            let StatusKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '030'
            }
            getCategory(StatusKbnData).then(res => {
                console.log(res)
                this.StatusKbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 申請種類のオプション
            let ApplyKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '031'
            }
            getCategory(ApplyKbnData).then(res => {
                console.log(res)
                this.ApplyKbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 休暇種類のオプション
            let LeaveKbnData = {
                companyCode: this.userData.companyCode,
                categoryClassNumber: '040',
            }
            getCategory(LeaveKbnData).then(res => {
                console.log(res)
                this.LeavekbnList = res.data
            }).catch(error => {
                this.tableLoading = false
                console.log(error)
            })
            // 初期表示（状態：未完了、種類：全て）
            let data = {
                companyCode: this.userData.companyCode,
                requestUserId :this.userData.userId,
                requestStatusKbn: '0',
                requestTypeKbn: ''
            }
            searchRequest(data).then(res => {
                console.log(res)
                this.searchApplyList = res.data
            }).catch(error => {
                    console.log(error)
            })
        },

        data() {
            return {
                select_status: '0',
                select_type: '',
                StatusKbnList: {index: '',categoryKbnName: '' },
                ApplyKbnList: {index: '',categoryKbnName: '' },
                LeavekbnList: {index: '',categoryKbnName: '', categoryKbn: '' },
                outerVisible: false,
                dialogRouter: false,
                changebyFlg: '',
                // 承認ルート確認
                routerList: [],
                // 詳細ボタン
                // detailList: [],
                // 申請一覧
                searchApplyList: [],
                rowdata : [],
            }
        },

        computed: {

        },

        methods: {
            // オプションのポップアップ
            onClick () {
                this.outerVisible = true
            },
            // 状態または申請種類検索
            search(){
                let data = {
                    companyCode: this.userData.companyCode,
                    requestUserId :this.userData.userId,
                    requestStatusKbn: this.select_status,
                    requestTypeKbn: this.select_type
                }
                searchRequest(data).then(res => {
                    console.log(res)
                    this.searchApplyList = res.data
                }).catch(error => {
                    console.log(error)
                })
            },
            // 承認ルート
            dialogRouterOpen (requestNumber) {
                this.routerList = []
                console.log(requestNumber)
                let data = {
                    companyCode: this.userData.companyCode,
                    requestNumber: requestNumber
                }
                getWorkFlowList(data).then(res => {
                    console.log(res)
                    this.routerList = res.data
                    this.dialogRouter = true
                }).catch(error => {

                })
            },
            // 詳細ボタン
            detailClick (rowData) {
                console.log(rowData)
                sessionStorage.setItem("row-data",JSON.stringify(rowData))
                this.rowdata = JSON.parse(sessionStorage.getItem("row-data"))
                sessionStorage.setItem("changeFlg",'2')
                this.changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"))
                if(rowData.requestTypeKbn == '010'){
                    this.$router.push({
                        path: '/attendance'
                    })
                }else{
                    this.movePage()
                }

            },
            // 申請状態（回覧中、保存、差戻、完了）の色
            cellStyle({row, column, rowIndex, columnIndex}){
                switch(row.statusKbnName) {
                    case '完了':
                        if(columnIndex == 5) {
                            return 'black-text'
                        }else {
                            return ''
                        }

                    case '回覧中':
                        if(columnIndex == 5) {
                            return 'blue-text'
                        }else {
                            return ''
                        }

                    case '保存':
                        if(columnIndex == 5) {
                            return 'green-text'
                        }else {
                            return ''
                        }

                    case '差戻':
                        if(columnIndex == 5) {
                            return 'red-text'
                        }else {
                            return ''
                        }
                }
            },
            movePage: function(){
                this.$emit('changePage',this.rowdata.requestTypeKbn,this.rowdata.holidayTypeKbn)
            },
            nameSearch(name) {
                for(let i = 0; i < this.LeavekbnList.length;i++) {
                    if(this.LeavekbnList[i].categoryKbnName == name) {
                        return this.LeavekbnList[i].categoryKbn
                    }
                }
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
        }
    }
</script>

<style lang="scss" scope>
.approval_route {
    padding: 20px;
    height: 350px;
    overflow-y: scroll;
}
.apply-area-title {
    padding: 0 20px;
    margin-bottom: 20px;
}
.option-title {
    font-size: 14px;
    line-height: 40px;
}
</style>