<template>
    <el-dialog
        title="代休履歴"
        :visible.sync="openreq"
        width="400px"
        :close-on-click-modal="true">
        <div style="border: 1px solid #EBEEF5;">
            <div class="remaining-schedule">
                <div class="month-title">{{this.lastMonth+'月末残:'}}</div>
                <div class="month-text">{{ lastMonthdata + '日'}}</div>
            </div>
        </div>

        <div class="card-paid">
            <div class="card-paid-title">{{this.thisMonth+'月予定'}}</div>
            <el-table
                :data="subHolidayHistoryList"
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
                <div class="month-text">{{ endOfThisMonthSubHolidaydata + '日'}}</div>
            </div>
        </div>

        <div class="card-paid">
            <div class="card-paid-title">{{this.nextMonth+'月・'+this.monthAfterNext+'月予定'}}</div>
            <el-table
                :data="subHolidayNextMonthList"
                :header-cell-style="{color:'#888',background: '#f3f3f3',fontFamily:'MicrosoftYaHeiUI',fontSize:'14px',fontWeight: 600, height: '40px'}"
                :cell-class-name="cellStyle"
                style="width= 100%"
                height="192px">
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
                <div class="month-text">{{ theEndOfTheFollowingMonthSubHolidaydata + '日'}}</div>
            </div>
        </div>
    </el-dialog>
</template>

<script>
    import { getCategory } from '@/api/category'
    import { subHolidayHistory, subHolidayNextMonth } from '@/api/requestHoliday'
    import { paidHolidayHistoryMonth } from '@/api/monthCloseHistory'
    import { getRequestSubHoliday,endOfThisMonthSubHoliday,theEndOfTheFollowingMonthSubHoliday } from '@/api/subHoliday'

    export default {
        props: ['userData','HeaderFlg'],
        name: "DayOffHistory",
        components: {

        },
        created() {
            let changebyFlg = JSON.parse(sessionStorage.getItem("changeFlg"));
            let rowdata = JSON.parse(sessionStorage.getItem("row-data"));
            if(this.HeaderFlg){
                this.reqId = this.userData.userId;
            }else{
                this.reqId = changebyFlg=="1" ? this.userData.userId : rowdata.requestUserId;
            }
            this.getCurrentTime()
            this.getLastMonth()
            this.getThisMonth()
            this.getNextMonth()
            this.getTwoMonthsLater()
            this.lastMonth = this.LastMonth
            this.thisMonth = this.ThisMonth
            this.nextMonth = this.NextMonth
            this.monthAfterNext = this.TwoMonthsLater
            // 有給休暇状態区分のオプション
            let paidHolidayKbnData = {
                companyCode:  this.userData.companyCode,
                categoryClassNumber: '040',
            }
            getCategory(paidHolidayKbnData).then(res => {
                console.log(res)
                this.paidHolidayKbnList=res.data
                console.log('getCategory', this.paidHolidayKbnList)
            }).catch(error => {
                console.log(error)
            })
            let keyData = {
                companyCode: this.userData.companyCode,
                requestUserId: this.reqId,
                setDate: this.today
            }
            console.log('keyData', keyData)
            // 当月分代休予定内容
            subHolidayHistory(keyData).then(res => {
                console.log(res)
                this.subHolidayHistoryList = res.data
                console.log('subHolidayHistory', this.subHolidayHistoryList)
            }).catch(error => {
                    console.log(error)
            })
            // 翌月翌々月分代休予定内容
            subHolidayNextMonth(keyData).then(res => {
                console.log(res)
                this.subHolidayNextMonthList = res.data
                console.log('subHolidayNextMonth', this.subHolidayNextMonthList)
            }).catch(error => {
                    console.log(error)
            })
            let getReqSubHolidayData = {
                companyCode: this.userData.companyCode,
                userId: this.reqId,
                setDate: this.today
            }
            console.log('getReqSubHolidayData', getReqSubHolidayData)
            // 前月末残
            getRequestSubHoliday(getReqSubHolidayData).then(res => {
                console.log(res)
                this.lastMonthdata = res.data
                console.log('getRequestSubHoliday', this.lastMonthdata)
            }).catch(error => {
                    console.log(error)
            })
            // 当月末残
            endOfThisMonthSubHoliday(getReqSubHolidayData).then(res => {
                console.log(res)
                this.endOfThisMonthSubHolidaydata = res.data
                console.log('endOfThisMonthSubHoliday', this.endOfThisMonthSubHolidaydata)
            }).catch(error => {
                    console.log(error)
            })
            // 予定残
            theEndOfTheFollowingMonthSubHoliday(getReqSubHolidayData).then(res => {
                console.log(res)
                this.theEndOfTheFollowingMonthSubHolidaydata = res.data
                console.log('theEndOfTheFollowingMonthSubHoliday', this.theEndOfTheFollowingMonthSubHolidaydata)
            }).catch(error => {
                    console.log(error)
            })
            //有休休暇履歴表示の月を取得
            // let getmonthdisp = {
            //     userId: this.reqId,
            // }
            // paidHolidayHistoryMonth(getmonthdisp).then(res => {
            //     console.log(res)
            //     this.lastMonth=res.data[0].lastMonth
            //     this.thisMonth=res.data[0].thisMonth
            //     this.nextMonth=res.data[0].nextMonth
            //     this.monthAfterNext=res.data[0].monthAfterNext
            // }).catch(error => {
            //         console.log(error)
            // })
        },

        data() {
            return {
                openreq: false,
                reqId: '',
                paidHolidayKbnList: '',
                lastMonthdata: '',
                endOfThisMonthSubHolidaydata: '',
                theEndOfTheFollowingMonthSubHolidaydata: '',
                endMonthdata: '',
                endMonthAfterNextdata: '',
                subHolidayHistoryList: [],
                subHolidayNextMonthList: [],
                lastMonth: '',
                thisMonth: '',
                nextMonth: '',
                monthAfterNext: '',
                LastMonth: '',
                ThisMonth: '',
                NextMonth: '',
                TwoMonthsLater: ''
            }
        },
        methods: {
            getCurrentTime() {
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
                console.log(this.aTime)
                this.mYear = yy
                this.today = yy + '/' + mm + '/' +dd
                console.log('this.today', this.today)
            },
            getLastMonth() {
                let date = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-' + this.yMd.substring(6,8)
                let today = new Date(date)
                let LastMonthNum = today.setMonth(today.getMonth() - 1)
                let LastMonthYmd = new Date(LastMonthNum)
                let LastMonth = LastMonthYmd.getMonth()+1
                console.log('LastMonth', LastMonth)
                this.LastMonth = LastMonth
            },
            getThisMonth() {
                let date = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-' + this.yMd.substring(6,8)
                let today = new Date(date)
                let ThisMonthNum = today.setMonth(today.getMonth())
                let ThisMonthYmd = new Date(ThisMonthNum)
                let ThisMonth = ThisMonthYmd.getMonth()+1
                console.log('ThisMonth', ThisMonth)
                this.ThisMonth = ThisMonth
            },
            getNextMonth() {
                let date = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-' + this.yMd.substring(6,8)
                let today = new Date(date)
                let NextMonthNum = today.setMonth(today.getMonth() + 1)
                let NextMonthYmd = new Date(NextMonthNum)
                let NextMonth = NextMonthYmd.getMonth()+1
                console.log('NextMonth', NextMonth)
                console.log('NextMonth', NextMonth)
                this.NextMonth = NextMonth
            },
            getTwoMonthsLater() {
                let date = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-' + this.yMd.substring(6,8)
                let today = new Date(date)
                let TwoMonthsLaterNum = today.setMonth(today.getMonth() + 2)
                let TwoMonthsLaterYmd = new Date(TwoMonthsLaterNum)
                let TwoMonthsLater = TwoMonthsLaterYmd.getMonth()+1
                console.log('TwoMonthsLater', TwoMonthsLater)
                this.TwoMonthsLater = TwoMonthsLater
            },
            dayOffHistoryOpen(){
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

                    case '(予)加算':
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

                    case '(予)減算':
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

                    case '(予)付与':
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
                    case '(予)失効':
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