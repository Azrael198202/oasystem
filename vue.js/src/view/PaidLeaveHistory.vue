<template>
    <el-dialog
        title="有休履歴"
        :visible.sync="openreq"
        width="400px"
        :close-on-click-modal="true"
        >
        <div style="border: 1px solid #EBEEF5;">
            <div class="remaining-schedule">
                <div class="month-title">{{this.lastMonth+'月末残'}}</div>
                <div class="month-text">{{ lastMonthdata + '日'}}</div>
            </div>
        </div>
      
        <div class="card-paid">
            <div class="card-paid-title">{{this.thisMonth+'月予定'}}</div>
            <el-table
                :data="paidHolidayHistoryList"
                :header-cell-style="{color:'#888',background: '#f5f5f5',fontSize:'14px', height: '40px'}"
                :cell-class-name="cellStyle"
                style="width:100%"
                height="160px">
                <el-table-column
                    type="index"
                    label="No"
                    width="60">
                </el-table-column>
                <el-table-column
                    prop="holidayPlanDay"
                    label="日付"
                    width="140">
                    <template slot-scope="scope">
                        {{ scope.row.holidayPlanDay.substring(0,4) + '年' + scope.row.holidayPlanDay.substring(4,6) + '月' + scope.row.holidayPlanDay.substring(6,8) + '日'}}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="categoryKbnName"
                    label="区分"
                    width="80">
                </el-table-column>
                <el-table-column
                    prop="holidayDays"
                    label="日数"
                    width="80">
                </el-table-column>
            </el-table>
            <div class="remaining-schedule">
                <div class="month-title">{{this.thisMonth + '月末残:'}}</div>
                <div class="month-text">{{ endMonthdata + '日'}}</div>
            </div>
        </div>

        <div class="card-paid">
            <div class="card-paid-title">{{this.nextMonth+'月・'+this.monthAfterNext+'月予定'}}</div>
            <el-table
                :data="paidHolidayNextMonthList"
                :header-cell-style="{color:'#888',background: '#f5f5f5',fontSize:'14px', height: '40px'}"
                :cell-class-name="cellStyle"
                style="width:100%"
                height="160px">
                <el-table-column
                    type="index"
                    label="No"
                    width="54">
                </el-table-column>
                <el-table-column
                    prop="holidayPlanDay"
                    label="日付"
                    width="140">
                    <template slot-scope="scope">
                        {{ scope.row.holidayPlanDay.substring(0,4) + '年' + scope.row.holidayPlanDay.substring(4,6) + '月' + scope.row.holidayPlanDay.substring(6,8) + '日'}}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="categoryKbnName"
                    label="区分"
                    width="80">
                </el-table-column>
                <el-table-column
                    prop="holidayDays"
                    label="日数"
                    width="80">
                </el-table-column>
            </el-table>
            <div class="remaining-schedule">
                <div class="month-title">予定残</div>
                <div class="month-text">{{ endMonthAfterNextdata + '日'}}</div>
            </div>
        </div>
    </el-dialog>
    <!-- <div class="month-title">{{this.nextMonth+'月・'+this.monthAfterNext+'月予定'}} -->
        
</template>

<script>
    import { getCategory } from '@/api/category'
    import { paidHolidayHistory, paidHolidayNextMonth } from '@/api/requestHoliday'
    import { paidHolidayHistoryMonth } from '@/api/monthCloseHistory'
    import { getRequestPaidHoliday, EndMonthPaidHoliday, EndMonthAfterNextPaidHoliday, } from '@/api/paidHoliday'

    export default {
        props: ['userData','HeaderFlg'],
        name: "PaidLeaveHistory",
        created() {
            let changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            let rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            if(this.HeaderFlg){
                this.reqId = this.userData.userId;
            }else{
                this.reqId = changebyFlg=="1" ? this.userData.userId : rowdata.requestUserId;
            }
            // 有給休暇状態区分のオプション
            let paidHolidayKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '040',
            }
            getCategory(paidHolidayKbnData).then(res => {
                console.log(res)
                this.paidHolidayKbnList=res.data
            }).catch(error => {
                console.log(error)
            })
            let keyData = {
                companyCode: this.userData.companyCode,
                requestUserId: this.reqId,
            }
            paidHolidayHistory(keyData).then(res => {
                console.log(res)
                this.paidHolidayHistoryList = res.data
            }).catch(error => {
                    console.log(error)
            })
            paidHolidayNextMonth(keyData).then(res => {
                console.log(res)
                this.paidHolidayNextMonthList = res.data
            }).catch(error => {
                    console.log(error)
            })
            let getReqPaidHolidayData = {
                companyCode: this.userData.companyCode,
                userId: this.reqId,
            }
            console.log(getReqPaidHolidayData)
            getRequestPaidHoliday(getReqPaidHolidayData).then(res => {
                console.log(res)
                this.lastMonthdata = res.data
            }).catch(error => {
                    console.log(error)
            })
            EndMonthPaidHoliday(getReqPaidHolidayData).then(res => {
                console.log(res)
                this.endMonthdata = res.data
            }).catch(error => {
                    console.log(error)
            })
            EndMonthAfterNextPaidHoliday(getReqPaidHolidayData).then(res => {
                console.log(res)
                this.endMonthAfterNextdata = res.data
            }).catch(error => {
                    console.log(error)
            })
            //有休休暇履歴表示の月を取得
            let getmonthdisp = {
                userId: this.reqId,
            }
            paidHolidayHistoryMonth(getmonthdisp).then(res => {
                console.log(res)
                this.lastMonth=res.data[0].lastMonth
                this.thisMonth=res.data[0].thisMonth
                this.nextMonth=res.data[0].nextMonth
                this.monthAfterNext=res.data[0].monthAfterNext
            }).catch(error => {
                    console.log(error)
            })
        },

        data() {
            return {
                openreq: false,
                reqId: '',
                paidHolidayKbnList: '',
                lastMonthdata: '',
                endMonthdata: '',
                endMonthAfterNextdata: '',
                paidHolidayHistoryList: [],
                paidHolidayNextMonthList: [],
                dispMonthData: [],
                lastMonth: '',
                thisMonth: '',
                nextMonth: '',
                monthAfterNext: '',
            }
        },
        methods: {
            paidLeaveHistoryOpen(){
                this.openreq = true
            },
            // 申請状態（回覧中、保存、差戻、完了）の色
            cellStyle({row, column, rowIndex, columnIndex}){
                switch(row.categoryKbnName) {
                    case '加算':
                        if(columnIndex == 2) {
                            return 'green-text'
                        }else {
                            return ''
                        }

                    case '減算':
                        if(columnIndex == 2) {
                            return 'red-text'
                        }else {
                            return ''
                        }

                    case '付与':
                        if(columnIndex == 2) {
                            return 'green-text'
                        }else {
                            return ''
                        }

                    case '失効':
                        if(columnIndex == 2) {
                            return 'blue-text'
                        }else {
                            return ''
                        }
                }
            },
        }
    }
</script>

<style lang="scss" scope>
.card-paid {
    width: 100%;
    border: 1px solid #EBEEF5;
    height: auto;
    margin: 10px 0;
    .card-paid-title {
        font-size: 16px;
        font-weight: bold;
        margin: 10px 10px;
    }
}
.remaining-schedule {
    display: flex;
    justify-content: space-between;
}
.month-title {
    margin: 10px;
    font-size: 14px;
    line-height: 24px;
}
.month-text {
    margin: 10px;
    font-size: 16px;
    font-weight: bold;
}
</style>