<template>
    <el-dialog
        title="差戻理由"
        :visible.sync="openwf"
        width="410px"
        :close-on-click-modal="true">
        <el-input
            ref="textareaInput"
            type="textarea"
            placeholder="差戻理由"
            v-model="appcomment"
            maxlength="200"
            :row="10"
            show-word-limit>
        </el-input>
        <span slot="footer" class="dialog-footer">
            <el-button @click="returnRes()">確 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
import { sendreqMailApi } from '@/api/findPass'
import { getUserInfo, getUserDetailedInfo } from '@/utils/auth'
import { getRequestList, insertRequest, updateRequest, updateSkRequest, deleteRequest, updateLaRequest } from '@/api/requestList'
import { getWfList, insertWf, deleteWf, updateWf, updateadmitWf, updatelastadmitWf, deleteDataWf } from '@/api/wfList'
import { selectTgtMonth } from '@/api/monthCloseHistory'
import { Message } from 'element-ui';

export default {
    props: ['dateList'],
    name: "Remand",
    created() {
        this.rowdata = JSON.parse(sessionStorage.getItem("row-data"));
        this.getCurrentTime()
    },
    data() {
        return {
            openwf: false,
            appcomment: '',
            rowdata: [],
            wfList:'',
        }
    },
    methods:{
        //差戻　確認ボタン
        // returnRes() {
        //     if(this.appcomment == '') {
        //         Message({
        //             message: '差戻理由を入力してください',
        //             type: 'error',
        //             duration: 1000
        //         })
        //         return
        //     }
        //     this.getCurrentTime()
        //     let data = {
        //         companyCode: '000001',
        //         requestNumber: this.rowdata.requestNumber,
        //     }
        //     getWfList(data).then(res =>{
        //         if (res.data) {
        //             this.total = res.data.length
        //         } else {
        //             this.total = 0
        //         }
        //         let dataskreq = {
        //             companyCode: '000001',
        //             requestNumber: this.rowdata.requestNumber,
        //             updateUser: this.rowdata.reqAppUserId
        //         }
        //         console.log(dataskreq)
        //         updateSkRequest(dataskreq).then((res) => {
        //             console.log(res);
        //         })
        //         .catch((error) => {});
        //         let dataupdatewf = {
        //             companyCode: '000001',
        //             requestNumber: this.rowdata.requestNumber,
        //             reqAppOrder: this.total,
        //             reqAppComment: this.appcomment,
        //             updateUser: this.rowdata.reqAppUserId,
        //             reqAppDatetime: new Date(),
        //         }
        //         updateWf(dataupdatewf).then((res) => {
        //             console.log(res);
        //             Message({
        //                 message: '差し戻しました。',
        //                 type: 'success',
        //                 duration: 1500
        //             })
        //         })
        //     }).catch(error => {
        //         console.log(error)
        //     })
        //     let datasendmail = {
        //         companyCode: '000001',
        //         requestNumber: this.rowdata.requestNumber,
        //         userId: this.rowdata.requestUserId,
        //         userAppId: getUserInfo().userId,
        //         userName: '',
        //         appName: getUserInfo().userName,
        //         reqMailKbn: this.rowdata.recognizeKbnName,
        //         appMailKbn: '2',
        //         requestOverview: this.rowdata.requestOverview,
        //         reqAppComment: this.appcomment
        //     }
        //     sendreqMailApi(datasendmail).then(response => {
        //         if (response.status === 602){
        //             Message({
        //                 message: response.msg,
        //                 type: 'error',
        //                 duration: 1500
        //             })
        //         }else{
        //             Message({
        //                 message: 'メールを送信しました。',
        //                 type: 'success',
        //                 duration: 1500
        //             })
        //         }
        //     }).catch(error => {
        //         console.log(error)
        //     })
        //     this.openwf = false
        //     setTimeout(()=>{
        //         this.$router.go(-1);
        //     },1000)
        // },

    //差戻処理 浦修正20220608 start
    returnRes() {
        if(this.appcomment == '') {
            Message({
                message: '差戻理由を入力してください',
                type: 'error',
                duration: 1000
            })
            return
        }
        this.getCurrentTime()
        let data = {
            companyCode: '000001',
            requestNumber: this.rowdata.requestNumber,
        }
        getWfList(data).then(res =>{
            this.wfList = res.data
            console.log('this.wfList', this.wfList)
            console.log('getUserInfo().userId', getUserInfo().userId)      
            let arr = []
            for(let i = 0; i < this.wfList.length;i++) {
                if(this.wfList[i].reqAppUserId == getUserInfo().userId && this.wfList[i].reqAppKbn == '002') {
                // if(this.wfList[i].reqAppUserId == getUserInfo().userId) {
                    console.log('this.wfList[i].reqAppOrder', this.wfList[i].reqAppOrder)
                    arr.push(this.wfList[i].reqAppOrder)
                }
            }
            console.log('arr', arr)
            this.reviseReqAppOrder = Math.max(...arr)
            console.log('this.reviseReqAppOrder', this.reviseReqAppOrder)

            // 差戻する承認者のレコードを差戻に更新
            let dataupdatewf = {
                companyCode: '000001',
                requestNumber: this.rowdata.requestNumber,
                reqAppOrder: this.reviseReqAppOrder,
                reqAppComment: this.appcomment,
                updateUser: this.rowdata.reqAppUserId,
                reqAppDatetime: new Date(),
            }
            console.log('dataupdatewf', dataupdatewf)
            updateWf(dataupdatewf).then((res) => {
                console.log(res);
                console.log('this.wfList.length', this.wfList.length)
                // 差戻する承認者のレコード以降のデータを全て削除
                let deleteList = []
                for(let i = 0; i < this.wfList.length;i++) {
                    if(this.wfList[i].reqAppOrder > this.reviseReqAppOrder) {
                        let deleteData = {
                            companyCode: this.rowdata.companyCode,
                            requestNumber: this.rowdata.requestNumber,
                            reqAppOrder: this.wfList[i].reqAppOrder
                        }
                    deleteList.push(deleteData)
                    console.log('deleteList', deleteList)
                    }
                }
                deleteDataWf(deleteList).then((res) => {
                    console.log(res);
                })
                .catch((error) => {console.log(error)});
                // 申請テーブルの状態を差戻に更新
                let dataskreq = {
                    companyCode: '000001',
                    requestNumber: this.rowdata.requestNumber,
                    updateUser: this.rowdata.reqAppUserId
                }
                console.log(dataskreq)
                updateSkRequest(dataskreq).then((res) => {
                    console.log(res);
                    Message({
                        message: '差し戻しました。',
                            type: 'success',
                        duration: 1500
                    })

                    let datasendmail = {
                        companyCode: '000001',
                        requestNumber: this.rowdata.requestNumber,
                        userId: this.rowdata.requestUserId,
                        userAppId: this.userId_req,
                        userName: '',
                        appName: getUserInfo().userName,
                        reqMailKbn: this.rowdata.recognizeKbnName,
                        appMailKbn: '2',
                        requestOverview: this.rowdata.requestOverview,
                        reqAppComment:  this.appcomment
                    }
                    sendreqMailApi(datasendmail).then(response => {
                        if (response.status === 602)
                        {
                        Message({
                            message: response.msg,
                            type: 'error',
                            duration: 1500
                        })
                        }else{
                        Message({
                            message: 'メールを送信しました。',
                            type: 'success',
                            duration: 1500
                        })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                    this.openwf = false
                    this.changebyFlgwf = false
                    this.changebyFlgapp = false
                })
                .catch((error) => {console.log(error)});
            })
            .catch((error) => {console.log(error)});
        })
        .catch((error) => {console.log(error)});
        setTimeout(()=>{
            this.$router.go(-1);
        },1000)
    },
    //差戻処理 浦修正20220608 end
    
        getCurrentTime() {
            let yy = String(new Date().getFullYear())
            let mm = String((new Date().getMonth()+1) <10 ? '0' + (new Date().getMonth()+1) : (new Date().getMonth()+1))
            let dd = String(new Date().getDate() <10 ? '0' + new Date().getDate() : new Date().getDate());
            let hh = String(new Date().getHours() <10 ? '0' + new Date().getHours() : new Date().getHours());
            let mf = String(new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes());
            let ss = String(new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds());
            let ms = String(new Date().getMilliseconds()<10 ? '00'+new Date().getMilliseconds() : new Date().getMilliseconds()<100 ? '0'+new Date().getMilliseconds() : new Date().getMilliseconds());
            this.aTime = yy+mm+dd+hh+mf+ss+ms;
            this.yMd = yy+mm+dd
        },
        remandDialogOpen(){
            if(this.rowdata.wfStatusKbn !="900") {
                this.openwf = true
            }else {
                // 月締完了月の取得
                let data = {
                    companyCode: this.rowdata.companyCode,
                    userId: this.rowdata.requestUserId
                }
                console.log(data)
                selectTgtMonth(data).then(res => {
                    console.log(res)
                    this.tgtMonth = res.data
                    console.log(this.tgtMonth)
                    console.log('this.tgtMonth',this.tgtMonth)
                    // 月締処理履歴テーブルにデータがない場合（申請日の月からを可とする）
                    if(this.tgtMonth == 0){
                        // console.log('this.yMd', this.yMd)
                        // let okDay = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-01'
                        // console.log(okDay)
                        // let acceptableDay = new Date(okDay)
                        // console.log(acceptableDay)
                        // this.acceptedDate = acceptableDay
                        // console.log(this.acceptedDate)
                        this.acceptedDate = this.yMd.substring(0,4) + '-' + this.yMd.substring(4,6) + '-01'
                        console.log('月締無', this.acceptedDate)
                    // 月締処理履歴テーブルにデータがある場合
                    }else{
                        let tgtDate = this.tgtMonth.substring(0,4) + '-' + this.tgtMonth.substring(4,6) + '-01'
                        console.log(tgtDate)
                        let tgtDay = new Date(tgtDate)
                        let ajustDate = tgtDay.setMonth(tgtDay.getMonth() + 1)
                        let acceptedDay = new Date(ajustDate)
                        console.log(acceptedDay)
                        let year = acceptedDay.getFullYear()
                        let month = acceptedDay.getMonth()+1
                        let day = acceptedDay.getDate()
                        console.log('today', year, month, day)
                        this.acceptedDate = year + '-' + month + '-' + day
                        console.log('月締有', this.acceptedDate)
                    }
                    console.log('acceptedDate', this.acceptedDate)
                    console.log(this.rowdata.requestTypeKbn)
                    // 申請中の休暇、残業、出勤の日付の配列
                    let days = []
                    if(this.rowdata.requestTypeKbn == '020'){
                        console.log(this.dateList)
                        console.log(this.dateList.length)
                        console.log(this.dateList[0].holidayPlanDay)
                        for(let i = 0; i < this.dateList.length; i++){
                            days.push(this.dateList[i].holidayPlanDay.substring(0,4) + '-' + this.dateList[i].holidayPlanDay.substring(4,6) + '-' + this.dateList[i].holidayPlanDay.substring(6,8))
                        }
                        console.log(days)
                    }else if(this.rowdata.requestTypeKbn == '021' || this.rowdata.requestTypeKbn == '022' || this.rowdata.requestTypeKbn == '023'){
                        console.log(this.dateList)
                        let yy = this.dateList.getFullYear()
                        let mm = this.dateList.getMonth()+1
                        let dd = this.dateList.getDate()
                        let day = yy + '-' + mm + '-' + dd
                        days.push(day)
                    } 
                    // 月締完了月を超えているかの真偽判定
                    console.log(days)
                    console.log(days.length)
                    console.log(this.acceptedDate)
                    let checkDays = []
                    for(let i = 0; i < days.length; i++){
                        console.log(new Date(days[i]))
                        console.log(new Date(this.acceptedDate.replace(/-/g , "/")))
                        checkDays.push(new Date(days[i].replace(/-/g , "/")) >= new Date(this.acceptedDate.replace(/-/g , "/")))
                    }
                    console.log(checkDays)
                    let count = checkDays.filter(checkDays => checkDays == false)
                    this.falseCount = count.length
                    console.log(this.falseCount)
                    console.log(this.falseCount)
                    if(this.falseCount >= 1){
                        Message({
                            message: '月締めが完了した日付が含まれております。',
                            type: 'error',
                            duration: 4000
                        })
                        return
                    }else{
                        this.openwf = true
                    }
                }).catch(error => {
                console.log(error) 
                })

            }
        },
    }
}
</script>